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
 * 用户注册
 * @author ldc4
 */
public class UserRegistAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String userAccount;
	private String userPassword;
	private String confirmPassword;
	private String userEmail;
	private String userNickname;
	
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
		if(confirmPassword==null||confirmPassword.equals("")){
			result = new ByteArrayInputStream("确认密码不能为空".getBytes("UTF-8"));
			return "success";
		}
		if(userNickname==null||userNickname.equals("")){
			result = new ByteArrayInputStream("昵称不能为空".getBytes("UTF-8"));
			return "success";
		}
		if(userEmail==null||userEmail.equals("")){
			result = new ByteArrayInputStream("邮箱不能为空".getBytes("UTF-8"));
			return "success";
		}
		if(!userPassword.equals(confirmPassword)){
			result = new ByteArrayInputStream("两次输入的密码不一致".getBytes("UTF-8"));
			return "success";
		}
		
		//封装
		User user = new User();
		user.setUserAccount(userAccount);
		//乱码处理。话说全局得到字符编码过滤器怎么没起到作用？
		user.setUserNickname(new String(userNickname.getBytes("ISO8859-1"),"UTF-8"));
		user.setUserPassword(userPassword);
		user.setUserEmail(userEmail);
		
		//注册操作
		UserService userService = new UserServiceImpl();
		userService.save(user);
		//注册后默认登陆
		userService = new UserServiceImpl();
		//登陆的密码为MD5加密
		user.setUserPassword(DigestUtils.md5Hex(userPassword));
		if(userService.login(user)){
			//放入session范围内
			ActionContext.getContext().getSession().put("user", user);
			result = new ByteArrayInputStream(("OK"+user.getUserNickname()).getBytes("UTF-8"));
		}else{
			result = new ByteArrayInputStream("您输入的账号或密码有误".getBytes("UTF-8"));
		}
		
		return "success";
	}


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
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public InputStream getResult() {
		return result;
	}
	public void setResult(InputStream result) {
		this.result = result;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
}
