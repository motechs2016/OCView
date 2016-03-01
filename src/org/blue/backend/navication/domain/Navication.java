package org.blue.backend.navication.domain;

import java.util.ArrayList;
import java.util.List;

import org.blue.backend.media.domain.Series;

/**
 * 导航类
 * @author ldc4
 */
public class Navication implements Comparable<Navication>{
	//主属性
	private int navicationId;				//导航编号
	//基本属性
	private String navicationName;		 	//导航名称
	private String navicationDescription; 	//导航描述
	private String navicationUrl;			//导航链接
	private String navicationTarget; 		//链接打开的方式,对应a标签的target属性
	//特殊属性
	private int navicationOrder; 			//导航顺序
	
	//关联属性
	private Navication parent;//上级
	private List<Navication> children = new ArrayList<Navication>();
	private List<Series> seriesList = new ArrayList<Series>();//该导航下的所属系列
	//无参构造方法
	public Navication(){}

	//getter&setter
	public int getNavicationId() {
		return navicationId;
	}
	public void setNavicationId(int navicationId) {
		this.navicationId = navicationId;
	}
	public String getNavicationName() {
		return navicationName;
	}
	public void setNavicationName(String navicationName) {
		this.navicationName = navicationName;
	}
	public String getNavicationDescription() {
		return navicationDescription;
	}
	public void setNavicationDescription(String navicationDescription) {
		this.navicationDescription = navicationDescription;
	}
	public String getNavicationUrl() {
		return navicationUrl;
	}
	public void setNavicationUrl(String navicationUrl) {
		this.navicationUrl = navicationUrl;
	}
	public int getNavicationOrder() {
		return navicationOrder;
	}
	public void setNavicationOrder(int navicationOrder) {
		this.navicationOrder = navicationOrder;
	}
	public Navication getParent() {
		return parent;
	}
	public void setParent(Navication parent) {
		this.parent = parent;
	}
	public List<Navication> getChildren() {
		return children;
	}
	public void setChildren(List<Navication> children) {
		this.children = children;
	}
	public String getNavicationTarget() {
		return navicationTarget;
	}
	public void setNavicationTarget(String navicationTarget) {
		this.navicationTarget = navicationTarget;
	}
	public List<Series> getSeriesList() {
		return seriesList;
	}
	public void setSeriesList(List<Series> seriesList) {
		this.seriesList = seriesList;
	}
	
	@Override
	public int compareTo(Navication o) {
		return this.getNavicationId() - o.getNavicationId();
	}
}
