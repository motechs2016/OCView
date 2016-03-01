package org.blue.backend.util;

import java.util.Date;

import org.blue.backend.permission.domain.Admin;
import org.blue.backend.permission.domain.Privilege;
import org.blue.backend.permission.service.AdminService;
import org.blue.backend.permission.service.PrivilegeService;
import org.blue.backend.permission.service.impl.AdminServiceImpl;
import org.blue.backend.permission.service.impl.PrivilegeServiceImpl;

/**
 * 安装类：提供权限表初始化
 * @author ldc4
 */
public class Installer {

	public void install() throws Exception {
		admin();
		system();
		navication();
		user();
		media();
	}

	public void admin() throws Exception{
		// 保存超级管理员用户
		Admin admin = new Admin();
		admin.setAdminAccount("admin");
		admin.setAdminName("超级管理员");
		admin.setAdminPassword("admin");
		Date currentTime = new Date();
		admin.setCreateDate(currentTime);
		admin.setUpdateDate(currentTime);
		admin.setLastLoginDate(new Date());
		admin.setLastLoginIP("127.0.0.1");
		admin.setLoginTimes(0);
		AdminService adminService = new AdminServiceImpl();
		//超级管理员不需要设置角色，它拥有所有权限
		adminService.save(admin,null);
	}
	
	public void system() throws Exception{
		// 保存权限数据(系统管理)
		Privilege menu,menu1,menu2;
		menu = new Privilege("系统管理", null, null);
		PrivilegeService priService = new PrivilegeServiceImpl();
		priService.save(menu);
		menu1 = new Privilege("管理员管理","admin", menu);
		menu2 = new Privilege("角色管理","role",  menu);
		priService = new PrivilegeServiceImpl();
		priService.save(menu1);
		priService = new PrivilegeServiceImpl();
		priService.save(menu2);
		
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("管理员列表", "/backend/admin_list", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("管理员删除", "/backend/admin_delete", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("管理员添加", "/backend/admin_add", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("管理员修改", "/backend/admin_edit", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("初始化密码", "/backend/admin_initPassword", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("管理员详情", "/backend/admin_details", menu1));
		
		
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("角色列表", "/backend/role_list", menu2));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("角色删除", "/backend/role_delete", menu2));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("角色添加", "/backend/role_add", menu2));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("角色修改", "/backend/role_edit", menu2));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("设置权限", "/backend/role_setPrivilege", menu2));
	}
	
	public void navication() throws Exception{
		// 保存权限数据(导航管理)
		Privilege menu,menu1;
		menu = new Privilege("导航管理", null, null);
		PrivilegeService priService = new PrivilegeServiceImpl();
		priService.save(menu);
		menu1 = new Privilege("前台导航","navication", menu);
		priService = new PrivilegeServiceImpl();
		priService.save(menu1);
		
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("导航列表", "/backend/navication_list", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("导航删除", "/backend/navication_delete", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("导航添加", "/backend/navication_add", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("导航修改", "/backend/navication_edit", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("导航上移", "/backend/navication_moveUp", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("导航下移", "/backend/navication_moveDown", menu1));
		
	}
	
	public void user() throws Exception{
		// 保存权限数据(用户管理)
		Privilege menu,menu1;
		menu = new Privilege("用户管理", null, null);
		PrivilegeService priService = new PrivilegeServiceImpl();
		priService.save(menu);
		menu1 = new Privilege("普通用户","user", menu);
		priService = new PrivilegeServiceImpl();
		priService.save(menu1);
		
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("用户列表", "/backend/user_list", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("用户删除", "/backend/user_delete", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("用户添加", "/backend/user_add", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("用户修改", "/backend/user_edit", menu1));
	}
	
	public void media() throws Exception{
		// 保存权限数据(基本管理)
		Privilege menu,menu1,menu2,menu3,menu4,menu5;
		menu = new Privilege("基本管理", null, null);
		PrivilegeService priService = new PrivilegeServiceImpl();
		priService.save(menu);
		menu1 = new Privilege("视频管理", "media", menu);
		menu2 = new Privilege("评论管理", "comment", menu);
		menu3 = new Privilege("讲师管理", "teacher", menu);
		menu4 = new Privilege("系列管理", "series", menu);
		menu5 = new Privilege("推荐管理", "recommend", menu);
		priService = new PrivilegeServiceImpl();
		priService.save(menu1);
		priService = new PrivilegeServiceImpl();
		priService.save(menu2);
		priService = new PrivilegeServiceImpl();
		priService.save(menu3);
		priService = new PrivilegeServiceImpl();
		priService.save(menu4);
		priService = new PrivilegeServiceImpl();
		priService.save(menu5);
		
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("视频列表", "/backend/media_list", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("视频删除", "/backend/media_delete", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("视频添加", "/backend/media_add", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("视频修改", "/backend/media_edit", menu1));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("投票设置", "/backend/media_vote", menu1));
		
		
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("评论列表", "/backend/comment_list", menu2));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("评论删除", "/backend/comment_delete", menu2));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("评论添加", "/backend/comment_add", menu2));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("评论修改", "/backend/comment_edit", menu2));
		
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("讲师列表", "/backend/teacher_list", menu3));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("讲师删除", "/backend/teacher_delete", menu3));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("讲师添加", "/backend/teacher_add", menu3));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("讲师修改", "/backend/teacher_edit", menu3));

		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("系列列表", "/backend/series_list", menu4));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("系列删除", "/backend/series_delete", menu4));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("系列添加", "/backend/series_add", menu4));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("系列修改", "/backend/series_edit", menu4));
		
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("推荐列表", "/backend/recommend_list", menu5));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("推荐删除", "/backend/recommend_delete", menu5));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("推荐添加", "/backend/recommend_add", menu5));
		priService = new PrivilegeServiceImpl();
		priService.save(new Privilege("推荐修改", "/backend/recommend_edit", menu5));
		
	}
	
	
	public static void main(String[] args) throws Exception {
		Installer installer = new Installer();
		installer.install();
	}
}
