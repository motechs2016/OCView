package org.blue.ocview.series.domain;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.blue.backend.user.domain.User;

public class Reply {

	private int replyId;
	
	private Date replyTime;
	
	private String replyContent;
	
	private User user;
	
	private Comment comment;

	
	//getter&setter
	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public java.sql.Timestamp getReplyTime() {
		return new java.sql.Timestamp(replyTime.getTime());
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@JSON(serialize=false)
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
}
