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
import org.blue.backend.util.PageBean;
import org.blue.ocview.series.domain.Comment;
import org.blue.ocview.series.domain.Reply;
import org.blue.ocview.series.service.CommentService;
import org.blue.ocview.series.service.ReplyService;

public class CommentServiceImpl implements CommentService{

	private Connection conn;
	
	public CommentServiceImpl() throws SQLException {
		conn = JdbcUtils.getConnection();
	}
	
	public PageBean getPageBean(int pageNum, int pageSize,int seriesId)
			throws Exception {
		//2次查询，不需要使用事务
		List<Comment> recordList = new ArrayList<Comment>();
		String sql = "select * from comment where seriesId = ? limit ?,?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, seriesId);
		pstmt.setInt(2, pageSize*(pageNum-1));
		pstmt.setInt(3, pageSize);
		ResultSet rs = pstmt.executeQuery();
		Comment comment = null;
		while(rs.next()){
			comment = new Comment();
			int commentId = rs.getInt("commentId");
			comment.setCommentId(commentId);
			comment.setCommentContent(rs.getString("commentContent"));
			comment.setCommentTime(rs.getTimestamp("commentTime"));
			//获取到评论下的所有回复
			ReplyService replyService = new ReplyServiceImpl();
			List<Reply> replies = replyService.findByCommentId(commentId);
			comment.setReplies(replies);
			//获取到发表该评论的用户
			UserService userService = new UserServiceImpl();
			User user = userService.getById(rs.getInt("userId"));
			comment.setUser(user);
			recordList.add(comment);
		}
		//查询总记录数
		sql = "select count(*) from comment where seriesId = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, seriesId);
		rs = pstmt.executeQuery();
		int recordCount = 0;
		if(rs.next()){
			recordCount = rs.getInt(1);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return new PageBean(pageNum, pageSize, recordCount, recordList);
	}

	@Override
	public int getUserCount(int seriesId) throws Exception {
		String sql = "select distinct userId from comment union select distinct userId from reply";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		rs.last();
		int userCount = rs.getRow();
		JdbcUtils.free(rs, pstmt, conn);
		return userCount;
	}

	@Override
	public boolean save(Comment comment) throws Exception {
		boolean flag = false;
		String sql = "insert into comment(userId,seriesId,commentTime,commentContent) values(?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, comment.getUser().getUserId());
		pstmt.setInt(2, comment.getSeries().getSeriesId());
		pstmt.setTimestamp(3, comment.getCommentTime());
		pstmt.setString(4, comment.getCommentContent());
		if(pstmt.executeUpdate()>0){
			flag = true;
		}
		JdbcUtils.free(null, pstmt, conn);
		return flag;
	}

}
