package org.blue.backend.media.service;

import java.util.List;

import org.blue.backend.media.domain.Series;
import org.blue.backend.util.PageBean;
import org.blue.ocview.index.domain.SearchBean;

public interface SeriesService {

	/**
	 * 查询分页信息
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	PageBean getPageBean(int pageNum, int pageSize) throws Exception;

	/**
	 * 删除
	 * @param ids
	 * @throws Exception
	 */
	void delete(int[] ids) throws Exception;

	/**
	 * 保存
	 * @param model
	 * @throws Exception
	 */
	void save(Series model) throws Exception;

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Series getById(int id) throws Exception;

	/**
	 * 更新
	 * @param model
	 * @throws Exception
	 */
	void update(Series model) throws Exception;

	/**
	 * 查询所有（id和seriesName）
	 * @return
	 * @throws Exception
	 */
	List<Series> findAllSimple() throws Exception;

	/**
	 * 根据导航查找最新7个
	 * @param navicationId
	 * @return
	 * @throws Exception
	 */
	List<Series> findLastSeven(int navicationId) throws Exception;

	/**
	 * 根据导航查找所有
	 * @param navicationId
	 * @return
	 */
	List<Series> getByNavicationId(int navicationId) throws Exception;

	/**
	 * 搜索
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	List<SearchBean> search(String keyword) throws Exception;

}
