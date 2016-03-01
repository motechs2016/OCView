package org.blue.ocview.media.action;

import org.apache.struts2.ServletActionContext;
import org.blue.backend.media.domain.Media;
import org.blue.backend.media.domain.Teacher;
import org.blue.backend.media.service.MediaService;
import org.blue.backend.media.service.SeriesService;
import org.blue.backend.media.service.TeacherService;
import org.blue.backend.media.service.impl.MediaServiceImpl;
import org.blue.backend.media.service.impl.SeriesServiceImpl;
import org.blue.backend.media.service.impl.TeacherServiceImpl;
import org.blue.backend.user.domain.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class MediaPageAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private int mediaId;
	
	public String execute() throws Exception {
		//提供视频信息
		MediaService mediaService = new MediaServiceImpl();
		Media media = mediaService.getById(mediaId);
		if(media==null){
			return "error";
		}
		ActionContext.getContext().put("media", media);
		//提供讲师信息
		SeriesService seriesService = new SeriesServiceImpl();
		int teacherId = seriesService.getById(media.getSeries().getSeriesId()).getTeacher().getTeacherId();
		TeacherService teacherService = new TeacherServiceImpl();
		Teacher teacher = teacherService.getById(teacherId);
		ActionContext.getContext().put("teacher", teacher);
		//返回登陆信息&&提供用户是否已经投过票标记
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		boolean loginFlag = true;
		boolean voteMark = false;
		if(user!=null){
			int userId = user.getUserId();
			mediaService = new MediaServiceImpl();
			voteMark = mediaService.checkVote(userId,mediaId);
			loginFlag = false;
		}
		ActionContext.getContext().put("voteMark", voteMark);
		ActionContext.getContext().put("loginFlag", loginFlag);
		
		return "success";
	}

	
	//getter&setter
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}

}
