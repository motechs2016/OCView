package org.blue.ocview.series.action;

import java.util.ArrayList;
import java.util.List;

import org.blue.backend.util.PageBean;
import org.blue.ocview.series.domain.Comment;
import org.blue.ocview.series.service.CommentService;
import org.blue.ocview.series.service.impl.CommentServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

public class ShowCommentReplyAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private int pageNum;
	private int pageSize;
	private int seriesId;
	
	private int pageCount;
	private int recordCount;
	
	private List<Comment> comments = new ArrayList<Comment>();
	
	private int userCount;
	
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		//校验
		if(pageNum <= 0){
			pageNum = 1;
		}
		if(pageSize <= 0){
			pageSize = 1;
		}
		
		//提供跟帖信息
		CommentService commentService = new CommentServiceImpl();
		PageBean recordList = commentService.getPageBean(pageNum,pageSize,seriesId);
		
		comments = (List<Comment>)recordList.getRecordList();
		pageCount = recordList.getPageCount();
		pageNum = recordList.getPageNum();
		pageSize = recordList.getPageSize();
		recordCount = recordList.getRecordCount();
		
		//提供跟帖人数
		commentService = new CommentServiceImpl();
		userCount = commentService.getUserCount(seriesId);
		
		return "success";
	}
	
	
	//getter&setter
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(int seriesId) {
		this.seriesId = seriesId;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
}
