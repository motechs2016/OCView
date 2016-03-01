package org.blue.ocview.index.service;

public interface OpinionService {

	/**
	 * 保存用户反馈的意见
	 * @param opinionContent
	 * @param contactInfo
	 * @throws Exception
	 */
	void save(String opinionContent, String contactInfo) throws Exception;

}
