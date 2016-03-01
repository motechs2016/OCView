package org.blue.backend.media.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.blue.backend.media.domain.Media;
import org.blue.backend.media.domain.Series;
import org.blue.backend.media.service.MediaService;
import org.blue.backend.media.service.SeriesService;
import org.blue.backend.media.service.impl.MediaServiceImpl;
import org.blue.backend.media.service.impl.SeriesServiceImpl;
import org.blue.backend.permission.domain.Admin;
import org.blue.backend.util.BaseAction;
import org.blue.backend.util.PageBean;

import com.opensymphony.xwork2.ActionContext;

public class MediaAction extends BaseAction<Media>{

	private static final long serialVersionUID = 1L;
	
	private int[] mediaIds;
	private int adminId;
	private int seriesId;
	
	//自动封装
	private File upload;//上传文件
	private String uploadContentType;//上传文件类型
	private String uploadFileName;//上传文件名
	
	//通过struts.xml配置得到
	private String transcodeType;//指定转码格式
	private String pictureType;//指定关键帧图片格式
	private String savePath;//指定上传路径
	private String allowTypes;//指定允许上传的格式
	private String ffmpegPath;//指定ffmpeg路径
	private String defaultPicturePath;//默认路径
	
	private int pageNum = 1;//页数
	private int pageSize = 10;//每页显示多少条
	
	/**列表*/
	public String list() throws Exception{
		//准备数据：列表
		MediaService mediaService = new MediaServiceImpl();
		PageBean recordList = mediaService.getPageBean(pageNum,pageSize);
		ActionContext.getContext().getValueStack().push(recordList);
		//准备数据：搜索
		SeriesService seriesService = new SeriesServiceImpl();
		List<Series> seriesList = seriesService.findAllSimple();
		ActionContext.getContext().put("seriesList", seriesList);
		return "list";
	}
	/**删除*/
	public String delete() throws Exception{
		MediaService mediaService = new MediaServiceImpl();
		if(mediaIds!=null){
			mediaService.delete(mediaIds);
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
		
		//文件名
		String mediaName = uploadFileName.split("\\.")[0];
		//后缀名
		String mediaFormat = uploadFileName.split("\\.")[1];
		//使用UUID作为上传文件的物理文件名
		String mediaPath = savePath + "/" +UUID.randomUUID().toString()+"."+mediaFormat;
		
		//设置保存信息
		Series series = new Series();
		series.setSeriesId(seriesId);
		model.setSeries(series);
		Admin admin = new Admin();
		admin.setAdminId(adminId);
		model.setAdmin(admin);
		model.setMediaName(mediaName);
		model.setMediaFormat(mediaFormat);
		model.setMediaPath(mediaPath);
		model.setMediaPicture(defaultPicturePath);
		if(transcodeType.equals(mediaFormat)){
			model.setMediaStatus("未转码");
		}else{
			model.setMediaStatus("转码中");
		}
		model.setCreateDate(new Date());
		model.setUpdateDate(new Date());
		model.setIsVoted("0");
		model.setVoteCount(0);
		model.setVisitCount(0);
		
		//上传文件
		FileInputStream fis = new FileInputStream(upload);
		FileOutputStream fos = new FileOutputStream(ServletActionContext.getServletContext().getRealPath(mediaPath));
		
		byte[] buffer = new byte[8096];
		int len = 0;
		while((len=fis.read(buffer))>0){
			fos.write(buffer, 0, len);
			fos.flush();
		}
		
		fis.close();
		fos.close();
		
		//保存视频信息
		MediaService mediaService = new MediaServiceImpl();
		mediaService.save(model);
		
		//如果上传的是指定格式的文件就不转码
		if(!transcodeType.equals(mediaFormat)){
			
			//单独开线程:转码文件和关键帧抽取
			new Thread(new Runnable() {
				//转码需要的参数，R后缀是为了便于区分
				private Media modelR = model;
				private String ffmpegRealPathR = ServletActionContext.getServletContext().getRealPath(ffmpegPath);
				private String mediaRealPathR = ServletActionContext.getServletContext().getRealPath(model.getMediaPath());
				private String transcodeMediaRealPathR = mediaRealPathR.split("\\.")[0]+"."+transcodeType;
				private String mediaPicRealPathR = (transcodeMediaRealPathR.split("\\.")[0]+"."+pictureType).replace("temp", "images");
				
				public void run() {
					try{
						//转码
						MediaService mediaService = new MediaServiceImpl();
						mediaService.executeCodecs(ffmpegRealPathR, mediaRealPathR, transcodeMediaRealPathR, mediaPicRealPathR,modelR);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}).start();
		}
		return "toList";
	}
	/**修改页面*/
	public String editUI() throws Exception{
		//准备数据
		SeriesService seriesService = new SeriesServiceImpl();
		List<Series> seriesList = seriesService.findAllSimple();
		ActionContext.getContext().put("seriesList", seriesList);
		//回显
		MediaService mediaService = new MediaServiceImpl();
		Media media = mediaService.getById(model.getMediaId());
		ActionContext.getContext().getValueStack().push(media);
		seriesId = media.getSeries().getSeriesId();
		return "saveUI";
	}
	/**修改*/
	public String edit() throws Exception{
		Series series = new Series();
		series.setSeriesId(seriesId);
		model.setSeries(series);
		//更新
		MediaService mediaService = new MediaServiceImpl();
		mediaService.update(model);
		return "toList";
	}
	/**搜索*/
	public String search() throws Exception{
		//准备数据：列表
		Series series = new Series();
		series.setSeriesId(seriesId);
		model.setSeries(series);
		MediaService mediaService = new MediaServiceImpl();
		PageBean recordList = mediaService.search(model,pageNum,pageSize);
		ActionContext.getContext().getValueStack().push(recordList);
		//准备数据：搜索
		SeriesService seriesService = new SeriesServiceImpl();
		List<Series> seriesList = seriesService.findAllSimple();
		ActionContext.getContext().put("seriesList", seriesList);
		return "search";
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
		String filterResult = filterTypes(getAllowTypes().split(","));
		if(filterResult!=null){
			addFieldError("typeError", "您上传的文件类型不正确！");
		}
	}
	
	//getter&setter
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
	public void setSavePath(String savePath){
		this.savePath = savePath;
	}
	public String getSavePath() {
		return savePath;
	}
	public String getAllowTypes() {
		return allowTypes;
	}
	public void setAllowTypes(String allowTypes) {
		this.allowTypes = allowTypes;
	}
	public String getFfmpegPath() {
		return ffmpegPath;
	}
	public void setFfmpegPath(String ffmpegPath) {
		this.ffmpegPath = ffmpegPath;
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
	public String getTranscodeType() {
		return transcodeType;
	}
	public void setTranscodeType(String transcodeType) {
		this.transcodeType = transcodeType;
	}
	public String getPictureType() {
		return pictureType;
	}
	public void setPictureType(String pictureType) {
		this.pictureType = pictureType;
	}
	public String getDefaultPicturePath() {
		return defaultPicturePath;
	}
	public void setDefaultPicturePath(String defaultPicturePath) {
		this.defaultPicturePath = defaultPicturePath;
	}
	public int getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(int seriesId) {
		this.seriesId = seriesId;
	}
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public int[] getMediaIds() {
		return mediaIds;
	}
	public void setMediaIds(int[] mediaIds) {
		this.mediaIds = mediaIds;
	}
}
