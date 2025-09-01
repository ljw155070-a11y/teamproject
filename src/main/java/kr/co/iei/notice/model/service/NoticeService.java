package kr.co.iei.notice.model.service;

import java.util.ArrayList;
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
	@Transactional
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
	/*
	public Notice selectOneNotice(int noticeNo, int memberNo) {
		Notice n = noticeDao.selectOneNotice(noticeNo);
		List filelist = noticeDao.selectNoticeFile(noticeNo);
		return n;
	}
	*/
	public Notice selectOnetNotice(int noticeNo) {
		Notice notice = noticeDao.selectOneNotice(noticeNo);
		return notice;
	}
	public NoticeFile selectOneNoticeFile(int noticeFileNo) {
		NoticeFile noticeFile = noticeDao.selectOneNoticeFile(noticeFileNo);
		return noticeFile;
	}
	@Transactional
	public int updateNotice(Notice notice) {
		int result = noticeDao.updateNotice(notice);
		return result;
	}
	
	/*
	public Notice selectOneNotice(int noticeNo, int memberNo) {
		Notice n = noticeDao.selectOneNotice(noticeNo);
		List<NoticeFile> filelist = noticeDao.selectNoticeFile(noticeNo);
		n.setFileList(filelist);
		return n;
	} 
	public NoticeFile selectOneNoticeFile(int noticeFileNo) {
		NoticeFile noticeFile = noticeDao.selectOneNoticeFile(noticeFileNo);
		return noticeFile;
	}
	@Transactional
	public int updateNotice(Notice notice) {
		int result = noticeDao.updateNotice(notice);
		return result;
	}
	
	@Transactional
	public List<NoticeFile> updateNotice(Notice n, List<NoticeFile> fileList, int[] delFileNo) {
	    int result = noticeDao.updateNotice(n);
	    for (NoticeFile noticeFile : fileList) {
	        noticeFile.setNoticeNo(n.getNoticeNo());
	        result += noticeDao.insertNoticeFile(noticeFile);
	    }
	    List<NoticeFile> delFileList = new ArrayList<>();
	    if (delFileNo != null) {
	        List<NoticeFile> list = noticeDao.selectNoticeFileList(delFileNo);
	        delFileList.addAll(list); // 실제 삭제될 파일들을 담기
	        for (int noticeFileNo : delFileNo) {
	            result += noticeDao.deleteNoticeFile(noticeFileNo);
	        }
	    }
	    return delFileList;
	}

	@Transactional
	public List<NoticeFile> updateNotice(Notice n, List<NoticeFile> fileList, int[] delFileNo) {
	    int result = noticeDao.updateNotice(n);
	    for (NoticeFile noticeFile : fileList) {
	        noticeFile.setNoticeNo(n.getNoticeNo());
	        result += noticeDao.insertNoticeFile(noticeFile);
	    }
	    List<NoticeFile> delFileList = new ArrayList<>();
	    if (delFileNo != null) {
	        List list = noticeDao.selectNoticeFileList(delFileNo);
	        for (int noticeFileNo : delFileNo) {
	            result += noticeDao.deleteNoticeFile(noticeFileNo);
	        }
	    }
	    return delFileList;
	}
	*/
	/*
	@Transactional
	public List<NoticeFile> deleteNotice(int noticeNo) {
		List delFileList = noticeDao.selectNoticeFile(noticeNo);
		int result = noticeDao.deleteNotice(noticeNo);
		return delFileList;
	}
	*/
	
	@Transactional
	public int deleteNotice(int noticeNo) {
		int result = noticeDao.deleteNotice(noticeNo);
		return result;
	}
	
	public NoticeListData searchTitle(int reqPage, String searchTitle) {
		int pageList = 10;
		
		int end = reqPage * pageList;
		int start = end-pageList+1;
		HashMap<String, Object> param = new HashMap<String,Object>();
		param.put("start", start);
		param.put("end", end);
		
		int total = noticeDao.searchTitleCount(searchTitle);
		if(total > 0) {
			int totalPage = (int)(Math.ceil(total/(double)pageList));
			
			int pageNavSize = 5;
			
			int pageNo = ((reqPage-1)/pageNavSize)*pageNavSize+1;
			
			String pageNav = "<ul class='pagination circle-style'>";
			if(pageNo != 1) {
				pageNav += "<li>";
				pageNav += "<a class='page-item' href='/notice/searchTitle?searchTitle="+searchTitle+"&reqPage="+(pageNo-1)+"'>";
				pageNav += "<span class='material-icons'>chevron_left</span>";
				pageNav += "</a>";
				pageNav += "</li>";
			}
			for(int i=0;i<pageNavSize;i++) {
				pageNav += "<li>";
				if(pageNo == reqPage) {
					pageNav += "<a class='page-item active-page' href='/notice/searchTitle?searchTitle="+searchTitle+"&reqPage="+pageNo+"'>";
				}else {
					pageNav += "<a class='page-item' href='/notice/searchTitle?searchTitle="+searchTitle+"&reqPage="+pageNo+"'>";
				}
				pageNav += pageNo;
				pageNav += "</a>";
				pageNav += "</li>";
				
				pageNo++;
				if(pageNo > totalPage) {
					break;
				}
			}
			if(pageNo <= totalPage) {
				pageNav += "<li>";
				pageNav += "<a class='page-item' href='/admission/list?reqPage="+pageNo+"'>";
				pageNav += "<span class='material-icons'>chevron_right</span>";
				pageNav += "</a>";
				pageNav += "</li>";
			}
			pageNav += "</ul>";                    
			
			param.put("searchTitle", searchTitle);
			List list = noticeDao.searchTitleNotice(param);
			
			
			NoticeListData nld = new NoticeListData(list, pageNav);
			
			return nld;
		}else {
			return null;
		}
	}
}
		

