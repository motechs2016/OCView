package org.blue.backend.media.domain;

public class Recommend {
	//主属性
	private int recommendId;
	//基本属性
	private String picturePath;
	private String backgroundColor;
	//特殊属性
	private int recommendOrder;
	//关联属性
	private Series series;
	
	public int getRecommendId() {
		return recommendId;
	}
	public void setRecommendId(int recommendId) {
		this.recommendId = recommendId;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public int getRecommendOrder() {
		return recommendOrder;
	}
	public void setRecommendOrder(int recommendOrder) {
		this.recommendOrder = recommendOrder;
	}
	public Series getSeries() {
		return series;
	}
	public void setSeries(Series series) {
		this.series = series;
	}
}
