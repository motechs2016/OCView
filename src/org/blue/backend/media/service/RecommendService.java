package org.blue.backend.media.service;

import java.util.List;

import org.blue.backend.media.domain.Recommend;

public interface RecommendService {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	List<Recommend> findAll() throws Exception;

	/**
	 * 删除
	 * @param ids
	 * @throws Exception
	 */
	void delete(int[] ids) throws Exception;

	
	/**
	 * 上移
	 * @param recommendId
	 * @throws Exception
	 */
	void moveUp(int recommendId) throws Exception;

	/**
	 * 下移
	 * @param recommendId
	 * @throws Exception
	 */
	void moveDown(int recommendId) throws Exception;

	/**
	 * 保存
	 * @param model
	 * @throws Exception
	 */
	void save(Recommend model) throws Exception;

	/**
	 * 根据编号查找
	 * @param recommendId
	 * @return
	 * @throws Exception
	 */
	Recommend getById(int recommendId) throws Exception;

	/**
	 * 更新
	 * @param model
	 * @throws Exception
	 */
	void update(Recommend model) throws Exception;

}
