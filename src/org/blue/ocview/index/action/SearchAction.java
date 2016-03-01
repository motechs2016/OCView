package org.blue.ocview.index.action;

import java.util.List;

import org.blue.backend.media.service.SeriesService;
import org.blue.backend.media.service.impl.SeriesServiceImpl;
import org.blue.ocview.index.domain.SearchBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * “搜索”业务控制类Action
 * @author ldc4
 */
public class SearchAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private String keyword;//关键词
	
	public String execute() throws Exception {
		//获取搜索结果
		SeriesService seriesService = new SeriesServiceImpl();
		List<SearchBean> searchList = seriesService.search(new String(keyword.getBytes("ISO8859-1"),"UTF-8"));
		ActionContext.getContext().put("searchList", searchList);
		
		return "success";
	}

	//getter&setter
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
