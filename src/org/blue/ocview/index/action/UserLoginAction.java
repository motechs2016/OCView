package org.blue.ocview.index.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.blue.backend.user.domain.User;
import org.blue.backend.user.service.UserService;
import org.blue.backend.user.service.impl.UserServiceImpl;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 用户登陆
 * @author ldc4
 */
public class UserLoginAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private String userAccount;
	private String userPassword;
	
	private InputStream result;
	
	
	public String execute() throws Exception {
		
		//校验
		if(userAccount==null||userAccount.equals("")){
			result = new ByteArrayInputStream("账号不能为空".getBytes("UTF-8"));
			return "success";
		}
		if(userPassword==null||userPassword.equals("")){
			result = new ByteArrayInputStream("密码不能为空".getBytes("UTF-8"));
			return "success";
		}
		
		//封装
		User user = new User();
		user.setUserAccount(userAccount);
		user.setUserPassword(DigestUtils.md5Hex(userPassword));
		
		//登陆操作
		UserService userService = new UserServiceImpl();
		if(userService.login(user)){
			//放入session范围内
			ActionContext.getContext().getSession().put("user", user);
			result = new ByteArrayInputStream(("OK"+user.getUserNickname()).getBytes("UTF-8"));
		}else{
			result = new ByteArrayInputStream("您输入的账号或密码有误".getBytes("UTF-8"));
		}
		
		return "success";
	}
	
	//getter&setter
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public InputStream getResult() {
		return result;
	}
	public void setResult(InputStream result) {
		this.result = result;
	}
}
