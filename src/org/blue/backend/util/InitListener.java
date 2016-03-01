package org.blue.backend.util;

import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.blue.backend.permission.domain.Privilege;
import org.blue.backend.permission.service.PrivilegeService;
import org.blue.backend.permission.service.impl.PrivilegeServiceImpl;

/**
 * 容器初始化监听器：提供权限数据
 * @author ldc4
 */
public class InitListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try{
			//准备数据
			PrivilegeService privilegeService = new PrivilegeServiceImpl();
			List<Privilege> topPrivilegeList = privilegeService.findTopList();
			sce.getServletContext().setAttribute("topPrivilegeList", topPrivilegeList);
			
			//准备数据
			privilegeService = new PrivilegeServiceImpl();
			Collection<String> allPrivilegeUrls = privilegeService.getAllPrivilgeUrls();
			sce.getServletContext().setAttribute("allPrivilegeUrls", allPrivilegeUrls);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
}
