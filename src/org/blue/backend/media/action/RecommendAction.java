package org.blue.backend.media.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.blue.backend.media.domain.Recommend;
import org.blue.backend.media.domain.Series;
import org.blue.backend.media.service.RecommendService;
import org.blue.backend.media.service.SeriesService;
import org.blue.backend.media.service.impl.RecommendServiceImpl;
import org.blue.backend.media.service.impl.SeriesServiceImpl;
import org.blue.backend.util.BaseAction;

import com.opensymphony.xwork2.ActionContext;

public class RecommendAction extends BaseAction<Recommend>{

	private static final long serialVersionUID = 1L;

	private int[] recommendIds;
	private int seriesId;
	
	//自动封装
	private File upload;//上传文件
	private String uploadContentType;//上传文件类型
	private String uploadFileName;//上传文件名
	
	private String savePath;//文件保存路径
	private String allowTypes;//允许上传的类型
	
	/**列表*/
	public String list() throws Exception{
		//准备数据
		RecommendService recService = new RecommendServiceImpl();
		List<Recommend> recList = recService.findAll();
		ActionContext.getContext().put("recList", recList);
		return "list";
	}
	/**删除*/
	public String delete() throws Exception{
		RecommendService recService = new RecommendServiceImpl();
		if(recommendIds!=null){
			recService.delete(recommendIds);
		}
		return "toList";
	}
	/**添加页面*/
	public String addUI() throws Exception{
		//准备数据
		SeriesService seriesService = new SeriesServiceImpl();
		List<Series> seriesList = seriesService.findAllSimple();
		ActionContext.getContext().put("seriesList", seriesList);
		return "saveUI";
	}
	/**添加*/
	public String add() throws Exception{
		
		String pictureFormat = "";
		//默认路径
		String picturePath = "videos/default/recommend.jpg";
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
		Series series = new Series();
		series.setSeriesId(seriesId);
		model.setSeries(series);
		model.setPicturePath(picturePath);
		
		RecommendService recommendService = new RecommendServiceImpl();
		recommendService.save(model);
		
		return "toList";
	}
	/**修改页面*/
	public String editUI() throws Exception{
		//回显
		RecommendService recommendService = new RecommendServiceImpl();
		Recommend recommend = recommendService.getById(model.getRecommendId());
		ActionContext.getContext().getValueStack().push(recommend);
		return "saveUI";
	}
	/**修改*/
	public String edit() throws Exception{
		
		if(upload != null){
			RecommendService recommendService = new RecommendServiceImpl();
			Recommend recommend = recommendService.getById(model.getRecommendId());
			//删除原来的图片
			File file = new File(ServletActionContext.getServletContext().getRealPath(recommend.getPicturePath()));
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
			model.setPicturePath(picturePath);
		}
		
		//更新
		RecommendService recommendService = new RecommendServiceImpl();
		recommendService.update(model);
		return "toList";
	}
	/**上移*/
	public String moveUp() throws Exception{
		RecommendService recService = new RecommendServiceImpl();
		//上移
		recService.moveUp(model.getRecommendId());
		return "toList";
	}
	/**下移*/
	public String moveDown() throws Exception{
		RecommendService recService = new RecommendServiceImpl();
		//下移
		recService.moveDown(model.getRecommendId());
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
	public int[] getRecommendIds() {
		return recommendIds;
	}
	public void setRecommendIds(int[] recommendIds) {
		this.recommendIds = recommendIds;
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
	public int getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(int seriesId) {
		this.seriesId = seriesId;
	}
}
