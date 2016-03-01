package org.blue.backend.media.domain;

public class Teacher {
	//主属性
	private int teacherId;//讲师编号
	//基本属性
	private String teacherName;//讲师姓名
	private String teacherJob;//讲师职业
	private String teacherDegree;//讲师学位
	private String teacherCollege;//讲师学院
	private String teacherDescription;//讲师描述
	private String teacherPicture;//讲师照片
	
	
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeacherJob() {
		return teacherJob;
	}
	public void setTeacherJob(String teacherJob) {
		this.teacherJob = teacherJob;
	}
	public String getTeacherDegree() {
		return teacherDegree;
	}
	public void setTeacherDegree(String teacherDegree) {
		this.teacherDegree = teacherDegree;
	}
	public String getTeacherCollege() {
		return teacherCollege;
	}
	public void setTeacherCollege(String teacherCollege) {
		this.teacherCollege = teacherCollege;
	}
	public String getTeacherDescription() {
		return teacherDescription;
	}
	public void setTeacherDescription(String teacherDescription) {
		this.teacherDescription = teacherDescription;
	}
	public String getTeacherPicture() {
		return teacherPicture;
	}
	public void setTeacherPicture(String teacherPicture) {
		this.teacherPicture = teacherPicture;
	}
}
