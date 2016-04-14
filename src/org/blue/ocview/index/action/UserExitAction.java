package org.blue.ocview.index.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 用户退出
 * @author ldc4
 */
public class UserExitAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		ActionContext.getContext().getSession().remove("user");
		return "success";
	}
}
