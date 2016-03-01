package org.blue.backend.permission.service;

import java.util.List;

import org.blue.backend.permission.domain.Admin;
import org.blue.backend.util.PageBean;

/**
 * “管理员”服务接口
 * @author ldc4
 */
public interface AdminService {

	/**
	 * 保存
	 * @param admin
	 * @throws Exception
	 */
	void save(Admin admin,int[] roleIds) throws Exception;
	/**
	 * 删除
	 * @param id
	 * @throws Exception
	 */
	void delete(int[] ids) throws Exception;
	/**
	 * 修改
	 * @param admin
	 * @throws Exception
	 */
	void update(Admin admin,int[] roleIds) throws Exception;
	/**
	 * 按ID查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Admin getById(int id) throws Exception;
	/**
	 * 查询所有(简略)
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	List<Admin> findAllSimple() throws Exception;
	/**
	 * 修改密码
	 * @param model
	 * @throws Exception
	 */
	void updatePassword(int adminId,String adminPassword) throws Exception;
	/**
	 * 登陆
	 * @param model
	 * @return
	 * @throws Exception
	 */
	boolean login(Admin model) throws Exception;
	/**
	 * 分页查询
	 * @return
	 */
	PageBean getPageBean(int pageNum, int pageSize) throws Exception;
	/**
	 * 检查唯一账号
	 * @param adminAccount
	 * @return
	 * @throws Exception
	 */
	boolean uniqueAccount(String adminAccount) throws Exception;
	
}
