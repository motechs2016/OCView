package org.blue.backend.permission.interceptor;

import org.blue.backend.permission.domain.Admin;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 检测权限的拦截器
 * @author ldc4
 */
public class CheckPrivilegeInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		Admin admin = (Admin) ActionContext.getContext().getSession().get("admin");
		String namespace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String privUrl = "";
		//判断是否是根命名空间
		if(namespace.equals("/")){
			privUrl = namespace + actionName ;
		}else{
			privUrl = namespace + "/" +actionName ;
		}
		//如果未登陆,就转到登陆页面
		if(admin == null){
			if(privUrl.contains("/admin_login")){
				//如果是去登录，就放行
				return invocation.invoke();
			}else{
				//如果不是去登录，就转到登陆页面
				return "loginUI";
			}
		}
		//如果已登录，就判断权限
		else{
			//如果有权限,就放行
			if(admin.hasPrivilegeByUrl(privUrl)){
				return invocation.invoke();
			}else{
			//如果没有权限，就转到提示页面
				return "noPrivilegeError";
			}
		}
	}
	
}
