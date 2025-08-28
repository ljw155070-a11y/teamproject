package kr.co.iei.notice.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.notice.model.dao.NoticeDao;
import kr.co.iei.notice.model.vo.Notice;
import kr.co.iei.notice.model.vo.NoticeFile;
import kr.co.iei.notice.model.vo.NoticeListData;

@Service
public class NoticeService {
	@Autowired
	private NoticeDao noticeDao;
	
//	public NoticeListData selectNoticeList(int reqPage) {
//		List list = noticeDao.selectNoticeList(reqPage);
//		return list;
//	}

	public List selectNoticeList() {
		List list = noticeDao.selectNoticeList();
		return list;
	}
	public Notice selectOneNotice(int noticeNo, int memberNo) {
		Notice n = noticeDao.selectOneNotice(noticeNo);
		if(n != null) {
			List fileList = noticeDao.selectNoticeFile(noticeNo);		
			//n.setFileList(filelist);
		}
		return n;
		
		
	}
}