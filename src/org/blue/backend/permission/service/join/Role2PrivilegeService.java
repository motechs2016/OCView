package org.blue.backend.permission.service.join;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.blue.backend.util.JdbcUtils;

/**
 * role和privilege连接服务
 * @author ldc4
 */
public class Role2PrivilegeService {

	private Connection conn = null;
	
	public Role2PrivilegeService() throws SQLException{
		conn = JdbcUtils.getConnection();
	}
	
	/**
	 * 保存
	 * @param roleId
	 * @param privilegeIds
	 * @throws SQLException
	 */
	public void savePrivilegeIds(int roleId, int[] privilegeIds) throws SQLException {
		conn.setAutoCommit(false);
		String sql = "delete from join_role_privilege where roleId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, roleId);
		try{
			pstmt.executeUpdate();
			sql = "insert into join_role_privilege(roleId,privilegeId) values(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roleId);
			if(privilegeIds!=null){
				for(int privilegeId : privilegeIds){
					pstmt.setInt(2, privilegeId);
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
	 * 按角色查找权限id
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	public int[] findAllIds(int roleId) throws SQLException {
		String sql = "select privilegeId from join_role_privilege where roleId = ? order by roleId asc";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, roleId);
		ResultSet rs = pstmt.executeQuery();
		//移动到最后一行之后
		rs.last();
		//获取最后一行之后的编号
		int count = rs.getRow();
		//移动到第一行之前
		rs.beforeFirst();
		int[] privilegeIds = new int[count];
		int index = 0;
		while(rs.next()){
			privilegeIds[index++] = rs.getInt("privilegeId");
		}
		JdbcUtils.free(rs, pstmt, conn);
		return privilegeIds;		
	}
	
}
