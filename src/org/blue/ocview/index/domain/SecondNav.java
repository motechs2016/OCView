package org.blue.ocview.index.domain;

import java.util.ArrayList;
import java.util.List;

import org.blue.backend.media.domain.Series;
import org.blue.backend.navication.domain.Navication;

/**
 * 整个二级导航
 * @author ldc4
 */
public class SecondNav {
	
	//每个二级导航列表
	private List<Navication> navList = new ArrayList<Navication>();

	//最新7个系列
	private List<Series> seriesList;
	
	public List<Navication> getNavList() {
		return navList;
	}

	public void setNavList(List<Navication> navList) {
		this.navList = navList;
	}

	public List<Series> getSeriesList() {
		return seriesList;
	}

	public void setSeriesList(List<Series> seriesList) {
		this.seriesList = seriesList;
	}

}
