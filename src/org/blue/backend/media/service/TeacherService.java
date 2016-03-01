package org.blue.backend.media.service;

import java.util.List;

import org.blue.backend.media.domain.Teacher;
import org.blue.backend.util.PageBean;

public interface TeacherService {

	/**
	 * 获取分页信息
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	PageBean getPageBean(int pageNum, int pageSize) throws Exception;

	/**
	 * 删除
	 * @param teacherIds
	 * @throws Exception
	 */
	void delete(int[] ids) throws Exception;

	/**
	 * 保存
	 * @param model
	 * @throws Exception
	 */
	void save(Teacher model) throws Exception;

	/**
	 * 根据Id查找
	 * @param teacherId
	 * @return
	 * @throws Exception
	 */
	Teacher getById(int id) throws Exception;

	/**
	 * 更新
	 * @param model
	 * @throws Exception
	 */
	void update(Teacher model) throws Exception;

	/**
	 * 查询所有的（id和teacherName）
	 * @return
	 * @throws Exception
	 */
	List<Teacher> findAllSimple() throws Exception;
	
}
