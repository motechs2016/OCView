package org.blue.backend.permission.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.blue.backend.permission.domain.Role;
import org.blue.backend.permission.service.RoleService;
import org.blue.backend.permission.service.join.Admin2RoleService;
import org.blue.backend.permission.service.join.Role2PrivilegeService;
import org.blue.backend.util.JdbcUtils;
import org.blue.backend.util.PageBean;

/**
 * “角色”服务实现类<br>
 * 一个业务平均只有一个dao方法，所以不调用Dao了，直接采用2层架构
 * @author ldc4
 */
public class RoleServiceImpl implements RoleService{

	private Connection conn = null;
	
	public RoleServiceImpl() throws SQLException{
		conn = JdbcUtils.getConnection();
	}
	
	
	@Override
	public List<Role> findAll() throws SQLException {
		List<Role> roleList = new ArrayList<Role>();
		String sql = "select * from role";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		Role role = null;
		while(rs.next()){
			role = new Role();
			role.setRoleId(rs.getInt("roleId"));
			role.setRoleName(rs.getString("roleName"));
			role.setRoleDescription(rs.getString("roleDescription"));
			roleList.add(role);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return roleList;
	}

	@Override
	public void save(Role role) throws SQLException{
		String sql = "insert into role(roleName,roleDescription) values(?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, role.getRoleName());
		pstmt.setString(2, role.getRoleDescription());
		pstmt.executeUpdate();
		JdbcUtils.free(null, pstmt, conn);
	}

	@Override
	public void delete(int[] ids) throws SQLException {
		conn.setAutoCommit(false);
		String sql = "delete from role where roleId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		try{
			for(int i=0;i<ids.length;i++){
				//删除权限角色连接表的数据
				Role2PrivilegeService joinService1 = new Role2PrivilegeService();
				joinService1.savePrivilegeIds(ids[i], null);
				//删除管理员角色连接表的数据
				Admin2RoleService joinService2 = new Admin2RoleService();
				joinService2.deleteAdminIds(ids[i]);
				//删除角色
				pstmt.setInt(1, ids[i]);
				pstmt.executeUpdate();
			}
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
		JdbcUtils.free(null, pstmt, conn);
	}

	@Override
	public void update(Role role) throws SQLException{
		String sql = "update role set roleName=?,roleDescription=? where roleId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, role.getRoleName());
		pstmt.setString(2, role.getRoleDescription());
		pstmt.setInt(3, role.getRoleId());
		pstmt.executeUpdate();
		JdbcUtils.free(null, pstmt, conn);
	}

	@Override
	public Role getById(int id) throws SQLException {
		Role role = new Role();
		String sql = "select * from role where roleId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			role.setRoleId(rs.getInt("roleId"));
			role.setRoleName(rs.getString("roleName"));
			role.setRoleDescription(rs.getString("roleDescription"));
		}
		JdbcUtils.free(rs, pstmt, conn);
		return role;
	}


	@Override
	public List<Role> getNamesByIds(int[] ids) throws SQLException {
		List<Role> roleList = new ArrayList<Role>();
		String sql = "select roleId,roleName from role where roleId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = null;
		Role role = null;
		for(int id : ids){
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			role = new Role();
			if(rs.next()){
				role.setRoleId(rs.getInt("roleId"));
				role.setRoleName(rs.getString("roleName"));
			}
			roleList.add(role);
		}
		JdbcUtils.free(rs, pstmt, conn); 
		return roleList;
	}


	@Override
	public List<Role> findAllNames() throws SQLException {
		List<Role> roleList = new ArrayList<Role>();
		String sql = "select roleName from role";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		Role role = null;
		while(rs.next()){
			role = new Role();
			role.setRoleName(rs.getString("roleName"));
			roleList.add(role);
		}
		JdbcUtils.free(rs, pstmt, conn); 
		return roleList;
	}


	@Override
	public PageBean getPageBean(int pageNum, int pageSize) throws Exception {
		//2次查询，不需要使用事务
		List<Role> recordList = new ArrayList<Role>();
		String sql = "select * from role limit ?,?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, pageSize*(pageNum-1));
		pstmt.setInt(2, pageSize);
		ResultSet rs = pstmt.executeQuery();
		Role role = null;
		while(rs.next()){
			role = new Role();
			role.setRoleId(rs.getInt("roleId"));
			role.setRoleName(rs.getString("roleName"));
			role.setRoleDescription(rs.getString("roleDescription"));
			recordList.add(role);
		}
		//查询总记录数
		sql = "select count(*) from role";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		int recordCount = 0;
		if(rs.next()){
			recordCount = rs.getInt(1);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return new PageBean(pageNum, pageSize, recordCount, recordList);
	}

}
