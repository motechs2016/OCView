package org.blue.ocview.index.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.blue.ocview.index.service.OpinionService;
import org.blue.ocview.index.service.impl.OpinionServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

/**
 * “反馈意见”业务控制类Action
 * @author ldc4
 */
public class OpinionAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private String opinionContent;
	private String contactInfo;
	
	private InputStream result;
	
	public String execute() throws Exception {
		//校验
		if(opinionContent==null||opinionContent.equals("")){
			result = new ByteArrayInputStream("请反馈您宝贵的建议！".getBytes("UTF-8"));
			return "success";
		}
		if(contactInfo==null||contactInfo.equals("")){
			result = new ByteArrayInputStream("请留下您的联系方式！".getBytes("UTF-8"));
			return "success";
		}
		
		OpinionService opinionService = new OpinionServiceImpl();
		opinionService.save(opinionContent,contactInfo);
		
		result = new ByteArrayInputStream("OK谢谢反馈意见，我们将参考您的意见，使网站更加完美！".getBytes("UTF-8"));
		return "success";
	}

	//getter&setter
	public String getOpinionContent() {
		return opinionContent;
	}
	public void setOpinionContent(String opinionContent) {
		this.opinionContent = opinionContent;
	}
	public String getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	public InputStream getResult() {
		return result;
	}
	public void setResult(InputStream result) {
		this.result = result;
	}
}
