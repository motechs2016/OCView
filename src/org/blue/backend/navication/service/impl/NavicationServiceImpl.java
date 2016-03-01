package org.blue.backend.navication.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.blue.backend.media.domain.Series;
import org.blue.backend.media.service.SeriesService;
import org.blue.backend.media.service.impl.SeriesServiceImpl;
import org.blue.backend.navication.domain.Navication;
import org.blue.backend.navication.service.NavicationService;
import org.blue.backend.util.JdbcUtils;

/**
 * “导航”服务实现类
 * @author ldc4
 */
public class NavicationServiceImpl implements NavicationService{

	private Connection conn = null;
	
	public NavicationServiceImpl() throws SQLException{
		conn = JdbcUtils.getConnection();
	}
	
	@Override
	public List<Navication> findByParentId(int parentId) throws Exception {
		List<Navication> navList = new ArrayList<Navication>();
		String sql = "select * from navication where parentId = ? order by navicationOrder asc";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, parentId);		
		ResultSet rs = pstmt.executeQuery();
		Navication nav = null;
		while(rs.next()){
			nav = new Navication();
			nav.setNavicationId(rs.getInt("navicationId"));
			nav.setNavicationName(rs.getString("navicationName"));
			nav.setNavicationDescription(rs.getString("navicationDescription"));
			nav.setNavicationUrl(rs.getString("navicationUrl"));
			nav.setNavicationOrder(rs.getInt("navicationOrder"));
			nav.setNavicationTarget(rs.getString("navicationTarget")); 
			//上级
			Navication parent = new Navication();
			parent.setNavicationId(rs.getInt("parentId"));
			nav.setParent(parent);
			navList.add(nav);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return navList;
	}

	@Override
	public void delete(int[] ids) throws Exception {
		conn.setAutoCommit(false);
		PreparedStatement pstmt = null;
		try{
			
			List<Integer> deleteIds = null;
			for(int i=0;i<ids.length;i++){
				deleteIds = new ArrayList<Integer>();
				//找到待删除的列表
				findIdList(ids[i],conn,deleteIds);
				//删除
				String sql = "delete from navication where navicationId = ?";
				pstmt = conn.prepareStatement(sql);
				for(int deleteId : deleteIds){
					pstmt.setInt(1, deleteId);
					pstmt.addBatch();
				}
				pstmt.executeBatch();
			}
			
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			JdbcUtils.free(null, pstmt, conn);
		}
		
	}
	
	@Override
	public void findIdList(int navicationId,Connection conn,List<Integer> ids) throws Exception {
		String sql = "select navicationId from navication where parentId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, navicationId);
		ResultSet rs = pstmt.executeQuery();
		//添加进list
		ids.add(navicationId);
		while(rs.next()){
			findIdList(rs.getInt("navicationId"), conn , ids);
		}
		JdbcUtils.free(rs, pstmt, null);
	}

	
	public Navication getById(int navicationId) throws Exception {
		String sql = "select * from navication where navicationId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, navicationId);
		ResultSet rs = pstmt.executeQuery();
		Navication nav = null;
		if(rs.next()){
			nav = new Navication();
			nav.setNavicationId(rs.getInt("navicationId"));
			nav.setNavicationName(rs.getString("navicationName"));
			nav.setNavicationDescription(rs.getString("navicationDescription"));
			nav.setNavicationUrl(rs.getString("navicationUrl"));
			nav.setNavicationOrder(rs.getInt("navicationOrder"));
			nav.setNavicationTarget(rs.getString("navicationTarget")); 
			//上级
			Navication parent = new Navication();
			parent.setNavicationId(rs.getInt("parentId"));
			nav.setParent(parent);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return nav;
	}

	@Override
	public List<Navication> findTopList(boolean lazy1,boolean lazy2) throws Exception {
		List<Navication> topList = new ArrayList<Navication>();
		String sql = "select navicationId,navicationName,navicationUrl,navicationTarget from navication where parentId = 0 order by navicationOrder";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		Navication nav = null;
		while(rs.next()){
			nav = new Navication();
			//需要id,name,url,target
			nav.setNavicationId(rs.getInt("navicationId"));
			nav.setNavicationName(rs.getString("navicationName"));
			nav.setNavicationUrl(rs.getString("navicationUrl"));
			nav.setNavicationTarget(rs.getString("navicationTarget"));
			topList.add(nav);
		}
		if(!lazy1){
			for(Navication top : topList){
				findChildren(top, conn, lazy2);
			}
		}
		JdbcUtils.free(rs, pstmt, conn);
		return topList;
	}

	@Override
	public void findChildren(Navication navication , Connection conn, boolean lazy) throws Exception {
		String sql = "select navicationId,navicationName,navicationUrl,navicationTarget from navication where parentId = ? order by navicationOrder";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, navication.getNavicationId());
		ResultSet rs = pstmt.executeQuery();
		List<Navication> children = new ArrayList<Navication>();
		Navication nav = null;
		while(rs.next()){
			nav = new Navication();
			//需要id、name、url
			int navicationId = rs.getInt("navicationId");
			nav.setNavicationId(navicationId);
			nav.setNavicationName(rs.getString("navicationName"));
			nav.setNavicationUrl(rs.getString("navicationUrl"));
			nav.setNavicationTarget(rs.getString("navicationTarget"));
			if(!lazy){
				//查找该导航下的所有系列
				SeriesService seriesService = new SeriesServiceImpl();
				List<Series> seriesList = seriesService.getByNavicationId(navicationId);
				nav.setSeriesList(seriesList);
			}
			//递归查找
			findChildren(nav,conn,lazy);
			children.add(nav);
		}
		navication.setChildren(children);
		JdbcUtils.free(rs, pstmt, null);
	}

