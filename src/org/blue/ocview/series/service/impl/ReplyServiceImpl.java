package org.blue.ocview.series.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.blue.backend.user.domain.User;
import org.blue.backend.user.service.UserService;
import org.blue.backend.user.service.impl.UserServiceImpl;
import org.blue.backend.util.JdbcUtils;
import org.blue.ocview.series.domain.Reply;
import org.blue.ocview.series.service.ReplyService;

public class ReplyServiceImpl implements ReplyService{

	private Connection conn;
	
	public ReplyServiceImpl() throws SQLException {
		conn = JdbcUtils.getConnection();
	}
	
	@Override
	public List<Reply> findByCommentId(int commentId) throws Exception {
		List<Reply> replies = new ArrayList<Reply>();
		String sql = "select * from reply where commentId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, commentId);
		ResultSet rs = pstmt.executeQuery();
		Reply reply = null;
		while(rs.next()){
			reply = new Reply();
			reply.setReplyId(rs.getInt("replyId"));
			reply.setReplyContent(rs.getString("replyContent"));
			reply.setReplyTime(rs.getTime("replyTime"));
			//查找发表该回复的用户
			UserService userService = new UserServiceImpl();
			User user = userService.getById(rs.getInt("userId"));
			reply.setUser(user);
			replies.add(reply);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return replies;
	}

	@Override
	public boolean save(Reply reply) throws Exception {
		boolean flag = false;
		String sql = "insert into reply(userId,commentId,replyTime,replyContent) values(?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, reply.getUser().getUserId());
		pstmt.setInt(2, reply.getComment().getCommentId());
		pstmt.setTimestamp(3, reply.getReplyTime());
		pstmt.setString(4, reply.getReplyContent());
		if(pstmt.executeUpdate()>0){
			flag = true;
		}
		JdbcUtils.free(null, pstmt, conn);
		return flag;
	}

}
