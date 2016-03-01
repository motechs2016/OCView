package org.blue.backend.navication.service;

import java.sql.Connection;
import java.util.List;

import org.blue.backend.navication.domain.Navication;

/**
 * “导航”服务接口
 * @author ldc4
 */
public interface NavicationService {

	/**
	 * 按父id查找列表
	 * @return
	 * @throws Exception
	 */
	List<Navication> findByParentId(int parentId) throws Exception;

	/**
	 * 删除
	 * @param navicationIds
	 * @throws Exception
	 */
	void delete(int[] navicationIds) throws Exception;

	/**
	 * 找到要删除的id列表(辅助删除)
	 * @param navicationId
	 * @throws Exception
	 */
	void findIdList(int navicationId,Connection conn,List<Integer> ids) throws Exception;

	/**
	 * 按id查找
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	Navication getById(int navicationId) throws Exception;

	/**
	 * 返回顶级列表（包括所有的下级）
	 * @param lazy1
	 * @param lazy2
	 * @return
	 * @throws Exception
	 */
	List<Navication> findTopList(boolean lazy1,boolean lazy2) throws Exception;
	
	/**
	 * 递归查找下级（辅助返回顶级列表）
	 * @param navicationId
	 * @param conn
	 * @param lazy
	 * @param children
	 * @throws Exception
	 */
	void findChildren(Navication navication,Connection conn,boolean lazy) throws Exception;

	/**
	 * 保存
	 * @param model
	 * @throws Exception
	 */
	void save(Navication model) throws Exception;

	/**
	 * 更新
	 * @param model
	 * @throws Exception
	 */
	void update(Navication model) throws Exception;

	/**
	 * 上移
	 * @param navicationId
	 * @throws Exception
	 */
	void moveUp(int navicationId) throws Exception;

	/**
	 * 下移
	 * @param navicationId
	 * @throws Exception
	 */
	void moveDown(int navicationId) throws Exception;
	
}
