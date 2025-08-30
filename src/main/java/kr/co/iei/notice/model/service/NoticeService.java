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
	
	public List selectNoticeList() {
		List list = noticeDao.selectNoticeList();
		return list;
	}
	public NoticeListData selectNoticeList(int reqPage) {
		int pageList = 10;
		int end = reqPage * pageList;
		int start = end - pageList + 1;
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("start", start);
		param.put("end", end);
		
		int total = noticeDao.selectNoticeTotal();
		System.out.println("총 게시물 수 : "+total);
		
		int totalPage = (int) (Math.ceil(total / (double) pageList));
		System.out.println("총 페이지 수 : "+totalPage);
		
		int pageNavSize = 5;
		int pageNo = ((reqPage - 1) / pageNavSize) * pageNavSize + 1;
		
		String pageNav = "<ul class = 'pagination circle-style'>";
		if (pageNo != 1) {
			pageNav += "<li>";
			pageNav += "<a class='page-item' href='/notice/list?reqPage=" + (pageNo - 1) + "'>";
			pageNav += "<span class='material-icons arrow' id='arrow'>chevron_left</span>";
			pageNav += "</a>";
			pageNav += "</li>";
		}
		for (int i = 0; i < pageNavSize; i++) {
			pageNav += "<li>";
			if (pageNo == reqPage) {
				pageNav += "<a class='page-item active-page' href='/notice/list?reqPage=" + pageNo + "'>";
			} else {
				pageNav += "<a class='page-item' href='/notice/list?reqPage=" + pageNo + "'>";
			}
			pageNav += pageNo;
			pageNav += "</a>";
			pageNav += "</li>";

			pageNo++;
			if (pageNo > totalPage) {
				break;
			}
		}
		if (pageNo <= totalPage) {
			pageNav += "<li>";
			pageNav += "<a class='page-item' href='/notice/list?reqPage=" + pageNo + "'>";
			pageNav += "<span class='material-icons'>chevron_right</span>";
			pageNav += "</a>";
			pageNav += "</li>";
		}
		pageNav += "</ul>";

		System.out.println(pageNav);
		List list = noticeDao.selectNoticeList(param);
		
		NoticeListData nl = new NoticeListData(list, pageNav);
		return nl;
	}
	public int insertNotice(Notice n, List<NoticeFile> fileList) {
		int newNoticeNo = noticeDao.getNoticeNo();
		n.setNoticeNo(newNoticeNo);
		int result = noticeDao.insertNotice(n);
		for(NoticeFile noticeFile : fileList) {
			noticeFile.setNoticeNo(newNoticeNo);
			result += noticeDao.insertNoticeFile(noticeFile);
		}
		return result;
	
	}
	public Notice selectOneNotice(int noticeNo, int memberNo) {
		Notice n = noticeDao.selectOneNotice(noticeNo);
		List filelist = noticeDao.selectNoticeFile(noticeNo);
		return n;
	}
	public NoticeFile selectOneNoticeFile(int noticeFileNo) {
		// TODO Auto-generated method stub
		return null;
	}
}	

	/*
	public List selectAll() {
		List list = noticeDao.selectAll();
		return list;
	}
}
	*/