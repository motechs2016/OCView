package org.blue.backend.user.domain;

import org.apache.struts2.json.annotations.JSON;


/**
 * 普通用户类
 * @author ldc4
 */
public class User {
	//主属性
	private int userId;	//用户编号
	//基本属性
	private String userNickname; //用户昵称
	private String userAccount; //用户账号
	private String userPassword; //用户密码
	private String userEmail;	//用户邮箱
	
	//getter&setter方法
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	@JSON(serialize=false)
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
