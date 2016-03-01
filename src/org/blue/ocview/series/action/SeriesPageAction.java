package org.blue.ocview.series.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.blue.backend.media.domain.Media;
import org.blue.backend.media.domain.Series;
import org.blue.backend.media.domain.Teacher;
import org.blue.backend.media.service.MediaService;
import org.blue.backend.media.service.SeriesService;
import org.blue.backend.media.service.TeacherService;
import org.blue.backend.media.service.impl.MediaServiceImpl;
import org.blue.backend.media.service.impl.SeriesServiceImpl;
import org.blue.backend.media.service.impl.TeacherServiceImpl;
import org.blue.backend.navication.domain.Navication;
import org.blue.backend.navication.service.NavicationService;
import org.blue.backend.navication.service.impl.NavicationServiceImpl;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SeriesPageAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private int seriesId;//系列编号
	
	public String execute() throws Exception {
		//提供系列信息
		SeriesService seriesService = new SeriesServiceImpl();
		Series series = seriesService.getById(seriesId);
		if(series==null){
			return "error";
		}
		ActionContext.getContext().put("series", series);
		//提供所属导航信息
		NavicationService navService = new NavicationServiceImpl();
		Navication navication = navService.getById(series.getNavication().getNavicationId());
		ActionContext.getContext().put("navication", navication);
		//提供讲师信息
		TeacherService teacherService = new TeacherServiceImpl();
		Teacher teacher = teacherService.getById(series.getTeacher().getTeacherId());
		ActionContext.getContext().put("teacher", teacher);
		//提供包含的视频信息
		MediaService mediaService = new MediaServiceImpl();
		List<Media> mediaList = mediaService.findBySeriesId(seriesId);
		ActionContext.getContext().put("mediaList", mediaList);
		//返回登陆信息
		boolean loginFlag = true;//true表示没 登陆
		if(ServletActionContext.getRequest().getSession().getAttribute("user") != null){
			loginFlag = false;
		}
		ActionContext.getContext().put("loginFlag", loginFlag);
		
		return "success";
	}

	//getter&setter
	public int getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(int seriesId) {
		this.seriesId = seriesId;
	}
	
}
