package org.blue.ocview.index.action;

import java.util.List;

import org.blue.backend.media.domain.Media;
import org.blue.backend.media.domain.Recommend;
import org.blue.backend.media.service.MediaService;
import org.blue.backend.media.service.RecommendService;
import org.blue.backend.media.service.impl.MediaServiceImpl;
import org.blue.backend.media.service.impl.RecommendServiceImpl;
import org.blue.backend.navication.domain.Navication;
import org.blue.backend.navication.service.NavicationService;
import org.blue.backend.navication.service.impl.NavicationServiceImpl;
import org.blue.backend.util.NavicationUtils;
import org.blue.ocview.index.domain.SecondNav;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * OC View “主页”业务控制类Action
 * @author ldc4
 */
public class IndexPageAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		//提供一级栏目数据
		NavicationService navService = new NavicationServiceImpl();
		List<Navication> topList = navService.findTopList(true,true);
		ActionContext.getContext().put("topList", topList);
		//提供二级栏目数据
		navService = new NavicationServiceImpl();
		topList = navService.findTopList(false,true);
		List<SecondNav> secondList = NavicationUtils.getSecendNavication1(topList);
		ActionContext.getContext().put("secondList", secondList);
		//提供首页大图路径
		RecommendService recService = new RecommendServiceImpl();
		List<Recommend> recList = recService.findAll();
		ActionContext.getContext().put("recList", recList);
		//提供热门排行
		MediaService mediaService = new MediaServiceImpl();
		List<Media> hotList =  mediaService.getHotList();
		ActionContext.getContext().put("hotList",hotList);
		//提供最新课程
		mediaService = new MediaServiceImpl();
		List<Media> newList =  mediaService.getNewList();
		ActionContext.getContext().put("newList",newList);
		return "success";
	}
}
