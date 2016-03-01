package org.blue.ocview.series.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;
import org.blue.backend.media.domain.Series;
import org.blue.backend.user.domain.User;

public class Comment {
	
	private int commentId;
	
	private Date commentTime;
	
	private String commentContent;
	
	private User user;
	
	private Series series;
	
	private List<Reply> replies = new ArrayList<Reply>();

	
	//getter&setter
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public java.sql.Timestamp getCommentTime() {
		return new java.sql.Timestamp(commentTime.getTime());
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@JSON(serialize=false)
	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}
	
}
