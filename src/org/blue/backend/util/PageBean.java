package org.blue.backend.util;

import java.util.List;

/**
 * 分页功能中的一页信息
 * @author ldc4
 */
@SuppressWarnings("rawtypes")
public class PageBean {
	
	//指定的或是页面参数
	private int pageNum; 	//当前页
	private int pageSize;		//每页显示多少条
	
	//查询数据库
	private int recordCount;	//总记录数
	private List recordList; 	//本页的数据列表
	
	//计算
	private int pageCount;	 	//总页数
	
	
	public PageBean(int pageNum, int pageSize, int recordCount, List recordList) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.recordCount = recordCount;
		this.recordList = recordList;
		//计算总页码
		pageCount = (recordCount + pageSize - 1) / pageSize; // (recordCount-1)/pageSize+1
	}
	public List getRecordList() {
		return recordList;
	}
	public void setRecordList(List recordList) {
		this.recordList = recordList;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
}
