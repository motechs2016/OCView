package org.blue.backend.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.blue.backend.media.domain.Series;
import org.blue.backend.media.service.SeriesService;
import org.blue.backend.media.service.impl.SeriesServiceImpl;
import org.blue.backend.navication.domain.Navication;
import org.blue.ocview.index.domain.SecondNav;

/**
 * 导航数据处理工具
 * @author ldc4
 */
public class NavicationUtils {

	/**
	 * 作树形处理
	 * @param topList
	 * @param isPrefix
	 * @return
	 */
	public static List<Navication> getAllNavication(List<Navication> topList,boolean isPrefix) {
		
		String prefix = "";
		
		if(isPrefix){
			prefix = "|-";
		}
		
		List<Navication> list = new ArrayList<Navication>();
		walkNavicationTreeList(topList,prefix,list,isPrefix);
		
		return list;
	}
	
	/**
	 * 辅助树形处理的递归类
	 * @param topList
	 * @param prefix
	 * @param list
	 * @param isPrefix
	 */
	private static void walkNavicationTreeList(List<Navication> topList,String prefix,List<Navication> list,boolean isPrefix){
		for(Navication top: topList){
			
			Navication copy = new Navication();
			copy.setNavicationId(top.getNavicationId());
			copy.setNavicationName(prefix + top.getNavicationName());
			copy.setNavicationUrl(top.getNavicationUrl());
			
			list.add(copy);
			
			walkNavicationTreeList(top.getChildren(),isPrefix ? "　　　"+prefix:prefix,list,isPrefix);
		}
	}
	
	/**
	 * 作双层list,以便符合首页的数据要求
	 * @param topList
	 * @return
	 */
	public static List<SecondNav> getSecendNavication1(List<Navication> topList){
		
		List<SecondNav> list = new ArrayList<SecondNav>();
		
		for(Navication top: topList){
			//每个顶级导航对应的二级导航
			SecondNav secondNav = new SecondNav();
			//二级导航列表
			List<Navication> navList = new ArrayList<Navication>();
			//最新7个系列
			try {
				SeriesService seriesService = new SeriesServiceImpl();
				List<Series> seriesList = seriesService.findLastSeven(top.getNavicationId());
				secondNav.setSeriesList(seriesList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			for(Navication nav : top.getChildren()){
				
				Navication copy = new Navication();
				copy.setNavicationId(nav.getNavicationId());
				copy.setNavicationName(nav.getNavicationName());
				copy.setNavicationUrl(nav.getNavicationUrl());
				copy.setNavicationTarget(nav.getNavicationTarget());
				navList.add(copy);
				
			}
			secondNav.setNavList(navList);
			list.add(secondNav);
		}
		return list;
	}
	
	/**
	 * 作双层list,以便符合主题页面的数据要求
	 * @param topList
	 * @return
	 */
	public static List<SecondNav> getSecendNavication2(List<Navication> topList){
		
		List<SecondNav> list = new ArrayList<SecondNav>();
		
		for(Navication top: topList){
			//每个顶级导航对应的二级导航
			SecondNav secondNav = new SecondNav();
			//二级导航列表
			List<Navication> navList = new ArrayList<Navication>();
			
			for(Navication nav : top.getChildren()){
				
				Navication copy = new Navication();
				copy.setNavicationId(nav.getNavicationId());
				copy.setNavicationName(nav.getNavicationName());
				copy.setNavicationUrl(nav.getNavicationUrl());
				copy.setNavicationTarget(nav.getNavicationTarget());
				copy.setSeriesList(nav.getSeriesList());
				
				navList.add(copy);
				
			}
			secondNav.setNavList(navList);
			list.add(secondNav);
		}
		return list;
	}
	
	/**
	 * 作Map,实现二级联动下拉框
	 * @param topList
	 * @return
	 */
	public static Map<Navication,List<Navication>> getMapNavication(List<Navication> topList){
		Map<Navication,List<Navication>> navMap = new TreeMap<Navication, List<Navication>>();
		
		for(Navication top: topList){
			//二级导航列表
			List<Navication> navList = new ArrayList<Navication>();
			
			for(Navication nav : top.getChildren()){
				
				Navication copy = new Navication();
				copy.setNavicationId(nav.getNavicationId());
				copy.setNavicationName(nav.getNavicationName());
				copy.setNavicationUrl(nav.getNavicationUrl());
				copy.setNavicationTarget(nav.getNavicationTarget());
				navList.add(copy);
				
			}
			navMap.put(top, navList);
		}
		
		return navMap;
	}
	
}
