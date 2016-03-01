package org.blue.backend.permission.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.codec.digest.DigestUtils;
import org.blue.backend.permission.domain.Admin;
import org.blue.backend.permission.domain.Privilege;
import org.blue.backend.permission.domain.Role;
import org.blue.backend.permission.service.AdminService;
import org.blue.backend.permission.service.PrivilegeService;
import org.blue.backend.permission.service.RoleService;
import org.blue.backend.permission.service.join.Admin2RoleService;
import org.blue.backend.permission.service.join.Role2PrivilegeService;
import org.blue.backend.util.JdbcUtils;
import org.blue.backend.util.PageBean;

/**
 * “管理员”服务实现类
 * @author ldc4
 */
public class AdminServiceImpl implements AdminService{

	private Connection conn = null;
	
	public AdminServiceImpl() throws SQLException{
		conn = JdbcUtils.getConnection();
	}
	
	@Override
	public void save(Admin admin,int[] roleIds) throws Exception {
		conn.setAutoCommit(false);
		String sql = "insert into admin(adminName,adminAccount,adminPassword,adminEmail,adminPhone,adminInfo,createDate,updateDate,loginTimes,lastLoginIP,lastLoginDate) " +
				"values(?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = null;
		try{
			pstmt.setString(1, admin.getAdminName());
			pstmt.setString(2, admin.getAdminAccount());
			String password = DigestUtils.md5Hex(admin.getAdminPassword());//初始密码
			pstmt.setString(3, password);
			pstmt.setString(4, admin.getAdminEmail());
			pstmt.setString(5, admin.getAdminPhone());
			pstmt.setString(6, admin.getAdminInfo());
			pstmt.setTimestamp(7, admin.getCreateDate());
			pstmt.setTimestamp(8, admin.getUpdateDate());
			pstmt.setInt(9, admin.getLoginTimes());
			pstmt.setString(10, admin.getLastLoginIP());
			pstmt.setTimestamp(11, admin.getLastLoginDate());
			pstmt.executeUpdate();
			//设置角色
			if(roleIds!=null&&roleIds.length>0){
				//获取插入role后的自增长id
				rs = pstmt.getGeneratedKeys();
				int adminId = 0;
				if(rs.next()) {
					adminId = ((Long)rs.getObject(1)).intValue();
		        }
				//在连接表中保存数据
//				Admin2RoleService joinService = new Admin2RoleService();
//				joinService.saveRoleIds(adminId, roleIds);
				sql = "delete from join_admin_role where adminId = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, adminId);
				pstmt.executeUpdate();
				sql = "insert into join_admin_role(adminId,roleId) values(?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, adminId);
				if(roleIds!=null){
					for(int roleId : roleIds){
						pstmt.setInt(2, roleId);
						pstmt.executeUpdate();
					}
				}
			}
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
		JdbcUtils.free(rs, pstmt, conn);
	}

	@Override
	public void delete(int[] ids) throws Exception {
		conn.setAutoCommit(false);
		String sql = "delete from admin where adminId=? and adminId!=0";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		try{
			for(int i=0;i<ids.length;i++){
				pstmt.setInt(1, ids[i]);
				pstmt.executeUpdate();
				//删除连接表中的数据
				Admin2RoleService joinService = new Admin2RoleService();
				joinService.deleteRoleIds(ids[i]);
			}
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
		JdbcUtils.free(null, pstmt, conn);
	}

	@Override
	public void update(Admin admin,int[] roleIds) throws Exception {
		conn.setAutoCommit(false);
		String sql = "update admin set adminName=?,adminEmail=?,adminPhone=?,adminInfo=?,updateDate=? where adminId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		try{
			pstmt.setString(1, admin.getAdminName());
			pstmt.setString(2, admin.getAdminEmail());
			pstmt.setString(3, admin.getAdminPhone());
			pstmt.setString(4, admin.getAdminInfo());
			pstmt.setTimestamp(5, admin.getUpdateDate());
			pstmt.setInt(6, admin.getAdminId());
			pstmt.executeUpdate();
			//设置角色
			if(roleIds!=null&&roleIds.length>0){
				//在连接表中更新数据
//				Admin2RoleService joinService = new Admin2RoleService();
//				joinService.saveRoleIds(admin.getAdminId(), roleIds);
				sql = "delete from join_admin_role where adminId = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, admin.getAdminId());
				pstmt.executeUpdate();
				sql = "insert into join_admin_role(adminId,roleId) values(?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, admin.getAdminId());
				if(roleIds!=null){
					for(int roleId : roleIds){
						pstmt.setInt(2, roleId);
						pstmt.executeUpdate();
					}
				}
			}
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
		JdbcUtils.free(null, pstmt, conn);
	}

	@Override
	public Admin getById(int id) throws Exception {
		Admin admin = new Admin();
		String sql = "select * from admin where adminId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			admin.setAdminId(id);
			admin.setAdminAccount(rs.getString("adminAccount"));
			admin.setAdminName(rs.getString("adminName"));
			admin.setAdminEmail(rs.getString("adminEmail"));
			admin.setAdminPhone(rs.getString("adminPhone"));
			admin.setAdminInfo(rs.getString("adminInfo"));
			admin.setCreateDate(rs.getTimestamp("createDate"));
			admin.setUpdateDate(rs.getTimestamp("updateDate"));
			admin.setLoginTimes(rs.getInt("loginTimes"));
			admin.setLastLoginDate(rs.getTimestamp("lastLoginDate"));
			admin.setLastLoginIP(rs.getString("lastLoginIP"));
			//查询所有角色的名称：
			//1.去连接表中取得对应的id
			Admin2RoleService joinService = new Admin2RoleService();
			int[] roleIds = joinService.findAllIds(id);
			//2.查找id对应的所有roleName
			RoleService roleService = new RoleServiceImpl();
			List<Role> roles = roleService.getNamesByIds(roleIds);
			admin.setRoles(new TreeSet<Role>(roles));
		}
		JdbcUtils.free(rs, pstmt, conn);
		return admin;
	}

	@Deprecated
	public List<Admin> findAllSimple() throws Exception {
		List<Admin> adminList = new ArrayList<Admin>();
		String sql = "select adminId,adminName,adminAccount from admin";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		Admin admin = null;
		while(rs.next()){
			
			admin = new Admin();
			
			//后续操作要用
			int adminId = rs.getInt("adminId");
			admin.setAdminId(adminId);
			admin.setAdminName(rs.getString("adminName"));
			admin.setAdminAccount(rs.getString("adminAccount"));
			
			//查询所有角色的名称：
			//1.去连接表中取得对应的id
			Admin2RoleService joinService = new Admin2RoleService();
			int[] roleIds = joinService.findAllIds(adminId);
			//2.查找id对应的所有roleName
			RoleService roleService = new RoleServiceImpl();
			List<Role> roles = roleService.getNamesByIds(roleIds);
			admin.setRoles(new TreeSet<Role>(roles));
			
			adminList.add(admin);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return adminList;
	}


	@Override
	public void updatePassword(int adminId,String adminPassword) throws Exception{
		String sql = "update admin set adminPassword=? where adminId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, adminPassword);
		pstmt.setInt(2, adminId);
		pstmt.executeUpdate();
		JdbcUtils.free(null, pstmt, conn);
	}


	@Override
	public boolean login(Admin model) throws Exception {
		conn.setAutoCommit(false);
		boolean flag = false;
		String sql = "select * from admin where adminAccount = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, model.getAdminAccount());
		ResultSet rs = null;
		
		try{
			rs = pstmt.executeQuery();
			//如果存在用户名
			if(rs.next()){
				//如果密码匹配
				if(model.getAdminPassword().equals(rs.getString("adminPassword"))){
					int adminId = rs.getInt("adminId");
					model.setAdminId(adminId);
					model.setAdminName(rs.getString("adminName"));
					model.setAdminEmail(rs.getString("adminEmail"));
					model.setAdminPhone(rs.getString("adminPhone"));
					model.setAdminInfo(rs.getString("adminInfo"));
					model.setCreateDate(rs.getTimestamp("createDate"));
					model.setUpdateDate(rs.getTimestamp("updateDate"));
					model.setLoginTimes(rs.getInt("loginTimes")+1);
					model.setLastLoginDate(rs.getTimestamp("lastLoginDate"));
					model.setLastLoginIP(rs.getString("lastLoginIp"));
					//设置拥有的权限
					//1.获取角色id
					Admin2RoleService joinService1 = new Admin2RoleService();
					int[] roleIds = joinService1.findAllIds(adminId);
					//2.循环遍历
					int[] privilegeIds = null;
					Role role = null;
					List<Role> roleList = new ArrayList<Role>();
					for(int roleId : roleIds){
						//取得角色名等信息
						RoleService roleService = new RoleServiceImpl();
						role = roleService.getById(roleId);
						//2-a.获取权限id
						Role2PrivilegeService joinService2 = new Role2PrivilegeService();
						privilegeIds = joinService2.findAllIds(roleId);
						//2-b.循环遍历
						List<Privilege> privilegeList = new ArrayList<Privilege>();
						Privilege privilege = null;
						for(int privilegeId:privilegeIds){
							//获取权限服务对象
							PrivilegeService privilegeService = new PrivilegeServiceImpl();
							//获取权限
							privilege = privilegeService.getById(privilegeId);
							//添加到集合中
							if(privilege!=null){
								privilegeList.add(privilege);
							}
						}
						//如果换成TreeSet会有问题：只有一个privilege
						//原因是：没有设置privilegeId.由于又是int型的,默认是0.当比较对象的时候永远相等
						//故这里不用固定权限的位置，用HashSet足够了
						role.setPrivileges(new HashSet<Privilege>(privilegeList));
						roleList.add(role);
					}
					//最后添加到admin的roles中
					model.setRoles(new HashSet<Role>(roleList));
					//更新相关记录
					sql = "update admin set loginTimes=loginTimes+1,lastLoginDate=?,lastLoginIP=? where adminId=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setTimestamp(1, model.getLoginDate());
					pstmt.setString(2, model.getLoginIP());
					pstmt.setInt(3, model.getAdminId());
					pstmt.executeUpdate();
					flag = true;
				}
			}
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
		JdbcUtils.free(rs, pstmt, conn);
		return flag;
	}


	@Override
	public PageBean getPageBean(int pageNum, int pageSize) throws Exception {
		//2次查询，不需要使用事务
		List<Admin> recordList = new ArrayList<Admin>();
		String sql = "select adminId,adminName,adminAccount,adminInfo from admin limit ?,?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, pageSize*(pageNum-1));
		pstmt.setInt(2, pageSize);
		ResultSet rs = pstmt.executeQuery();
		Admin admin = null;
		while(rs.next()){
			admin = new Admin();
			admin.setAdminId(rs.getInt("adminId"));
			admin.setAdminName(rs.getString("adminName"));
			admin.setAdminAccount(rs.getString("adminAccount"));
			admin.setAdminInfo(rs.getString("adminInfo"));
			recordList.add(admin);
		}
		//查询总记录数
		sql = "select count(*) from admin";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		int recordCount = 0;
		if(rs.next()){
			recordCount = rs.getInt(1);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return new PageBean(pageNum, pageSize, recordCount, recordList);
	}

	@Override
	public boolean uniqueAccount(String adminAccount) throws Exception {
		boolean flag = false;
		String sql = "select count(*) from admin where adminAccount = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, adminAccount);
		ResultSet rs = pstmt.executeQuery();
		int recordCount = -1;
		if(rs.next()){
			recordCount = rs.getInt(1);
		}
		if(recordCount==0){
			flag = true;
		}
		return flag;
	}

}
