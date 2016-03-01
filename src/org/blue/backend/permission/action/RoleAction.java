package org.blue.backend.permission.action;

import org.blue.backend.permission.domain.Role;
import org.blue.backend.permission.service.PrivilegeService;
import org.blue.backend.permission.service.RoleService;
import org.blue.backend.permission.service.impl.PrivilegeServiceImpl;
import org.blue.backend.permission.service.impl.RoleServiceImpl;
import org.blue.backend.permission.service.join.Role2PrivilegeService;
import org.blue.backend.util.BaseAction;
import org.blue.backend.util.PageBean;


import com.opensymphony.xwork2.ActionContext;

/**
 * “角色”业务控制Action
 * @author ldc4
 */
public class RoleAction extends BaseAction<Role>{
	
	private static final long serialVersionUID = 1L;
	
	private int[] privilegeIds;
	private int[] roleIds;
	private int pageNum = 1;//页数
	private int pageSize = 10;//每页显示多少条
	
	/**列表*/
	public String list() throws Exception{
		//获取到服务对象
		RoleService roleService = new RoleServiceImpl();
		//设置数据
		PageBean recordList = roleService.getPageBean(pageNum, pageSize);
		ActionContext.getContext().getValueStack().push(recordList);
		return "list";
	}
	/**删除*/
	public String delete() throws Exception{
		//获取到一个服务对象
		RoleService roleService = new RoleServiceImpl();
		//删除
		if(roleIds!=null){
			roleService.delete(roleIds);
		}
		return "toList";
	}
	/**添加页面*/
	public String addUI() throws Exception{
		return "saveUI";
	}
	/**添加*/
	public String add() throws Exception{
		//获取到一个服务对象
		RoleService roleService = new RoleServiceImpl();
		//model为ModelDriven自动封装的参数对象
		roleService.save(model);
		return "toList";
	}
	/**修改页面*/
	public String editUI() throws Exception{
		//准备回显数据
		RoleService roleService = new RoleServiceImpl();
		Role role = roleService.getById(model.getRoleId());
		ActionContext.getContext().getValueStack().push(role);
		return "saveUI";
	}
	/**修改*/
	public String edit() throws Exception{
		//获取到一个服务对象
		RoleService roleService = new RoleServiceImpl();
		//取出原来的
		Role role = roleService.getById(model.getRoleId());
		//设置属性
		role.setRoleName(model.getRoleName());
		role.setRoleDescription(model.getRoleDescription());
		//更新
		roleService = new RoleServiceImpl();
		roleService.update(role);
		return "toList";
	}
	/**权限*/
	public String setPrivilege() throws Exception{
		Role2PrivilegeService joinService = new Role2PrivilegeService();
		joinService.savePrivilegeIds(model.getRoleId(),privilegeIds);
		return "toList";
	}
	/**权限页面*/
	public String setPrivilegeUI() throws Exception{
		/**页面通过application范围可以取到。
		//准备数据
		PrivilegeService priService = new PrivilegeServiceImpl();
		List<Privilege> privilegeList = priService.findAll(); 
		ActionContext.getContext().put("privilegeList", privilegeList);
		*/
		//回显
		Role2PrivilegeService joinService = new Role2PrivilegeService();
		privilegeIds = joinService.findAllIds(model.getRoleId());
		ActionContext.getContext().getValueStack().push(privilegeIds);
		
		return "setPrivilegeUI";
	}
	
	//校验
	public void validateSetPrivilege() {
		int roleId = model.getRoleId();
		try {
			//判断是否存在这个角色
			RoleService roleService = new RoleServiceImpl();
			if(roleService.getById(roleId).getRoleName()==null){
				addFieldError("roleError", "角色不存在");
			}
			//判断是否存在权限
			if(privilegeIds!=null){
				for(int privilegeId:privilegeIds){
					PrivilegeService privService = new PrivilegeServiceImpl();
					if(privService.getById(privilegeId)==null){
						addFieldError("roleError", "权限不存在");
						break;
					}
				}
			}
		} catch (Exception e) {
			addFieldError("exception", "校验异常");
			e.printStackTrace();
		}
		
	}
	
	
	//getter&setter
	public int[] getPrivilegeIds() {
		return privilegeIds;
	}
	public void setPrivilegeIds(int[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
	public int[] getRoleIds() {
		return roleIds;
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
}
