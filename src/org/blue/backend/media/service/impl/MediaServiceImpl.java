package org.blue.backend.media.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.blue.backend.media.domain.Media;
import org.blue.backend.media.domain.Series;
import org.blue.backend.media.service.MediaService;
import org.blue.backend.util.JdbcUtils;
import org.blue.backend.util.PageBean;



public class MediaServiceImpl implements MediaService{

	private Connection conn = null;
	
	public MediaServiceImpl() throws SQLException {
		conn = JdbcUtils.getConnection();
	}
	
    public void executeCodecs(String ffmpegPath, String upFilePath, String codcFilePath,
            String mediaPicPath, Media media) throws Exception {
//    	 高品质：ffmpeg -i infile -ab 128 -acodec libmp3lame -ac 1 -ar 22050 -r 29.97 -qscale 6 -y outfile
//		 低品质：ffmpeg -i infile -ab 128 -acodec libmp3lame -ac 1 -ar 22050 -r 29.97 -b 512 -y outfile
//    	问题的关键是处在输入流缓冲区那个地方，子进程的产生的输出流没有被JVM及时的读取最后缓冲区满了就卡住了
    	
        // 创建一个List集合来保存转换视频文件为flv格式的命令
        List<String> convert = new ArrayList<String>();
        convert.add(ffmpegPath); 	// 添加转换工具路径
        convert.add("-loglevel");	// 设置输出
        convert.add("quiet");		// 不让子进程向输入流写入数据
        convert.add("-i"); 			// 添加参数＂-i＂，该参数指定要转换的文件
        convert.add(upFilePath); 	// 添加要转换格式的视频文件的路径
        convert.add("-ab");        	// 设置音频码率
        convert.add("64");
        convert.add("-ac");       	// 设置声道数
        convert.add("2");
        convert.add("-ar");        	// 设置声音的采样频率
        convert.add("22050");
        convert.add("-r");         	// 设置帧频
        convert.add("24");
        convert.add("-qscale");     // 指定转换的质量
        convert.add("6");
        convert.add("-y"); 			// 添加参数＂-y＂，该参数指定将覆盖已存在的文件
        convert.add(codcFilePath);

        // 创建一个List集合来保存从视频中截取图片的命令
        List<String> cutpic = new ArrayList<String>();
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(upFilePath); 	// 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss"); 			// 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add("1"); 			// 添加起始时间为第1秒
        cutpic.add("-t"); 			// 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("0.001"); 		// 添加持续时间为1毫秒
        cutpic.add("-s"); 			// 添加参数＂-s＂，该参数指定截取的图片大小
        cutpic.add("800*400"); 		// 添加截取的图片大小为800*400
        cutpic.add(mediaPicPath); 	// 添加截取的图片的保存路径
        
        ProcessBuilder builder = new ProcessBuilder();
       
        builder.command(convert);
        builder.redirectErrorStream(true);
        Process p1 = builder.start();
        
        builder.command(cutpic);
        builder.redirectErrorStream(true);
        // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
        // 因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
        Process p2 = builder.start();
        
        
        //等待转码和关键帧抽取完成
        if(p2.waitFor()==0&&p1.waitFor()==0){
        	//更改视频信息
        	String sql = "update media set mediaFormat=?,mediaPath=?,mediaPicture=?,mediaStatus=? where mediaId=?";
        	PreparedStatement pstmt = conn.prepareStatement(sql);
        	pstmt.setString(1, codcFilePath.split("\\.")[1]);
        	pstmt.setString(2, media.getMediaPath().split("\\.")[0]+"."+codcFilePath.split("\\.")[1]);
        	pstmt.setString(3, (media.getMediaPath().split("\\.")[0]+"."+mediaPicPath.split("\\.")[1]).replace("temp", "images"));
        	pstmt.setString(4, "转码成功");
        	pstmt.setInt(5, media.getMediaId());
        	pstmt.executeUpdate();
            JdbcUtils.free(null, pstmt, conn);
        }else{
        	String sql = "update media set mediaStatus=? where mediaId=?";
        	PreparedStatement pstmt = conn.prepareStatement(sql);
        	pstmt.setString(1, "转码失败");
        	pstmt.setInt(2, media.getMediaId());
        	pstmt.executeUpdate();
            JdbcUtils.free(null, pstmt, conn);
        }
        
    }

