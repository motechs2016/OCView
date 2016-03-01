package org.blue.backend.permission.service;

import java.util.List;

import org.blue.backend.permission.domain.Role;
import org.blue.backend.util.PageBean;

/**
 * “角色”服务接口
 * @author ldc4
 */
public interface RoleService{
	
	/**
	 * 保存
	 * @param role
	 * @throws Exception
	 */
	void save(Role role) throws Exception;
	/**
	 * 删除
	 * @param ids
	 * @throws Exception
	 */
	void delete(int[] ids) throws Exception;
	/**
	 * 修改
	 * @param role
	 * @throws Exception
	 */
	void update(Role role) throws Exception;
	/**
	 * 按ID查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Role getById(int id) throws Exception;
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	List<Role> findAll() throws Exception;
	/**
	 * 按多个id查询角色名
	 * @param roleIds
	 * @return
	 * @throws Exception
	 */
	List<Role> getNamesByIds(int[] ids) throws Exception;
	/**
	 * 查询所有角色名
	 * @return
	 */
	List<Role> findAllNames() throws Exception;
	/**
	 * 分页查询
	 * @return
	 */
	PageBean getPageBean(int pageNum, int pageSize) throws Exception;
	
}
