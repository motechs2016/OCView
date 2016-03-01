package org.blue.backend.media.service;

import java.util.List;

import org.blue.backend.media.domain.Media;
import org.blue.backend.util.PageBean;

public interface MediaService {

	/**
     * 视频转码
     * @param ffmpegPath    转码工具的存放路径
     * @param upFilePath    用于指定要转换格式的文件,要截图的视频源文件
     * @param codcFilePath  格式转换后的的文件保存路径
     * @param mediaPicPath  截图保存路径
     * @param media
     * @throws Exception
     */
    public void executeCodecs(String ffmpegPath,String upFilePath, String codcFilePath, String mediaPicPath, Media media)throws Exception;
    
    /**
     * 保存
     * @param media
     * @return
     * @throws Exception
     */
    public void save(Media media)throws Exception;
    
    
    /**
     * 查询分页信息
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
	public PageBean getPageBean(int pageNum, int pageSize)throws Exception;

	/**
	 * 根据Id查找
	 * @param mediaId
	 * @return
	 * @throws Exception
	 */
	public Media getById(int mediaId)throws Exception;

	/**
	 * 删除
	 * @param teacherIds
	 * @throws Exception
	 */
	void delete(int[] ids) throws Exception;
	
	/**
	 * 更新
	 * @param model
	 * @throws Exception
	 */
	void update(Media model) throws Exception;

	/**
	 * 搜索
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageBean search(Media model,int pageNum, int pageSize) throws Exception;
	
	
	/**
	 * 得到热门列表
	 * @return
	 * @throws Exception
	 */
	public List<Media> getHotList() throws Exception;

	/**
	 * 得到最新课程
	 * @return
	 * @throws Exception
	 */
	public List<Media> getNewList() throws Exception;

	/**
	 * 找到某个系列的所有视频
	 * @param seriesId
	 * @return
	 * @throws Exception
	 */
	public List<Media> findBySeriesId(int seriesId) throws Exception;

	/**
	 * 检测用户是否已经投过票
	 * true表示没有投
	 * @param userId
	 * @param mediaId 
	 * @return
	 * @throws Exception
	 */
	public boolean checkVote(int userId, int mediaId) throws Exception;

	/**
	 * 执行投票操作并返回投票总数
	 * @param userId
	 * @param mediaId
	 * @return
	 * @throws Exception
	 */
	public int vote(int userId, int mediaId) throws Exception;
}
