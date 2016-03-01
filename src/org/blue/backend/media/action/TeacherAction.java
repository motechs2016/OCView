package org.blue.backend.media.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.blue.backend.media.domain.Teacher;
import org.blue.backend.media.service.TeacherService;
import org.blue.backend.media.service.impl.TeacherServiceImpl;
import org.blue.backend.util.BaseAction;
import org.blue.backend.util.PageBean;

import com.opensymphony.xwork2.ActionContext;

public class TeacherAction extends BaseAction<Teacher>{
	
	private static final long serialVersionUID = 1L;
	
	//自动封装
	private File upload;//上传文件
	private String uploadContentType;//上传文件类型
	private String uploadFileName;//上传文件名
	
	private String savePath;//文件保存路径
	private String allowTypes;//允许上传的类型
	
	private int[] teacherIds;
	private int pageNum = 1;//页数
	private int pageSize = 10;//每页显示多少条
	
	/**列表*/
	public String list() throws Exception{
		//准备数据
		TeacherService teacherService = new TeacherServiceImpl();
		PageBean recordList = teacherService.getPageBean(pageNum,pageSize);
		ActionContext.getContext().getValueStack().push(recordList);
		return "list";
	}
	/**删除*/
	public String delete() throws Exception{
		TeacherService teacherService = new TeacherServiceImpl();
		if(teacherIds!=null){
			teacherService.delete(teacherIds);
		}
		return "toList";
	}
	/**添加页面*/
	public String addUI() throws Exception{
		return "saveUI";
	}
	/**添加*/
	public String add() throws Exception{
		
		String pictureFormat = "";
		//默认路径
		String picturePath = "videos/default/teacher.jpg";
		if(upload != null){
			//图片格式
			pictureFormat = uploadFileName.split("\\.")[1];
			//图片保存路径
			picturePath = savePath + "/" +UUID.randomUUID().toString()+"."+pictureFormat;
			//上传文件
			FileInputStream fis = new FileInputStream(upload);
			FileOutputStream fos = new FileOutputStream(ServletActionContext.getServletContext().getRealPath(picturePath));
			
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len=fis.read(buffer))>0){
				fos.write(buffer, 0, len);
				fos.flush();
			}
			
			fis.close();
			fos.close();
		}
		
		//设置属性
		model.setTeacherPicture(picturePath);
		
		TeacherService teacherService = new TeacherServiceImpl();
		teacherService.save(model);
		return "toList";
	}
	/**修改页面*/
	public String editUI() throws Exception{
		//回显
		TeacherService teacherService = new TeacherServiceImpl();
		Teacher teacher = teacherService.getById(model.getTeacherId());
		ActionContext.getContext().getValueStack().push(teacher);
		return "saveUI";
	}
	/**修改*/
	public String edit() throws Exception{
		
		if(upload != null){
			TeacherService teacherService = new TeacherServiceImpl();
			Teacher teacher = teacherService.getById(model.getTeacherId());
			//删除原来的图片
			File file = new File(ServletActionContext.getServletContext().getRealPath(teacher.getTeacherPicture()));
			if(file.exists()){
				if(!file.delete())
					new RuntimeException("删除文件失败");
			}
			
			//图片格式
			String pictureFormat = uploadFileName.split("\\.")[1];
			//图片保存路径
			String picturePath = savePath + "/" +UUID.randomUUID().toString()+"."+pictureFormat;
			//上传文件
			FileInputStream fis = new FileInputStream(upload);
			FileOutputStream fos = new FileOutputStream(ServletActionContext.getServletContext().getRealPath(picturePath));
			
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len=fis.read(buffer))>0){
				fos.write(buffer, 0, len);
				fos.flush();
			}
			
			fis.close();
			fos.close();
			
			//设置属性
			model.setTeacherPicture(picturePath);
		}
		
		//更新
		TeacherService teacherService = new TeacherServiceImpl();
		teacherService.update(model);
		return "toList";
	}
	
	//文件过滤
	public String filterTypes(String[] types){
		String fileType = getUploadContentType();
		for(String type : types){
			if(type.equals(fileType)){
				return null;
			}
		}
		return ERROR;
	}
	
	//校验add方法
	public void validateAdd(){
		if(upload==null)
			return ;
		String filterResult = filterTypes(getAllowTypes().split(","));
		if(filterResult!=null){
			addFieldError("typeError", "您上传的文件类型不正确！");
		}
	}
		
	
	//getter&setter
	public int[] getTeacherIds() {
		return teacherIds;
	}
	public void setTeacherIds(int[] teacherIds) {
		this.teacherIds = teacherIds;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getAllowTypes() {
		return allowTypes;
	}
	public void setAllowTypes(String allowTypes) {
		this.allowTypes = allowTypes;
	}
}
