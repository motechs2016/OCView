package org.blue.backend.navication.action;

import java.util.List;

import org.blue.backend.navication.domain.Navication;
import org.blue.backend.navication.service.NavicationService;
import org.blue.backend.navication.service.impl.NavicationServiceImpl;
import org.blue.backend.util.BaseAction;
import org.blue.backend.util.NavicationUtils;

import com.opensymphony.xwork2.ActionContext;

/**
 * “导航”业务控制类Action
 * @author ldc4
 */
public class NavicationAction extends BaseAction<Navication> {
	
	private static final long serialVersionUID = 1L;
	
	private int[] navicationIds;
	private int parentId = 0;//父ID
	
	/**列表*/
	public String list() throws Exception{
		//准备数据
		NavicationService navService = new NavicationServiceImpl();
		List<Navication> navList = navService.findByParentId(parentId);
		ActionContext.getContext().put("navList", navList);
		//准备数据:parent
		if(parentId != 0){
			NavicationService navService2 = new NavicationServiceImpl();
			Navication parent = navService2.getById(parentId);
			ActionContext.getContext().put("parent", parent);
		}
		return "list";
	}
	/**删除*/
	public String delete() throws Exception{
		NavicationService navService = new NavicationServiceImpl();
		if(navicationIds!=null){
			navService.delete(navicationIds);
		}
		return "toList";
	}
	/**添加页面*/
	public String addUI() throws Exception{
		//准备数据
		NavicationService navService = new NavicationServiceImpl();
		List<Navication> topList = navService.findTopList(false,true);
		List<Navication> navList = NavicationUtils.getAllNavication(topList,true);
		ActionContext.getContext().put("navList", navList);
		return "saveUI";
	}
	/**添加*/
	public String add() throws Exception{
		NavicationService navService = new NavicationServiceImpl();
		Navication parent = new Navication();
		parent.setNavicationId(parentId);
		model.setParent(parent);
		//保存
		navService.save(model);
		return "toList";
	}
	/**修改页面*/
	public String editUI() throws Exception{
		//准备数据
		NavicationService navService = new NavicationServiceImpl();
		List<Navication> topList = navService.findTopList(false,true);
		List<Navication> navList = NavicationUtils.getAllNavication(topList,true);
		ActionContext.getContext().put("navList", navList);
		//回显
		navService = new NavicationServiceImpl();
		Navication nav = navService.getById(model.getNavicationId());
		ActionContext.getContext().getValueStack().push(nav);
		if(nav!=null){
			parentId = nav.getParent().getNavicationId();
		}
		return "saveUI";
	}
	/**修改*/
	public String edit() throws Exception{
		NavicationService navService = new NavicationServiceImpl();
		//更新
		navService.update(model);
		return "toList";
	}
	/**上移*/
	public String moveUp() throws Exception{
		NavicationService navService = new NavicationServiceImpl();
		//上移
		navService.moveUp(model.getNavicationId());
		return "toList";
	}
	/**下移*/
	public String moveDown() throws Exception{
		NavicationService navService = new NavicationServiceImpl();
		//下移
		navService.moveDown(model.getNavicationId());
		return "toList";
	}
	
	//校验
	public void validateAdd() {
		//判断上级导航是否存在
		try {
			NavicationService navService = new NavicationServiceImpl();
			if(navService.getById(parentId)==null){
				addFieldError("noParentError", "没有该上级导航");
			}
		} catch (Exception e) {
			addFieldError("exception", "校验异常");
			e.printStackTrace();
		}
	}
	
	//setter&getter
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int[] getNavicationIds() {
		return navicationIds;
	}
	public void setNavicationIds(int[] navicationIds) {
		this.navicationIds = navicationIds;
	}
}
