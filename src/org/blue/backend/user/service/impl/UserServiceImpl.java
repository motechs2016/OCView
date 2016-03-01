package org.blue.backend.user.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.blue.backend.user.domain.User;
import org.blue.backend.user.service.UserService;
import org.blue.backend.util.JdbcUtils;
import org.blue.backend.util.PageBean;

public class UserServiceImpl implements UserService {
	
	private Connection conn = null;
	
	public UserServiceImpl() throws SQLException {
		conn = JdbcUtils.getConnection();
	}
	
	@Override
	public PageBean getPageBean(int pageNum, int pageSize) throws Exception {
		//2次查询，不需要使用事务
		List<User> recordList = new ArrayList<User>();
		String sql = "select userId,userAccount,userNickname,userEmail from user limit ?,?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, pageSize*(pageNum-1));
		pstmt.setInt(2, pageSize);
		ResultSet rs = pstmt.executeQuery();
		User user = null;
		while(rs.next()){
			user = new User();
			user.setUserId(rs.getInt("userId"));
			user.setUserAccount(rs.getString("userAccount"));
			user.setUserNickname(rs.getString("userNickname"));
			user.setUserEmail(rs.getString("userEmail"));
			recordList.add(user);
		}
		//查询总记录数
		sql = "select count(*) from user";
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
	public void delete(int[] userIds) throws Exception {
		conn.setAutoCommit(false);
		String sql = "delete from user where userId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		try{
			for(int i=0;i<userIds.length;i++){
				//删除用户
				pstmt.setInt(1, userIds[i]);
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
	public void save(User model) throws Exception {
		String sql = "insert into user(userAccount,userPassword,userNickname,userEmail) values(?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, model.getUserAccount());
		pstmt.setString(2, DigestUtils.md5Hex(model.getUserPassword()));
		pstmt.setString(3, model.getUserNickname());
		pstmt.setString(4, model.getUserEmail());
		pstmt.executeUpdate();
		JdbcUtils.free(null, pstmt, conn);
	}

	@Override
	public User getById(int userId) throws Exception {
		User user = new User();
		String sql = "select * from user where userId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, userId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			user.setUserId(rs.getInt("userId"));
			user.setUserNickname(rs.getString("userNickname"));
			user.setUserAccount(rs.getString("userAccount"));
			user.setUserEmail(rs.getString("userEmail"));
		}
		JdbcUtils.free(rs, pstmt, conn);
		return user;
	}

	@Override
	public void update(User user) throws Exception {
		String sql = "update user set userNickname=?,userEmail=? where userId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getUserNickname());
		pstmt.setString(2, user.getUserEmail());
		pstmt.setInt(3, user.getUserId());
		pstmt.executeUpdate();
		JdbcUtils.free(null, pstmt, conn);
	}

	@Override
	public boolean login(User user) throws Exception {
		boolean flag = false;
		
		String sql = "select * from user where userAccount=? limit 1";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getUserAccount());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			if(user.getUserPassword().equals(rs.getString("userPassword"))){
				user.setUserId(rs.getInt("userId"));
				user.setUserNickname(rs.getString("userNickname"));
				user.setUserEmail(rs.getString("userEmail"));
				flag = true;
			}
		}
		JdbcUtils.free(rs, pstmt, conn);
		return flag;
	}
}