	@Override
	public void save(Media media) throws Exception {
		String sql = "insert into media(mediaName,mediaDescription,mediaFormat,mediaPath,mediaPicture,mediaStatus,createDate,updateDate,voteCount,isVoted,visitCount,seriesId,adminId) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, media.getMediaName());
		pstmt.setString(2, media.getMediaDescription());
		pstmt.setString(3, media.getMediaFormat());
		pstmt.setString(4, media.getMediaPath());
		pstmt.setString(5, media.getMediaPicture());
		pstmt.setString(6, media.getMediaStatus());
		pstmt.setTimestamp(7, media.getCreateDate());
		pstmt.setTimestamp(8, media.getUpdateDate());
		pstmt.setInt(9, media.getVoteCount());
		pstmt.setString(10, media.getIsVoted());
		pstmt.setInt(11, media.getVisitCount());
		pstmt.setInt(12, media.getSeries().getSeriesId());
		pstmt.setInt(13, media.getAdmin().getAdminId());
		pstmt.executeUpdate();
		//获取自增长主键值
		ResultSet rs = pstmt.getGeneratedKeys();
		int mediaId = 0;
		if(rs.next()) {
			mediaId = ((Long)rs.getObject(1)).intValue();
        }
		media.setMediaId(mediaId);
		JdbcUtils.free(null, pstmt, conn);
	}

