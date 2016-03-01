package org.blue.backend.media.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.blue.backend.media.domain.Recommend;
import org.blue.backend.media.service.RecommendService;
import org.blue.backend.media.service.SeriesService;
import org.blue.backend.util.JdbcUtils;

public class RecommendServiceImpl implements RecommendService{

	private Connection conn = null;
	
	public RecommendServiceImpl() throws SQLException {
		conn = JdbcUtils.getConnection();
	}
	
	@Override
	public List<Recommend> findAll() throws Exception {
		List<Recommend> recommends = new ArrayList<Recommend>();
		String sql = "select * from recommend order by recommendOrder asc";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		Recommend recommend = null;
		while(rs.next()){
			recommend = new Recommend();
			recommend.setRecommendId(rs.getInt("recommendId"));
			recommend.setPicturePath(rs.getString("picturePath"));
			recommend.setBackgroundColor(rs.getString("backgroundColor"));
			SeriesService seriesService = new SeriesServiceImpl();
			recommend.setSeries(seriesService.getById(rs.getInt("seriesId")));
			recommends.add(recommend);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return recommends;
	}

	@Override
	public void delete(int[] ids) throws Exception {
		conn.setAutoCommit(false);
		String sql = "delete from recommend where recommendId = ?";
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
	public void moveUp(int recommendId) throws Exception {
		conn.setAutoCommit(false);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			//得到recommendOrder值
			String sql = "select recommendOrder from recommend where recommendId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, recommendId);
			rs = pstmt.executeQuery();
			int recommendOrder = 0;
			if(rs.next()){
				recommendOrder = rs.getInt("recommendOrder");
			}else{
				return ;
			}
			//找到比recommendId略小的那个对象
			sql = "select recommendId,recommendOrder from recommend where recommendOrder < ? order by recommendOrder DESC limit 0,1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, recommendOrder);
			rs = pstmt.executeQuery();
			int otherId = 0;
			int otherOrder = 0;
			if(rs.next()){
				otherId = rs.getInt("recommendId");
				otherOrder = rs.getInt("recommendOrder");
			}else{
				return ;
			}
			//交换order值
			sql = "update recommend set recommendOrder = ? where recommendId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, otherOrder);
			pstmt.setInt(2, recommendId);
			pstmt.addBatch();
			pstmt.setInt(1, recommendOrder);
			pstmt.setInt(2, otherId);
			pstmt.addBatch();
			pstmt.executeBatch();
			
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			e.printStackTrace();
		}finally{
			JdbcUtils.free(rs, pstmt, conn);
		}
	}

	@Override
	public void moveDown(int recommendId) throws Exception {
		conn.setAutoCommit(false);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			//得到recommendOrder值
			String sql = "select recommendOrder from recommend where recommendId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, recommendId);
			rs = pstmt.executeQuery();
			int recommendOrder = 0;
			if(rs.next()){
				recommendOrder = rs.getInt("recommendOrder");
			}else{
				return ;
			}
			//找到比recommendId略大的那个对象
			sql = "select recommendId,recommendOrder from recommend where recommendOrder > ? order by recommendOrder ASC limit 0,1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, recommendOrder);
			rs = pstmt.executeQuery();
			int otherId = 0;
			int otherOrder = 0;
			if(rs.next()){
				otherId = rs.getInt("recommendId");
				otherOrder = rs.getInt("recommendOrder");
			}else{
				return ;
			}
			//交换order值
			sql = "update recommend set recommendOrder = ? where recommendId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, otherOrder);
			pstmt.setInt(2, recommendId);
			pstmt.addBatch();
			pstmt.setInt(1, recommendOrder);
			pstmt.setInt(2, otherId);
			pstmt.addBatch();
			pstmt.executeBatch();
			
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			e.printStackTrace();
		}finally{
			JdbcUtils.free(rs, pstmt, conn);
		}
	}

	@Override
	public void save(Recommend model) throws Exception {
		conn.setAutoCommit(false);
		PreparedStatement pstmt = null;
		try{
			//插入一条新纪录，保留navicationOrder字段为0
			String sql = "insert into recommend(seriesId,picturePath,backgroundColor,recommendOrder) value(?,?,?,0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, model.getSeries().getSeriesId());
			pstmt.setString(2, model.getPicturePath());
			pstmt.setString(3, model.getBackgroundColor());
			pstmt.executeUpdate();
			//获取插入后的主键
			ResultSet rs = pstmt.getGeneratedKeys();
			int recommendId = 0;
			if(rs.next()) {
				recommendId = ((Long)rs.getObject(1)).intValue();
	        }
			//使recommendOrder的值和主键保持一致
			sql = "update recommend set recommendOrder = ? where recommendId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, recommendId);
			pstmt.setInt(2, recommendId);
			pstmt.executeUpdate();
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			e.printStackTrace();
		}finally{
			JdbcUtils.free(null, pstmt, conn);
		}
	}

	@Override
	public Recommend getById(int recommendId) throws Exception {
		String sql = "select * from recommend where recommendId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, recommendId);
		ResultSet rs = pstmt.executeQuery();
		Recommend recommend = null;
		
		if(rs.next()){
			recommend = new Recommend();
			recommend.setRecommendId(rs.getInt("recommendId"));
			recommend.setPicturePath(rs.getString("picturePath"));
			recommend.setBackgroundColor(rs.getString("backgroundColor"));
			SeriesService seriesService = new SeriesServiceImpl();
			recommend.setSeries(seriesService.getById(rs.getInt("seriesId")));
		}
		
		JdbcUtils.free(rs, pstmt, conn);
		return recommend;
	}

	@Override
	public void update(Recommend model) throws Exception {
		String sql = "update recommend set picturePath = ? ,backgroundColor = ? where recommendId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, model.getPicturePath());
		pstmt.setString(2, model.getBackgroundColor());
		pstmt.setInt(3, model.getRecommendId());
		pstmt.executeUpdate();
		JdbcUtils.free(null, pstmt, conn);
	}

}
