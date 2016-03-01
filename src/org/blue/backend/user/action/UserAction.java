package org.blue.backend.user.action;

import org.blue.backend.user.domain.User;
import org.blue.backend.user.service.UserService;
import org.blue.backend.user.service.impl.UserServiceImpl;
import org.blue.backend.util.BaseAction;
import org.blue.backend.util.PageBean;

import com.opensymphony.xwork2.ActionContext;

public class UserAction extends BaseAction<User>{
	
	private static final long serialVersionUID = 1L;
	
	private int[] userIds;
	private int pageNum = 1;//页数
	private int pageSize = 10;//每页显示多少条
	
	/**列表*/
	public String list() throws Exception{
		//准备数据
		UserService userService = new UserServiceImpl();
		PageBean recordList = userService.getPageBean(pageNum,pageSize);
		ActionContext.getContext().getValueStack().push(recordList);
		return "list";
	}
	/**删除*/
	public String delete() throws Exception{
		//获取到一个服务对象
		UserService userService = new UserServiceImpl();
		//从数据库中删除记录
		if(userIds!=null){
			userService.delete(userIds);
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
		UserService userService = new UserServiceImpl();
		//初始密码
		model.setUserPassword("123456");
		//model为ModelDriven自动封装的参数对象
		userService.save(model);
		return "toList";
	}
	/**修改页面*/
	public String editUI() throws Exception{
		//准备回显数据
		UserService userService = new UserServiceImpl();
		User user = userService.getById(model.getUserId());
		ActionContext.getContext().getValueStack().push(user);
		return "saveUI";
	}
	/**修改*/
	public String edit() throws Exception{
		//获取到一个服务对象
		UserService userService = new UserServiceImpl();
		//取出原来的
		User user = userService.getById(model.getUserId());
		//设置属性
		user.setUserNickname(model.getUserNickname());
		user.setUserAccount(model.getUserAccount());
		user.setUserEmail(model.getUserEmail());
		//更新
		userService = new UserServiceImpl();
		userService.update(user);
		return "toList";
	}
	
	//getter&setter
	public int[] getUserIds() {
		return userIds;
	}
	public void setUserIds(int[] userIds) {
		this.userIds = userIds;
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
