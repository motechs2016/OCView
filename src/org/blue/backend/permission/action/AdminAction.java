package org.blue.backend.permission.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.blue.backend.permission.domain.Admin;
import org.blue.backend.permission.domain.Role;
import org.blue.backend.permission.service.AdminService;
import org.blue.backend.permission.service.RoleService;
import org.blue.backend.permission.service.impl.AdminServiceImpl;
import org.blue.backend.permission.service.impl.RoleServiceImpl;
import org.blue.backend.util.BaseAction;
import org.blue.backend.util.PageBean;

import com.opensymphony.xwork2.ActionContext;

/**
 * “管理员”业务控制Action
 * @author ldc4
 */
public class AdminAction extends BaseAction<Admin>{
	
	private static final long serialVersionUID = 1L;
	
	private int[] roleIds;
	private int[] adminIds;
	private int pageNum = 1;//页数
	private int pageSize = 10;//每页显示多少条
	
	private String password = "";//密码
	private String passwordConfirm = "";//确认密码
	
	/**列表*/
	public String list() throws Exception{
		//准备数据
		AdminService adminService = new AdminServiceImpl();
		PageBean recordList = adminService.getPageBean(pageNum,pageSize);
		ActionContext.getContext().getValueStack().push(recordList);
		return "list";
	}
	/**详细信息*/
	public String details() throws Exception{
		//准备数据
		AdminService adminService = new AdminServiceImpl();
		Admin adminMsg = adminService.getById(model.getAdminId());
		ActionContext.getContext().put("adminMsg", adminMsg);
		return "details";
	}
	/**删除*/
	public String delete() throws Exception{
		//获取到一个服务对象
		AdminService adminService = new AdminServiceImpl();
		//从数据库中删除记录
		if(adminIds!=null){
			//不准删除自己
			for(int adminId:adminIds){
				if(adminId==((Admin)ActionContext.getContext().getSession().get("admin")).getAdminId()){
					return "toList";
				}
			}
			//执行删除(超级管理员不能删除，在SQL中体现)
			adminService.delete(adminIds);
		}
		return "toList";
	}
	/**添加页面*/
	public String addUI() throws Exception{
		//准备数据
		RoleService roleService = new RoleServiceImpl();
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		return "saveUI";
	}
	/**添加*/
	public String add() throws Exception{
		//获取到一个服务对象
		AdminService adminService = new AdminServiceImpl();
		Date currentTime = new Date();
		model.setCreateDate(currentTime);
		model.setUpdateDate(currentTime);
		model.setLastLoginDate(currentTime);
		model.setLastLoginIP(ServletActionContext.getRequest().getRemoteAddr());
		//初始密码
		model.setAdminPassword("123456");
		//model为ModelDriven自动封装的参数对象
		adminService.save(model,roleIds);
		return "toList";
	}
	/**修改页面*/
	public String editUI() throws Exception{
		//准备数据
		RoleService roleService = new RoleServiceImpl();
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		//回显
		AdminService adminService = new AdminServiceImpl();
		Admin adminMsg = adminService.getById(model.getAdminId());
		ActionContext.getContext().getValueStack().push(adminMsg);
		//回显复选框
		if (adminMsg.getRoles() != null) {
			roleIds = new int[adminMsg.getRoles().size()];
			int index = 0;
			for (Role role : adminMsg.getRoles()) {
				roleIds[index++] = role.getRoleId();
			}
		}
		return "saveUI";
	}
	/**修改*/
	public String edit() throws Exception{
		//获取到一个服务对象
		AdminService adminService = new AdminServiceImpl();
		model.setUpdateDate(new Date());
		//model为ModelDriven自动封装的参数对象
		adminService.update(model,roleIds);
		return "toList";
	}
	/**初始化密码*/
	public String initPassword() throws Exception {
		//获取到一个服务对象
		AdminService adminService = new AdminServiceImpl();
		//MD5加密
		String md5Password = DigestUtils.md5Hex("123456");
		//model为ModelDriven自动封装的参数对象
		adminService.updatePassword(model.getAdminId(),md5Password);
		return "toList";
	}
	/**修改密码界面*/
	public String changePasswordUI() throws Exception {
		return "changePasswordUI";
	} 
	/**修改密码*/
	public String changePassword() throws Exception {
		//获取到一个服务对象
		AdminService adminService = new AdminServiceImpl();
		//MD5加密
		String md5Password = DigestUtils.md5Hex(password);
		//model为ModelDriven自动封装的参数对象
		adminService.updatePassword(model.getAdminId(),md5Password);
		return "toIndex";
	}
	/**登陆界面*/
	public String loginUI() throws Exception{
		return "loginUI";
	}
	/**登陆*/
	public String login() throws Exception{
		//获取到一个服务对象
		AdminService adminService = new AdminServiceImpl();
		//如果直接访问该方法，model中没有值
		String adminPassword = model.getAdminPassword();
		if(adminPassword==null){
			return "loginUI";
		}
		//MD5加密
		String md5Password = DigestUtils.md5Hex(adminPassword);
		model.setAdminPassword(md5Password);
		//登陆时间
		model.setLoginDate(new Date());
		//登陆ip
		model.setLoginIP(ServletActionContext.getRequest().getRemoteAddr());
		//model为ModelDriven自动封装的参数对象
		if(adminService.login(model)){
			ActionContext.getContext().getSession().put("admin", model);
		}else{
			addFieldError("login","用户名和密码不正确");
			return "loginUI";
		}
		return "toIndex";
	}
	/**注销*/
	public String logout() throws Exception{
		ActionContext.getContext().getSession().remove("admin");
		return "logout";
	}
	
	//校验
	public void validateAdd() {
		try {
			AdminService adminService = new AdminServiceImpl();
			if(!adminService.uniqueAccount(model.getAdminAccount())){
				addFieldError("repeat", "用户名重复");
			}
			RoleService roleService = new RoleServiceImpl();
			for(int roleId:roleIds){
				if(roleService.getById(roleId).getRoleName()==null){
					addFieldError("roleError", "角色不存在");
					break;
				}
			}
		} catch (Exception e) {
			addFieldError("exception", "校验异常");
			e.printStackTrace();
		}
	}
	public void validateEdit() {
		try {
			for(int roleId:roleIds){
				RoleService roleService = new RoleServiceImpl();
				if(roleService.getById(roleId).getRoleName()==null){
					addFieldError("roleError", "角色不存在");
					break;
				}
			}
		} catch (Exception e) {
			addFieldError("exception", "校验异常");
			e.printStackTrace();
		}
	}
	
	//getter&setter
	public int[] getRoleIds() {
		return roleIds;
	}
	public int[] getAdminIds() {
		return adminIds;
	}
	public void setAdminIds(int[] adminIds) {
		this.adminIds = adminIds;
	}
	public void setRoleIds(int[] roleIds) {
		this.roleIds = roleIds;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
}
