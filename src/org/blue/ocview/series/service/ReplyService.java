package org.blue.ocview.series.service;

import java.util.List;

import org.blue.ocview.series.domain.Reply;

public interface ReplyService {

	/**
	 * 根据评论编号查找回复记录
	 * @param commentId
	 * @return
	 * @throws Exception
	 */
	List<Reply> findByCommentId(int commentId) throws Exception;

	/**
	 * 保存回复
	 * @param reply
	 * @return 
	 * @throws Exception
	 */
	boolean save(Reply reply) throws Exception;

}
