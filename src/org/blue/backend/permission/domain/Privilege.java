package org.blue.backend.permission.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限类
 * @author ldc4
 */
public class Privilege implements Comparable<Privilege>,Serializable {
	
	private static final long serialVersionUID = 1L;

	//主属性
	private int privilegeId;		//权限编号
	
	//基本属性
	private String privilegeName;	//权限名称(后台栏目和操作连接)
	private String privilegeUrl;	//权限URL
	
	//关联属性
	private Set<Role> roles = new HashSet<Role>();
	private Privilege parent;//上级
	private Set<Privilege> children = new HashSet<Privilege>();//下级
	
	//====================================================================
	
	//无参构造方法
	public Privilege(){}
	//部分参数构造方法
	public Privilege(String privilegeName, String privilegeUrl, Privilege parent) {
		this.privilegeName = privilegeName;
		this.privilegeUrl = privilegeUrl;
		this.parent = parent;
	}
	//====================================================================
	
	//getter&setter方法
	public int getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(int privilegeId) {
		this.privilegeId = privilegeId;
	}
	public String getPrivilegeName() {
		return privilegeName;
	}
	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}
	public String getPrivilegeUrl() {
		return privilegeUrl;
	}
	public void setPrivilegeUrl(String privilegeUrl) {
		this.privilegeUrl = privilegeUrl;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Privilege getParent() {
		return parent;
	}
	public void setParent(Privilege parent) {
		this.parent = parent;
	}
	public Set<Privilege> getChildren() {
		return children;
	}
	public void setChildren(Set<Privilege> children) {
		this.children = children;
	}
	@Override
	public int compareTo(Privilege o) {
		return this.privilegeId - o.getPrivilegeId();
	}
}
