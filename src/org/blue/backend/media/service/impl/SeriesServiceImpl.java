package org.blue.backend.media.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.blue.backend.media.domain.Series;
import org.blue.backend.media.domain.Teacher;
import org.blue.backend.media.service.SeriesService;
import org.blue.backend.navication.domain.Navication;
import org.blue.backend.util.JdbcUtils;
import org.blue.backend.util.PageBean;
import org.blue.ocview.index.domain.SearchBean;

public class SeriesServiceImpl implements SeriesService{

	private Connection conn = null;
	
	public SeriesServiceImpl() throws SQLException{
		conn = JdbcUtils.getConnection();
	}
	
	@Override
	public PageBean getPageBean(int pageNum, int pageSize) throws Exception {
		List<Series> recordList = new ArrayList<Series>();
		String sql = "select * from series limit ?,?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, pageSize*(pageNum-1));
		pstmt.setInt(2, pageSize);
		ResultSet rs = pstmt.executeQuery();
		Series series = null;
		while(rs.next()){
			series = new Series();
			series.setSeriesId(rs.getInt("seriesId"));
			series.setSeriesName(rs.getString("seriesName"));
			series.setSeriesDescription(rs.getString("seriesDescription"));
			series.setSeriesInfo(rs.getString("seriesInfo"));
			recordList.add(series);
		}
		//查询总记录数
		sql = "select count(*) from series";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		int recordCount = 0;
		if(rs.next()){
			recordCount = rs.getInt(1);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return new PageBean(pageNum, pageSize, recordCount, recordList);
	}

	@Override
	public void delete(int[] ids) throws Exception {
		conn.setAutoCommit(false);
		String sql = "delete from series where seriesId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		try{
			for(int i=0;i<ids.length;i++){
				pstmt.setInt(1, ids[i]);
				pstmt.executeUpdate();
			}
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
		JdbcUtils.free(null, pstmt, conn);
	}

	@Override
	public void save(Series model) throws Exception {
		String sql = "insert into series(seriesName,seriesInfo,seriesDescription,navicationId,teacherId,picturePath,courseNum) values(?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, model.getSeriesName());
		pstmt.setString(2, model.getSeriesInfo());
		pstmt.setString(3, model.getSeriesDescription());
		pstmt.setInt(4, model.getNavication().getNavicationId());
		pstmt.setInt(5, model.getTeacher().getTeacherId());
		pstmt.setString(6, model.getPicturePath());
		pstmt.setInt(7, model.getCourseNum());
		pstmt.executeUpdate();
		JdbcUtils.free(null, pstmt, conn);
	}

	@Override
	public Series getById(int id) throws Exception {
		Series series = null;
		String sql = "select * from series where seriesId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			series = new Series();
			series.setSeriesId(rs.getInt("seriesId"));
			series.setSeriesName(rs.getString("seriesName"));
			series.setSeriesDescription(rs.getString("seriesDescription"));
			series.setSeriesInfo(rs.getString("seriesInfo"));
			series.setPicturePath(rs.getString("picturePath"));
			Navication navication = new Navication();
			navication.setNavicationId(rs.getInt("navicationId"));
			series.setNavication(navication);
			Teacher teacher = new Teacher();
			teacher.setTeacherId(rs.getInt("teacherId"));
			series.setTeacher(teacher);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return series;
	}

	@Override
	public void update(Series model) throws Exception {
		String sql = "update series set seriesName=?,seriesInfo=?,seriesDescription=?,navicationId=?,teacherId=?,picturePath=?,courseNum=? where seriesId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, model.getSeriesName());
		pstmt.setString(2, model.getSeriesInfo());
		pstmt.setString(3, model.getSeriesDescription());
		pstmt.setInt(4, model.getNavication().getNavicationId());
		pstmt.setInt(5, model.getTeacher().getTeacherId());
		pstmt.setString(6, model.getPicturePath());
		pstmt.setInt(7, model.getCourseNum());
		pstmt.setInt(8, model.getSeriesId());
		pstmt.executeUpdate();
		JdbcUtils.free(null, pstmt, conn);
	}

	@Override
	public List<Series> findAllSimple() throws Exception {
		List<Series> seriesList = new ArrayList<Series>();
		String sql = "select seriesId,seriesName from series";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		Series series = null;
		while(rs.next()){
			series = new Series();
			series.setSeriesId(rs.getInt("seriesId"));
			series.setSeriesName(rs.getString("seriesName"));
			seriesList.add(series);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return seriesList;
	}

	@Override
	public List<Series> findLastSeven(int navicationId) throws Exception {
		List<Series> seriesList = new ArrayList<Series>();
		String sql = "select * from series where navicationId in (select navicationId from navication where parentId=?) order by seriesId desc limit 0,7";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, navicationId);
		ResultSet rs = pstmt.executeQuery();
		Series series = null;
		while(rs.next()){
			series = new Series();
			series.setSeriesId(rs.getInt("seriesId"));
			series.setSeriesName(rs.getString("seriesName"));
			series.setSeriesInfo(rs.getString("seriesInfo"));
			series.setPicturePath(rs.getString("picturePath"));
			seriesList.add(series);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return seriesList;
	}

	@Override
	public List<Series> getByNavicationId(int navicationId) throws Exception {
		List<Series> seriesList = new ArrayList<Series>();
		String sql = "select * from series where navicationId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, navicationId);
		ResultSet rs = pstmt.executeQuery();
		Series series = null;
		while(rs.next()){
			series = new Series();
			series.setSeriesId(rs.getInt("seriesId"));
			series.setSeriesName(rs.getString("seriesName"));
			series.setSeriesInfo(rs.getString("seriesInfo"));
			series.setPicturePath(rs.getString("picturePath"));
			seriesList.add(series);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return seriesList;
	}

	@Override
	public List<SearchBean> search(String keyword) throws Exception {
		List<SearchBean> searchList = new ArrayList<SearchBean>();
		String sql = "select * from series as s,teacher as t,navication as n " +
				"where s.teacherId = t.teacherId and s.navicationId = n.navicationId " +
				"and (seriesName like ? or seriesDescription like ? or teacherName like ? or teacherCollege like ? or navicationName like ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		for(int i=1;i<=5;i++){
			pstmt.setString(i, "%"+keyword+"%");
		}
		SearchBean searchBean = null;
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			searchBean = new SearchBean();
			searchBean.setSeriesId(rs.getInt("seriesId"));
			searchBean.setSeriesName(rs.getString("seriesName"));
			searchBean.setSeriesDescription(rs.getString("seriesDescription"));
			searchBean.setPicturePath(rs.getString("picturePath"));
			searchBean.setTeacherName(rs.getString("teacherName"));
			searchBean.setTeacherCollege(rs.getString("teacherCollege"));
			searchBean.setNavicationName(rs.getString("navicationName"));
			searchList.add(searchBean);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return searchList;
	}

}
