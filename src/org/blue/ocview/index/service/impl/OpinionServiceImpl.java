package org.blue.ocview.index.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.blue.backend.util.JdbcUtils;
import org.blue.ocview.index.service.OpinionService;

public class OpinionServiceImpl implements OpinionService{

	private Connection conn;
	
	public OpinionServiceImpl() throws SQLException {
		conn = JdbcUtils.getConnection();
	}
	
	@Override
	public void save(String opinionContent, String contactInfo)
			throws Exception {
		String sql = "insert into opinion(opinionContent,contactInfo) values(?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, opinionContent);
		pstmt.setString(2, contactInfo);
		pstmt.executeUpdate();
		JdbcUtils.free(null, pstmt, conn);
	}
	
}
