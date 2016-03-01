package org.blue.backend.permission.service.join;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.blue.backend.util.JdbcUtils;

/**
 * admin和role连接服务
 * @author ldc4
 */
public class Admin2RoleService {

	private Connection conn = null;
	
	public Admin2RoleService() throws SQLException{
		conn = JdbcUtils.getConnection();
	}
	
	/**
	 * 取出amdin对应的所有role的id
	 * @param adminId
	 * @return
	 * @throws Exception
	 */
	public int[] findAllIds(int adminId)throws Exception{
		String sql = "select roleId from join_admin_role where adminId = ? order by roleId asc";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, adminId);
		ResultSet rs = pstmt.executeQuery();
		//移动到最后一行之后
		rs.last();
		//获取最后一行之后的编号
		int count = rs.getRow();
		//移动到第一行之前
		rs.beforeFirst();
		int[] roleIds = new int[count];
		int index = 0;
		while(rs.next()){
			roleIds[index++] = rs.getInt("roleId");
		}
		JdbcUtils.free(rs, pstmt, conn);
		return roleIds;
	}
	
	/**
	 * 保存新增的admin对应的roleid
	 * @param adminId
	 * @param roleIds
	 * @throws Exception
	 */
	@Deprecated
	public void saveRoleIds(int adminId,int[] roleIds) throws Exception{
		//不知道为何这个方法抛 Got error -1 from storage engine异常
		conn.setAutoCommit(false);
		String sql = "delete from join_admin_role where adminId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, adminId);
		try{
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
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			e.printStackTrace();
		}
		JdbcUtils.free(null, pstmt, conn);
	}
	
	/**
	 * 删除指定admin的roleid
	 * @param adminId
	 * @throws Exception
	 */
	public void deleteRoleIds(int adminId) throws Exception{
		String sql = "delete from join_admin_role where adminId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, adminId);
		pstmt.executeUpdate();
		JdbcUtils.free(null, pstmt, conn);
	}
	/**
	 * 删除指定role的adminId
	 * @param roleId
	 * @throws Exception
	 */
	public void deleteAdminIds(int roleId) throws Exception{
		String sql = "delete from join_admin_role where roleId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, roleId);
		pstmt.executeUpdate();
		JdbcUtils.free(null, pstmt, conn);
	}
	
}
