package org.blue.backend.user.service;

import org.blue.backend.user.domain.User;
import org.blue.backend.util.PageBean;

public interface UserService {

	/**
	 * 得到分页信息
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	PageBean getPageBean(int pageNum, int pageSize) throws Exception;

	/**
	 * 删除
	 * @param userIds
	 * @throws Exception
	 */
	void delete(int[] userIds) throws Exception;

	/**
	 * 添加
	 * @param model
	 * @throws Exception
	 */
	void save(User model) throws Exception;

	/**
	 * 按ID查找
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	User getById(int userId) throws Exception;

	/**
	 * 更新
	 * @param user
	 * @throws Exception
	 */
	void update(User user) throws Exception;

	/**
	 * 登陆
	 * @param user
	 * @return
	 * @throws Exception
	 */
	boolean login(User user) throws Exception;

}
