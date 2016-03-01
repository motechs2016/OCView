package org.blue.ocview.media.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.struts2.ServletActionContext;
import org.blue.backend.media.service.MediaService;
import org.blue.backend.media.service.impl.MediaServiceImpl;
import org.blue.backend.user.domain.User;

import com.opensymphony.xwork2.ActionSupport;

public class VoteAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private int mediaId;
	
	private InputStream result;
	
	public String execute() throws Exception {
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if(user==null){
			result = new ByteArrayInputStream(("err").getBytes("UTF-8"));
			return "success";
		}
		//获取登录用户ID
		int userId = user.getUserId();
		//检测是否已经投过票
		MediaService mediaService = new MediaServiceImpl();
		if(!mediaService.checkVote(userId, mediaId)){
			result = new ByteArrayInputStream(("err").getBytes("UTF-8"));
			return "success";
		}
		//执行投票操作
		mediaService = new MediaServiceImpl();
		int voteCount = mediaService.vote(userId,mediaId);
		result = new ByteArrayInputStream((""+voteCount).getBytes("UTF-8"));
		
		return "success";
	}

	//getter&setter
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	public InputStream getResult() {
		return result;
	}
	public void setResult(InputStream result) {
		this.result = result;
	}
}
