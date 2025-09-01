package kr.co.iei.notice.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.notice.model.vo.Notice;
import kr.co.iei.notice.model.vo.NoticeFile;

@Mapper
public interface NoticeDao {

	int selectNoticeTotal();

	List selectNoticeList(HashMap<String, Object> param);

	/*List selectAll();*/

	int getNoticeNo();

	int insertNotice(Notice n);

	int insertNoticeFile(NoticeFile noticeFile);

	Notice selectOneNotice(int noticeNo);

	List selectNoticeFile(int noticeNo);

	NoticeFile selectOneNoticeFile(int noticeFileNo);

    int updateNotice(Notice n);

	List selectNoticeFileList(int[] delFileNo);

	int deleteNoticeFile(int noticeFileNo);

	int deleteNotice(int noticeNo);

	int searchTitleCount(String searchTitle);

	List searchTitleNotice(HashMap<String, Object> param);

	


}