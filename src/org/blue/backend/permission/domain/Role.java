package org.blue.backend.permission.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色类
 * @author ldc4
 */
public class Role implements Comparable<Role>,Serializable{
	
	private static final long serialVersionUID = 1L;

	//主属性
	private int roleId;				//角色编号
	
	//基本属性
	private String roleName;		//角色名称
	private String roleDescription;	//角色描述
	
	//关联属性
	private Set<Role> roles = new HashSet<Role>();
	private Set<Privilege> privileges  = new HashSet<Privilege>();
	
	//====================================================================

	//无参构造方法
	public Role(){}
	//全参构造方法
	public Role(int roleId, String roleName, String roleDescription, Set<Role> roles, Set<Privilege> privileges) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDescription = roleDescription;
		this.roles = roles;
		this.privileges = privileges;
	}
	
	//====================================================================
	
	//getter&setter方法
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	@Override
	public int compareTo(Role o) {
		return o.getRoleId()-this.roleId;
	}
}
