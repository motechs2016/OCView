package org.blue.backend.permission.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.blue.backend.permission.domain.Privilege;
import org.blue.backend.permission.service.PrivilegeService;
import org.blue.backend.util.JdbcUtils;

/**
 * “权限”服务实现类
 * @author ldc4
 */
public class PrivilegeServiceImpl implements PrivilegeService{

	private Connection conn = null;
	
	public PrivilegeServiceImpl() throws SQLException{
		conn = JdbcUtils.getConnection();
	}
	
	@Override
	public void save(Privilege menu) throws SQLException {
		String sql = "insert into privilege(privilegeName,privilegeUrl,parentId) values(?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, menu.getPrivilegeName());
		pstmt.setString(2, menu.getPrivilegeUrl());
		if(menu.getParent()!=null){
			pstmt.setInt(3, menu.getParent().getPrivilegeId());
		}else{
			pstmt.setInt(3, 0);
		}
		pstmt.executeUpdate();
		//获取插入role后的自增长id
		ResultSet rs = pstmt.getGeneratedKeys();
		int priId = 0;
		if(rs.next()) {
			priId = ((Long)rs.getObject(1)).intValue();
        }
		//设置插入后的id值
		menu.setPrivilegeId(priId);
		JdbcUtils.free(rs, pstmt, conn);
	}

	@Override
	public List<Privilege> findAll() throws SQLException {
		List<Privilege> privilegeList = new ArrayList<Privilege>();
		String sql = "select * from privilege";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		Privilege privilege = null;
		while(rs.next()){
			privilege = new Privilege();
			privilege.setPrivilegeId(rs.getInt("privilegeId"));
			privilege.setPrivilegeName(rs.getString("privilegeName"));
			privilege.setPrivilegeUrl(rs.getString("privilegeUrl"));
			Privilege parent = new Privilege();
			parent.setPrivilegeId(rs.getInt("parentId"));
			privilege.setParent(parent);
			privilegeList.add(privilege);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return privilegeList;
	}

	@Override
	public List<Privilege> findTopList() throws SQLException {
		List<Privilege> topPrivilegeList = new ArrayList<Privilege>();
		String sql = "select * from privilege where parentId = 0";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		Privilege privilege = null;
		while(rs.next()){
			privilege = new Privilege();
			privilege.setPrivilegeId(rs.getInt("privilegeId"));
			privilege.setPrivilegeName(rs.getString("privilegeName"));
			List<Privilege> children = new ArrayList<Privilege>();
			PrivilegeService privilegeService = new PrivilegeServiceImpl();
			children = privilegeService.findChildren(privilege);
			privilege.setChildren(new TreeSet<Privilege>(children));
			topPrivilegeList.add(privilege);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return topPrivilegeList;
	}
	
	@Override
	public List<Privilege> findChildren(Privilege parent) throws SQLException {
		List<Privilege> children = new ArrayList<Privilege>();
		String sql = "select * from privilege where parentId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		//设置条件：上级id
		pstmt.setInt(1, parent.getPrivilegeId());
		ResultSet rs = pstmt.executeQuery();
		Privilege privilege = null;
		if(rs.next()){
			do{
				privilege = new Privilege();
				privilege.setPrivilegeId(rs.getInt("privilegeId"));
				privilege.setPrivilegeName(rs.getString("privilegeName"));
				privilege.setPrivilegeUrl(rs.getString("privilegeUrl"));
				//设置下级的下级
				List<Privilege> grandson = new ArrayList<Privilege>();
				//递归调用
				PrivilegeService privilegeService = new PrivilegeServiceImpl();
				grandson = privilegeService.findChildren(privilege);
				if(grandson!=null){
					privilege.setChildren(new TreeSet<Privilege>(grandson));
				}
				children.add(privilege);
			}while(rs.next());
			JdbcUtils.free(rs, pstmt, conn);
			return children;
		}else{
			JdbcUtils.free(rs, pstmt, conn);
			return null;
		}
	}
	
	@Override
	public Collection<String> getAllPrivilgeUrls() throws SQLException {
		Collection<String> urls = new ArrayList<String>();
		String sql = "select privilegeUrl from privilege where privilegeUrl is not null";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			urls.add(rs.getString("privilegeUrl"));
		}
		JdbcUtils.free(rs, pstmt, conn);
		return urls;
	}

	@Override
	public Privilege getById(int privilegeId) throws SQLException {
		Privilege privilege = null;
		//privilegeUrl不为空，会导致取不到顶级菜单栏：如“系统管理”  and privilegeUrl is not null
		String sql = "select privilegeUrl,privilegeName from privilege where privilegeId = ? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, privilegeId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			privilege = new Privilege();
			privilege.setPrivilegeUrl(rs.getString("privilegeUrl"));
			privilege.setPrivilegeName(rs.getString("privilegeName"));
		}
		JdbcUtils.free(rs, pstmt, conn);
		return privilege;
	}

}
