package org.blue.ocview.index.domain;

/**
 * 搜索结果类
 * @author ldc4
 */
public class SearchBean {
	
	private int seriesId;
	private String seriesName;
	private String seriesDescription;
	private String picturePath;
	
	private String teacherName;
	private String teacherCollege;
	
	private String navicationName;

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

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherCollege() {
		return teacherCollege;
	}

	public void setTeacherCollege(String teacherCollege) {
		this.teacherCollege = teacherCollege;
	}

	public String getNavicationName() {
		return navicationName;
	}

	public void setNavicationName(String navicationName) {
		this.navicationName = navicationName;
	}
	
}
