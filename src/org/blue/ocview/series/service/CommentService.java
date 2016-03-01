package org.blue.ocview.series.service;

import org.blue.backend.util.PageBean;
import org.blue.ocview.series.domain.Comment;

public interface CommentService {

	/**
	 * 查询分页信息
	 * @param pageNum
	 * @param pageSize
	 * @param seriesId 
	 * @return
	 * @throws Exception
	 */
	PageBean getPageBean(int pageNum, int pageSize, int seriesId) throws Exception;

	/**
	 * 获取参与人数
	 * @param seriesId
	 * @return
	 * @throws Exception
	 */
	int getUserCount(int seriesId) throws Exception;

	/**
	 * 保存评论
	 * @param comment
	 * @return 
	 * @throws Exception
	 */
	boolean save(Comment comment) throws Exception;

}
