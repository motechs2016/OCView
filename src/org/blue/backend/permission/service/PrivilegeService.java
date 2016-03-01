package org.blue.backend.permission.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.blue.backend.permission.domain.Privilege;

/**
 * “权限”服务接口
 * @author ldc4
 */
public interface PrivilegeService {

	/**
	 * 保存
	 * @param menu
	 * @throws SQLException
	 */
	void save(Privilege menu) throws SQLException;
	/**
	 * 查询所有
	 * @return
	 * @throws SQLException
	 */
	List<Privilege> findAll() throws SQLException;
	/**
	 * 查找所有的顶级(包含children)
	 * @return
	 * @throws SQLException
	 */
	List<Privilege> findTopList() throws SQLException;
	/**
	 * 查找下级
	 * @param parent
	 * @return
	 * @throws SQLException
	 */
	List<Privilege> findChildren(Privilege parent) throws SQLException;
	/**
	 * 得到所有URL
	 * @return
	 * @throws SQLException
	 */
	Collection<String> getAllPrivilgeUrls() throws SQLException;
	/**
	 * 按id查找
	 * @param privilegeId
	 * @return
	 */
	Privilege getById(int privilegeId) throws SQLException;
	
}
