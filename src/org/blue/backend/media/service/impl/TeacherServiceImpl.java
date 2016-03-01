package org.blue.backend.media.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.blue.backend.media.domain.Teacher;
import org.blue.backend.media.service.TeacherService;
import org.blue.backend.util.JdbcUtils;
import org.blue.backend.util.PageBean;

public class TeacherServiceImpl implements TeacherService{

	private Connection conn = null;
	
	public TeacherServiceImpl() throws SQLException{
		conn = JdbcUtils.getConnection();
	}
	
	@Override
	public PageBean getPageBean(int pageNum, int pageSize) throws Exception {
		List<Teacher> recordList = new ArrayList<Teacher>();
		String sql = "select * from teacher limit ?,?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, pageSize*(pageNum-1));
		pstmt.setInt(2, pageSize);
		ResultSet rs = pstmt.executeQuery();
		Teacher teacher = null;
		while(rs.next()){
			teacher = new Teacher();
			teacher.setTeacherId(rs.getInt("teacherId"));
			teacher.setTeacherName(rs.getString("teacherName"));
			teacher.setTeacherDescription(rs.getString("teacherDescription"));
			teacher.setTeacherCollege(rs.getString("teacherCollege"));
			teacher.setTeacherDegree(rs.getString("teacherDegree"));
			teacher.setTeacherJob(rs.getString("teacherJob"));
			recordList.add(teacher);
		}
		//查询总记录数
		sql = "select count(*) from teacher";
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
		String sql = "delete from teacher where teacherId = ?";
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
	public void save(Teacher model) throws Exception {
		String sql = "insert into teacher(teacherName,teacherDescription,teacherCollege,teacherDegree,teacherJob,teacherPicture) values(?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, model.getTeacherName());
		pstmt.setString(2, model.getTeacherDescription());
		pstmt.setString(3, model.getTeacherCollege());
		pstmt.setString(4, model.getTeacherDegree());
		pstmt.setString(5, model.getTeacherJob());
		pstmt.setString(6, model.getTeacherPicture());
		pstmt.executeUpdate();
		JdbcUtils.free(null, pstmt, conn);
	}

	@Override
	public Teacher getById(int id) throws Exception {
		Teacher teacher = new Teacher();
		String sql = "select * from teacher where teacherId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			teacher.setTeacherId(rs.getInt("teacherId"));
			teacher.setTeacherName(rs.getString("teacherName"));
			teacher.setTeacherDescription(rs.getString("teacherDescription"));
			teacher.setTeacherCollege(rs.getString("teacherCollege"));
			teacher.setTeacherDegree(rs.getString("teacherDegree"));
			teacher.setTeacherJob(rs.getString("teacherJob"));
			teacher.setTeacherPicture(rs.getString("teacherPicture"));
		}
		JdbcUtils.free(rs, pstmt, conn);
		return teacher;
	}

	@Override
	public void update(Teacher model) throws Exception {
		String sql = "update teacher set teacherName=?,teacherDescription=?,teacherCollege=?,teacherDegree=?,teacherJob=? where teacherId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, model.getTeacherName());
		pstmt.setString(2, model.getTeacherDescription());
		pstmt.setString(3, model.getTeacherCollege());
		pstmt.setString(4, model.getTeacherDegree());
		pstmt.setString(5, model.getTeacherJob());
		pstmt.setInt(6, model.getTeacherId());
		pstmt.executeUpdate();
		JdbcUtils.free(null, pstmt, conn);
	}

	@Override
	public List<Teacher> findAllSimple() throws Exception {
		List<Teacher> teacherList = new ArrayList<Teacher>();
		String sql = "select teacherId,teacherName from teacher";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		Teacher teacher = null;
		while(rs.next()){
			teacher = new Teacher();
			teacher.setTeacherId(rs.getInt("teacherId"));
			teacher.setTeacherName(rs.getString("teacherName"));
			teacherList.add(teacher);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return teacherList;
	}

}
