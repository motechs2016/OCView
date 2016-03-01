package org.blue.backend.media.domain;

import org.blue.backend.navication.domain.Navication;

public class Series {
	
	//主属性
	private int seriesId;				//系列编号
	
	//基本属性
	private String seriesName;			//系列名称
	private String seriesDescription;	//系列描述
	private String seriesInfo;			//系列简述
	
	//特殊属性
	private int courseNum;				//课程数
	private String picturePath;			//图片路径
	
	//关联属性
	private Navication navication;		//所属导航
	private Teacher teacher;			//讲师
	
	
	public int getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(int seriesId) {
		this.seriesId = seriesId;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	public String getSeriesDescription() {
		return seriesDescription;
	}
	public void setSeriesDescription(String seriesDescription) {
		this.seriesDescription = seriesDescription;
	}
	public int getCourseNum() {
		return courseNum;
	}
	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public Navication getNavication() {
		return navication;
	}
	public void setNavication(Navication navication) {
		this.navication = navication;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public String getSeriesInfo() {
		return seriesInfo;
	}
	public void setSeriesInfo(String seriesInfo) {
		this.seriesInfo = seriesInfo;
	}
}