	@Override
	public void save(Navication model) throws Exception {
		conn.setAutoCommit(false);
		PreparedStatement pstmt = null;
		try{
			//插入一条新纪录，保留navicationOrder字段为0
			String sql  = "insert into navication(navicationName,navicationDescription,navicationUrl,navicationTarget,navicationOrder,parentId) values(?,?,?,?,0,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, model.getNavicationName());
			pstmt.setString(2, model.getNavicationDescription());
			pstmt.setString(3, model.getNavicationUrl());
			pstmt.setString(4, model.getNavicationTarget());
			pstmt.setInt(5, model.getParent().getNavicationId());
			pstmt.executeUpdate();
			//获取插入后的主键
			ResultSet rs = pstmt.getGeneratedKeys();
			int navicationId = 0;
			if(rs.next()) {
				navicationId = ((Long)rs.getObject(1)).intValue();
	        }
			//使navicationOrder的值和主键保持一致
			sql = "update navication set navicationOrder = ? where navicationId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, navicationId);
			pstmt.setInt(2, navicationId);
			pstmt.executeUpdate();
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			e.printStackTrace();
		}finally{
			JdbcUtils.free(null, pstmt, conn);
		}
	}

	@Override
	public void update(Navication model) throws Exception {
		String sql = "update navication set navicationName = ?,navicationUrl = ?,navicationDescription = ?,navicationTarget = ? where navicationId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, model.getNavicationName());
		pstmt.setString(2, model.getNavicationUrl());
		pstmt.setString(3, model.getNavicationDescription());
		pstmt.setString(4, model.getNavicationTarget());
		pstmt.setInt(5, model.getNavicationId());
		pstmt.executeUpdate();
		JdbcUtils.free(null, pstmt, conn);
	}

	@Override
	public void moveUp(int navicationId) throws Exception {
		conn.setAutoCommit(false);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			//先找到navicationId对应的parentId,navicationOrder
			String sql = "select parentId,navicationOrder from navication where navicationId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, navicationId);
			rs = pstmt.executeQuery();
			int parentId = 0;
			int navicationOrder = 0;
			if(rs.next()){
				parentId = rs.getInt("parentId");
				navicationOrder = rs.getInt("navicationOrder");
			}else{
				return ;
			}
			//然后找到该级下的比navicationId略小的那个对象
			sql = "select navicationId,navicationOrder from navication where parentId = ? and navicationOrder < ? order by navicationOrder DESC limit 0,1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parentId);
			pstmt.setInt(2, navicationOrder);
			rs = pstmt.executeQuery();
			int otherId = 0;
			int otherOrder = 0;
			if(rs.next()){
				otherId = rs.getInt("navicationId");
				otherOrder = rs.getInt("navicationOrder");
			}else{
				return ;
			}
			//交换order值
			sql = "update navication set navicationOrder = ? where navicationId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, otherOrder);
			pstmt.setInt(2, navicationId);
			pstmt.addBatch();
			pstmt.setInt(1, navicationOrder);
			pstmt.setInt(2, otherId);
			pstmt.addBatch();
			pstmt.executeBatch();
			
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			e.printStackTrace();
		}finally{
			JdbcUtils.free(rs, pstmt, conn);
		}
		
	}

	@Override
	public void moveDown(int navicationId) throws Exception {
		conn.setAutoCommit(false);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			//先找到navicationId对应的parentId,navicationOrder
			String sql = "select parentId,navicationOrder from navication where navicationId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, navicationId);
			rs = pstmt.executeQuery();
			int parentId = 0;
			int navicationOrder = 0;
			if(rs.next()){
				parentId = rs.getInt("parentId");
				navicationOrder = rs.getInt("navicationOrder");
			}else{
				return ;
			}
			//然后找到该级下的比navicationId略大的那个对象
			sql = "select navicationId,navicationOrder from navication where parentId = ? and navicationOrder > ? order by navicationOrder ASC limit 0,1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parentId);
			pstmt.setInt(2, navicationOrder);
			rs = pstmt.executeQuery();
			int otherId = 0;
			int otherOrder = 0;
			if(rs.next()){
				otherId = rs.getInt("navicationId");
				otherOrder = rs.getInt("navicationOrder");
			}else{
				return ;
			}
			//交换order值
			sql = "update navication set navicationOrder = ? where navicationId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, otherOrder);
			pstmt.setInt(2, navicationId);
			pstmt.addBatch();
			pstmt.setInt(1, navicationOrder);
			pstmt.setInt(2, otherId);
			pstmt.addBatch();
			pstmt.executeBatch();
			
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			e.printStackTrace();
		}finally{
			JdbcUtils.free(rs, pstmt, conn);
		}
	}

}