	@Override
	public PageBean getPageBean(int pageNum, int pageSize) throws Exception {

		List<Media> recordList = new ArrayList<Media>();
		String sql = "select * from media limit ?,?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, pageSize*(pageNum-1));
		pstmt.setInt(2, pageSize);
		ResultSet rs = pstmt.executeQuery();
		Media media = null;
		while(rs.next()){
			media = new Media();
			media.setMediaId(rs.getInt("mediaId"));
			media.setMediaName(rs.getString("mediaName"));
			media.setMediaDescription(rs.getString("mediaDescription"));
			media.setMediaStatus(rs.getString("mediaStatus"));
			media.setIsVoted(rs.getString("isVoted"));
			media.setVoteCount(rs.getInt("voteCount"));
			recordList.add(media);
		}
		//查询总记录数
		sql = "select count(*) from media";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		int recordCount = 0;
		if(rs.next()){
			recordCount = rs.getInt(1);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return new PageBean(pageNum, pageSize, recordCount, recordList);
	}

	@Override
	public Media getById(int mediaId) throws Exception {
		String sql = "select * from media where mediaId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, mediaId);
		ResultSet rs = pstmt.executeQuery();
		Media media = null;
		if(rs.next()){
			media = new Media();
			media.setMediaId(rs.getInt("mediaId"));
			media.setMediaName(rs.getString("mediaName"));
			media.setMediaDescription(rs.getString("mediaDescription"));
			media.setIsVoted(rs.getString("isVoted"));
			media.setVoteCount(rs.getInt("voteCount"));
			Series series = new Series();
			series.setSeriesId(rs.getInt("seriesId"));
			media.setSeries(series);
			media.setMediaPath(rs.getString("mediaPath"));
			media.setMediaPicture(rs.getString("mediaPicture"));
		}
		JdbcUtils.free(rs, pstmt, conn);
		return media;
	}

	@Override
	public void delete(int[] ids) throws Exception {
		conn.setAutoCommit(false);
		String sql = "delete from media where mediaId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		try{
			for(int i=0;i<ids.length;i++){
				pstmt.setInt(1, ids[i]);
				pstmt.executeUpdate();
			}
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
		JdbcUtils.free(null, pstmt, conn);
	}

	@Override
	public void update(Media model) throws Exception {
		String sql = "update media set seriesId=?,mediaDescription=?,isVoted=? where mediaId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, model.getSeries().getSeriesId());
		pstmt.setString(2, model.getMediaDescription());
		pstmt.setString(3, model.getIsVoted());
		pstmt.setInt(4, model.getMediaId());
		pstmt.executeUpdate();
		JdbcUtils.free(null, pstmt, conn);
	}

	@Override
	public PageBean search(Media model,int pageNum, int pageSize) throws Exception {
		List<Media> recordList = new ArrayList<Media>();
		String sql = "select * from media where mediaName like ? and mediaStatus like ? and isVoted like ? and seriesId like ? limit ?,?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		//增加判断条件
		if(model.getMediaName()==null||model.getMediaName().equals("")){
			pstmt.setString(1, "%");
		}else{
			pstmt.setString(1, "%"+model.getMediaName()+"%");
		}
		if(model.getMediaStatus().equals("不限")){
			pstmt.setString(2, "%");
		}else{
			pstmt.setString(2, model.getMediaStatus());
		}
		if(model.getIsVoted().equals("2")){
			pstmt.setString(3, "%");
		}else{
			pstmt.setString(3, model.getIsVoted());
		}
		if(model.getSeries().getSeriesId()==0){
			pstmt.setString(4, "%");
		}else{
			pstmt.setInt(4, model.getSeries().getSeriesId());
		}
		pstmt.setInt(5, pageSize*(pageNum-1));
		pstmt.setInt(6, pageSize);
		ResultSet rs = pstmt.executeQuery();
		Media media = null;
		while(rs.next()){
			media = new Media();
			media.setMediaId(rs.getInt("mediaId"));
			media.setMediaName(rs.getString("mediaName"));
			media.setMediaDescription(rs.getString("mediaDescription"));
			media.setMediaStatus(rs.getString("mediaStatus"));
			media.setIsVoted(rs.getString("isVoted"));
			media.setVoteCount(rs.getInt("voteCount"));
			recordList.add(media);
		}
		//查询总记录数
		sql = "select count(*) from media where mediaName like ? and mediaStatus like ? and isVoted like ? and seriesId like ?";
		pstmt = conn.prepareStatement(sql);
		//增加判断条件
		if(model.getMediaName()==null||model.getMediaName().equals("")){
			pstmt.setString(1, "%");
		}else{
			pstmt.setString(1, "%"+model.getMediaName()+"%");
		}
		if(model.getMediaStatus().equals("不限")){
			pstmt.setString(2, "%");
		}else{
			pstmt.setString(2, model.getMediaStatus());
		}
		if(model.getIsVoted().equals("2")){
			pstmt.setString(3, "%");
		}else{
			pstmt.setString(3, model.getIsVoted());
		}
		if(model.getSeries().getSeriesId()==0){
			pstmt.setString(4, "%");
		}else{
			pstmt.setInt(4, model.getSeries().getSeriesId());
		}
		rs = pstmt.executeQuery();
		int recordCount = 0;
		if(rs.next()){
			recordCount = rs.getInt(1);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return new PageBean(pageNum, pageSize, recordCount, recordList);
	}
	
	
	@Override
	public List<Media> getHotList() throws Exception {
		List<Media> hotList = new ArrayList<Media>();
		String sql = "select mediaId,mediaName from media order by visitCount desc limit 0,12";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		Media media = null;
		while(rs.next()){
			media = new Media();
			media.setMediaId(rs.getInt("mediaId"));
			media.setMediaName(rs.getString("mediaName"));
			hotList.add(media);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return hotList;
	}

	@Override
	public List<Media> getNewList() throws Exception {
		List<Media> newList = new ArrayList<Media>();
		String sql = "select mediaId,mediaName from media order by updateDate desc limit 0,12";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		Media media = null;
		while(rs.next()){
			media = new Media();
			media.setMediaId(rs.getInt("mediaId"));
			media.setMediaName(rs.getString("mediaName"));
			newList.add(media);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return newList;
	}

	@Override
	public List<Media> findBySeriesId(int seriesId) throws Exception {
		List<Media> mediaList = new ArrayList<Media>();
		String sql = "select * from media where seriesId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, seriesId);
		ResultSet rs = pstmt.executeQuery();
		Media media = null;
		while(rs.next()){
			media = new Media();
			media.setMediaId(rs.getInt("mediaId"));
			media.setMediaName(rs.getString("mediaName"));
			media.setMediaPath(rs.getString("mediaPath"));
			mediaList.add(media);
		}
		JdbcUtils.free(rs, pstmt, conn);
		return mediaList;
	}

	@Override
	public boolean checkVote(int userId,int mediaId) throws Exception {
		boolean mark = true;
		String sql = "select * from vote where mediaId = ? and userId = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, mediaId);
		pstmt.setInt(2, userId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			mark = false;
		}
		JdbcUtils.free(rs, pstmt, conn);
		return mark;
	}

	@Override
	public int vote(int userId, int mediaId) throws Exception {
		conn.setAutoCommit(false);
		int voteCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			//在vote表中插入一条记录
			String sql = "insert into vote(userId,mediaId) values(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, mediaId);
			pstmt.executeUpdate();
			//更新media的voteCount字段
			sql = "update media set voteCount=voteCount+1 where mediaId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mediaId);
			pstmt.executeUpdate();
			//得到更新后的voteCount
			sql = "select voteCount from media where mediaId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mediaId);
			rs = pstmt.executeQuery();
			if(rs.next()){
				voteCount = rs.getInt("voteCount");
			}
			conn.commit();
		}catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			JdbcUtils.free(rs, pstmt, conn);
		}
		return voteCount;
	}
}
