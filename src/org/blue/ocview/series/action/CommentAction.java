package org.blue.ocview.series.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.blue.backend.media.domain.Series;
import org.blue.backend.user.domain.User;
import org.blue.ocview.series.domain.Comment;
import org.blue.ocview.series.service.CommentService;
import org.blue.ocview.series.service.impl.CommentServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

public class CommentAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private String commentContent;//评论的内容
	private int seriesId;//评论的系列
	
	private InputStream result;
	
	public String execute() throws Exception {
		//封装需要保存的信息
		Comment comment = new Comment();
		comment.setCommentContent(new String(commentContent.getBytes("ISO8859-1"),"UTF-8"));
		comment.setCommentTime(new Date());
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if(user==null){
			return "error";
		}
		comment.setUser(user);
		Series series = new Series();
		series.setSeriesId(seriesId);
		comment.setSeries(series);
		
		//保存用户发表的评论
		CommentService commentService = new CommentServiceImpl();
		boolean flag = commentService.save(comment);
		if(flag){
			result = new ByteArrayInputStream("OK".getBytes("UTF-8"));
		}else{
			result = new ByteArrayInputStream("err".getBytes("UTF-8"));
		}
		
		return "success";
	}

	
	//getter&setter 
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public int getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(int seriesId) {
		this.seriesId = seriesId;
	}
	public InputStream getResult() {
		return result;
	}
	public void setResult(InputStream result) {
		this.result = result;
	}
	
}
