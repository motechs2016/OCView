package org.blue.ocview.series.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.blue.backend.user.domain.User;
import org.blue.ocview.series.domain.Comment;
import org.blue.ocview.series.domain.Reply;
import org.blue.ocview.series.service.ReplyService;
import org.blue.ocview.series.service.impl.ReplyServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

public class ReplyAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private String replyContent;
	private int commentId;
	
	private InputStream result;
	
	public String execute() throws Exception {
		//封装需要保存的信息
		Reply reply = new Reply();
		reply.setReplyContent(new String(replyContent.getBytes("ISO8859-1"),"UTF-8"));
		reply.setReplyTime(new Date());
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if(user==null){
			return "error";
		}
		reply.setUser(user);
		Comment comment = new Comment();
		comment.setCommentId(commentId);
		reply.setComment(comment);
		
		//保存用户发表的回复
		ReplyService replyService = new ReplyServiceImpl();
		boolean flag = replyService.save(reply);
		
		if(flag){
			result = new ByteArrayInputStream("OK".getBytes("UTF-8"));
		}else{
			result = new ByteArrayInputStream("err".getBytes("UTF-8"));
		}
		
		return "success";
	}


	//getter&setter
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public InputStream getResult() {
		return result;
	}
	public void setResult(InputStream result) {
		this.result = result;
	}
}
