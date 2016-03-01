package org.blue.backend.media.domain;

import java.util.Date;

import org.blue.backend.permission.domain.Admin;

public class Media {
	//主属性
	private int mediaId; 				//视频编号
	
	//基本属性
	private String mediaName; 			//视频名称
	private String mediaDescription; 	//视频描述
	private String mediaFormat; 		//视频格式
	private String mediaPath;			//视频路径
	private String mediaPicture;		//视频图片
	private String mediaStatus;			//视频状态
	
	//特殊属性
	private Date createDate; 			//创建日期
	private Date updateDate; 			//更新日期
	private int voteCount;	 			//投票总数
	private String isVoted;  			//是否支持投票
	private int visitCount;				//访问次数
	
	//关联属性
	private Admin admin;				//上传视频的管理员
	private Series series;				//所属系列
	
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	public String getMediaDescription() {
		return mediaDescription;
	}
	public void setMediaDescription(String mediaDescription) {
		this.mediaDescription = mediaDescription;
	}
	public String getMediaFormat() {
		return mediaFormat;
	}
	public void setMediaFormat(String mediaFormat) {
		this.mediaFormat = mediaFormat;
	}
	public java.sql.Timestamp getCreateDate() {
		return new java.sql.Timestamp(createDate.getTime());
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public java.sql.Timestamp getUpdateDate() {
		return new java.sql.Timestamp(updateDate.getTime());
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public int getVoteCount() {
		return voteCount;
	}
	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}
	public String getIsVoted() {
		return isVoted;
	}
	public void setIsVoted(String isVoted) {
		this.isVoted = isVoted;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public Series getSeries() {
		return series;
	}
	public void setSeries(Series series) {
		this.series = series;
	}
	public String getMediaPath() {
		return mediaPath;
	}
	public void setMediaPath(String mediaPath) {
		this.mediaPath = mediaPath;
	}
	public String getMediaPicture() {
		return mediaPicture;
	}
	public void setMediaPicture(String mediaPicture) {
		this.mediaPicture = mediaPicture;
	}
	public String getMediaStatus() {
		return mediaStatus;
	}
	public void setMediaStatus(String mediaStatus) {
		this.mediaStatus = mediaStatus;
	}
	public int getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
}
