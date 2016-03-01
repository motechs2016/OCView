package org.blue.backend.media.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.struts2.ServletActionContext;
import org.blue.backend.media.domain.Series;
import org.blue.backend.media.domain.Teacher;
import org.blue.backend.media.service.SeriesService;
import org.blue.backend.media.service.TeacherService;
import org.blue.backend.media.service.impl.SeriesServiceImpl;
import org.blue.backend.media.service.impl.TeacherServiceImpl;
import org.blue.backend.navication.domain.Navication;
import org.blue.backend.navication.service.NavicationService;
import org.blue.backend.navication.service.impl.NavicationServiceImpl;
import org.blue.backend.util.BaseAction;
import org.blue.backend.util.ImageUtils;
import org.blue.backend.util.NavicationUtils;
import org.blue.backend.util.PageBean;

import com.opensymphony.xwork2.ActionContext;

public class SeriesAction extends BaseAction<Series>{

	private static final long serialVersionUID = 1L;
	
	//自动封装
	private File upload;//上传文件
	private String uploadContentType;//上传文件类型
	private String uploadFileName;//上传文件名
	
	private String savePath;//文件保存路径
	private String allowTypes;//允许上传的类型
	
	private int[] seriesIds;
	private int firstNav;//一级导航编号（仅用于回显）
	private int navicationId;//二级导航编号
	private int teacherId;//讲师编号
	private int pageNum = 1;//页数
	private int pageSize = 10;//每页显示多少条
	
	/**列表*/
	public String list() throws Exception{
		//准备数据
		SeriesService seriesService = new SeriesServiceImpl();
		PageBean recordList = seriesService.getPageBean(pageNum,pageSize);
		ActionContext.getContext().getValueStack().push(recordList);
		return "list";
	}
	/**删除*/
	public String delete() throws Exception{
		SeriesService seriesService = new SeriesServiceImpl();
		if(seriesIds!=null){
			seriesService.delete(seriesIds);
		}
		return "toList";
	}
	/**添加页面*/
	public String addUI() throws Exception{
		//准备数据：导航
		NavicationService navService = new NavicationServiceImpl();
		List<Navication> topList = navService.findTopList(false,true);
		Map<Navication,List<Navication>> navMap = NavicationUtils.getMapNavication(topList);
		ActionContext.getContext().put("navMap", navMap);
		//准备数据：讲师
		TeacherService teacherService = new TeacherServiceImpl();
		List<Teacher> teacherList = teacherService.findAllSimple();
		ActionContext.getContext().put("teacherList", teacherList);
		return "saveUI";
	}
	/**添加*/
	public String add() throws Exception{
		String pictureFormat = "";
		//默认路径
		String picturePath = "videos/default/series.jpg";
		if(upload != null){
			//图片格式
			pictureFormat = uploadFileName.split("\\.")[1];
			//图片保存路径
			picturePath = savePath + "/" +UUID.randomUUID().toString()+"-new."+pictureFormat;
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
			//缩放图片
			BufferedImage originalPic = ImageIO.read(upload);
			BufferedImage changePic = ImageUtils.getResizePicture(originalPic, 100.0/275);
			picturePath = picturePath.replace("-new", "");
			File outputFile = new File(ServletActionContext.getServletContext().getRealPath(picturePath));
			ImageIO.write(changePic, pictureFormat, outputFile);
		}
		
		//设置保存信息
		Teacher teacher = new Teacher();
		teacher.setTeacherId(teacherId);
		model.setTeacher(teacher);
		Navication navication = new Navication();
		navication.setNavicationId(navicationId);
		model.setNavication(navication);
		model.setPicturePath("");
		model.setCourseNum(0);
		model.setPicturePath(picturePath);
		//保存
		SeriesService seriesService = new SeriesServiceImpl();
		seriesService.save(model);
		return "toList";
	}
	/**修改页面*/
	public String editUI() throws Exception{
		//准备数据：导航
		NavicationService navService = new NavicationServiceImpl();
		List<Navication> topList = navService.findTopList(false,true);
		Map<Navication,List<Navication>> navMap = NavicationUtils.getMapNavication(topList);
		ActionContext.getContext().put("navMap", navMap);
		//准备数据：讲师
		TeacherService teacherService = new TeacherServiceImpl();
		List<Teacher> teacherList = teacherService.findAllSimple();
		ActionContext.getContext().put("teacherList", teacherList);
		//回显基本信息
		SeriesService seriesService = new SeriesServiceImpl();
		Series series = seriesService.getById(model.getSeriesId());
		ActionContext.getContext().getValueStack().push(series);
		//回显下拉选择框
		navicationId = series.getNavication().getNavicationId();
		navService = new NavicationServiceImpl();
		firstNav = navService.getById(navicationId).getParent().getNavicationId();
		teacherId = series.getTeacher().getTeacherId();
		return "saveUI";
	}
	/**修改*/
	public String edit() throws Exception{
		//设置保存信息
		Teacher teacher = new Teacher();
		teacher.setTeacherId(teacherId);
		model.setTeacher(teacher);
		Navication navication = new Navication();
		navication.setNavicationId(navicationId);
		model.setNavication(navication);
		model.setPicturePath("");
		model.setCourseNum(0);
		//更新
		SeriesService seriesService = new SeriesServiceImpl();
		seriesService.update(model);
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
	public int[] getSeriesIds() {
		return seriesIds;
	}
	public void setSeriesIds(int[] seriesIds) {
		this.seriesIds = seriesIds;
	}
	public int getNavicationId() {
		return navicationId;
	}
	public void setNavicationId(int navicationId) {
		this.navicationId = navicationId;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
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
	public int getFirstNav() {
		return firstNav;
	}
	public void setFirstNav(int firstNav) {
		this.firstNav = firstNav;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
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
