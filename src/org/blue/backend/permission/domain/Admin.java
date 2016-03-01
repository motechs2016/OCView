package org.blue.backend.permission.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;

/**
 * 管理员类
 * @author ldc4
 */
public class Admin implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//主属性
	private int adminId;			//管理员编号
	
	//基本属性
	private String adminName;		//管理员姓名
	private String adminAccount;	//管理员账号
	private String adminPassword;	//管理员密码
	private String adminEmail;		//管理员邮箱
	private String adminPhone;		//管理员电话
	private String adminInfo;		//管理员备注信息
	
	//特殊属性
	private Date createDate;		//创建日期
	private Date updateDate;		//修改日期
	private int loginTimes;			//登陆次数
	private String loginIP;			//登陆IP(临时)
	private String lastLoginIP;		//上次登陆IP
	private Date loginDate;			//登陆时间(临时)
	private Date lastLoginDate;		//上次登陆时间
	
	//关联属性
	private Set<Role> roles = new HashSet<Role>();
	
	//====================================================================
	
	//无参构造方法
	public Admin() {}
	//账号密码构造方法
	public Admin(String adminAccount, String adminPassword) {
		this.adminAccount = adminAccount;
		this.adminPassword = adminPassword;
	}
	
	//====================================================================
	
	//通过权限名判断权限
	public boolean hasPrivilegeByName(String name){
		//超级管理员
		if(isAdmin()){
			return true;
		}
		
		//针对普通用户的
		for(Role role : roles){
			for(Privilege priv : role.getPrivileges()){
				if(priv.getPrivilegeName().equals(name)){
					return true;
				}
			}
		}
		return false;
	}
	
	
	//根据URL判断权限
	public boolean hasPrivilegeByUrl(String privUrl){
		
		//超级管理员
		if(isAdmin()){
			return true;
		}
		
		//去掉后面的参数
    	int pos = privUrl.indexOf("?");
    	if(pos!=-1){
    		privUrl = privUrl.substring(0,pos);
    	}
    	//去掉UI后缀
    	if(privUrl.endsWith("UI")){
    		privUrl = privUrl.substring(0,privUrl.length() - 2);
    	}
    	//添加 '/'
    	if(!privUrl.startsWith("/")){
    		privUrl = "/"+privUrl;
    	}
		
    	
    	//针对普通用户的
    	@SuppressWarnings("unchecked")
		Collection<String> allPrivilegeUrls = (Collection<String>) ActionContext.getContext().getApplication().get("allPrivilegeUrls");
    	//如果不在需要判断权限的范围内就放行
    	if(!allPrivilegeUrls.contains(privUrl)){
    		return true;
    	}else{
			for(Role role : roles){
				for(Privilege priv : role.getPrivileges()){
					if(privUrl.equals(priv.getPrivilegeUrl())){
						return true;
					}
				}
			}
			return false;
    	}
		
	}
	
	public boolean isAdmin(){
		return "admin".equals(adminAccount);
	}
	
	//====================================================================
	
	//getter&setter方法
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getAdminAccount() {
		return adminAccount;
	}
	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	//修改日期返回类型
	public java.sql.Timestamp getCreateDate() {
		return new java.sql.Timestamp(createDate.getTime());
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	//修改日期返回类型
	public java.sql.Timestamp getUpdateDate() {
		return new java.sql.Timestamp(updateDate.getTime());
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public int getLoginTimes() {
		return loginTimes;
	}
	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}
	public String getLoginIP() {
		return loginIP;
	}
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	public String getLastLoginIP() {
		return lastLoginIP;
	}
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}
	//修改日期返回类型
	public java.sql.Timestamp getLoginDate() {
		return new java.sql.Timestamp(loginDate.getTime());
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	//修改日期返回类型
	public java.sql.Timestamp getLastLoginDate() {
		return new java.sql.Timestamp(lastLoginDate.getTime());
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getAdminPhone() {
		return adminPhone;
	}
	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}
	public String getAdminInfo() {
		return adminInfo;
	}
	public void setAdminInfo(String adminInfo) {
		this.adminInfo = adminInfo;
	}
	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", adminName=" + adminName
				+ ", adminAccount=" + adminAccount + ", adminPassword="
				+ adminPassword + ", adminEmail=" + adminEmail
				+ ", adminPhone=" + adminPhone + ", adminInfo=" + adminInfo
				+ ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", loginTimes=" + loginTimes + ", loginIP=" + loginIP
				+ ", lastLoginIP=" + lastLoginIP + ", loginDate=" + loginDate
				+ ", lastLoginDate=" + lastLoginDate + ", roles=" + roles + "]";
	}
}
