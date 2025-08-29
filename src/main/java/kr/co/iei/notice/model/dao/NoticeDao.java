package kr.co.iei.notice.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.notice.model.vo.Notice;
import kr.co.iei.notice.model.vo.NoticeFile;

@Mapper
public interface NoticeDao {

	List selectNoticeList(int reqPage);

	List selectNoticeList();

	int modifyeNotice(Notice n);

	


}