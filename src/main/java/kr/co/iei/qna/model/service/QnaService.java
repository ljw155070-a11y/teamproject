package kr.co.iei.qna.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.member.model.vo.Member;
import kr.co.iei.qna.model.dao.QnaDao;
import kr.co.iei.qna.model.vo.Qna;
import kr.co.iei.qna.model.vo.QnaComment;
import kr.co.iei.qna.model.vo.QnaListData;

@Service
public class QnaService {
	@Autowired
	private QnaDao qnaDao;

	public QnaListData selectAllQnaList(int reqPage) {
		int numPerPage = 20; //한 페이지에 보여줄 게시물
		
		int endPage = reqPage * numPerPage;
		int startPage = endPage - numPerPage + 1;
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("startPage", startPage);
		param.put("endPage", endPage);
		
		int totalCount = qnaDao.selectQnaTotalCount();
		
		int totalPage = (int)Math.ceil(totalCount/(double)numPerPage);
		
		int pageNaviSize = 5; //페이지 길이
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
		String pageNavi = "<ul class='qna-paging'>";
		if(pageNo != 1) {			
			pageNavi += "<li>";
			pageNavi += "<a class='qnaPage' href='/qna/list?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class=\"material-symbols-outlined\">keyboard_double_arrow_left</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='qnaPage active-page' href='/qna/list?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='qnaPage' href='/qna/list?reqPage="+pageNo+"'>";
			}
			pageNavi += pageNo;
			pageNavi += "</a>";
			pageNavi += "</li>";
			
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='qnaPage' href='/qna/list?reqPage="+pageNo+"'>";
			pageNavi += "<span class=\"material-symbols-outlined\">keyboard_double_arrow_right</span>";
			pageNavi += "</a>";
			pageNavi +="</li>";
		}
		
		pageNavi += "</ul>";
		
		List list = qnaDao.selectAllQna(param);
		QnaListData qld = new QnaListData(list, pageNavi);
		return qld;
	}//질문 목록창 및 페이징

	public Qna selectOneQnaList(int qnaNo) {
		Qna q = qnaDao.selectOneQnaList(qnaNo);
		if(q != null) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("qnaNo", qnaNo);		
			List<QnaComment> qnaCommentList = qnaDao.selectAllQnaCommentList(param);
			q.setQnaCommentList(qnaCommentList);
		}
		return q;
	}//상세보기

	@Transactional
	public int insertQnaComment(QnaComment qc) {
		System.out.println(qc);
		int result = qnaDao.insertQnaComment(qc);
		return result;
	}//댓글 작성

	@Transactional
	public int qnaReport(int qnaNo, int memberNo) {
		HashMap<String, Object> qnaReportSet = new HashMap<>();
		qnaReportSet.put("qnaNo", qnaNo);
		qnaReportSet.put("memberNo", memberNo);
		int count = qnaDao.selectAllQnaReport(qnaReportSet);
		if(count == 0) {
			int result = qnaDao.insertQnaReport(qnaReportSet);			
			return result;
		}else {
			return 0;
		}
	}//게시글 신고
	
	@Transactional
	public int deleteQnaComment(int qnaCommentNo) {
		System.out.println("댓글삭제 호출 : "+qnaCommentNo);
		int result = qnaDao.deleteQnaComment(qnaCommentNo);
		System.out.println(result);
		return result;
	}//댓글 삭제

	@Transactional
	public int insertQnaContent(Qna q) {	
		int newQnaNo = qnaDao.getQnaNo();
		int result = qnaDao.insertQna(q);
		System.out.println(result);
		return result;
	}//게시글 작성

	@Transactional
	public int deleteQna(int qnaNo) {
		int result = qnaDao.deleteQna(qnaNo);
		return result;
	}//게시글 삭제
	
	public HashMap<String, Object> qnaReportedList(int reqPage) {

		int numPerPage = 10;
		
		
		int end = reqPage * numPerPage;
		int start = end - numPerPage+1;
		
		HashMap<String, Object> qnaReportedListSet = new HashMap<>();
		qnaReportedListSet.put("start", start);
		qnaReportedListSet.put("end", end);
		List<Qna> list = qnaDao.qnaReportedList(qnaReportedListSet);
		
		HashMap<String, Object> reqSet = new HashMap<>();
		
		
		//전체 게시물 수
		int totalCount = qnaDao.qnaReportedTotalCount();
		
		HashMap<String, Integer> pageInfo = new HashMap<>();
		//전체 페이지 수
		int totalPage = (int)(Math.ceil(totalCount/(double)numPerPage));
		
		//페이지네비 길이
		int pageNaviSize = 5;
		
		//양쪽에 올 네비 갯수
		int bothSidePage = (pageNaviSize-1)/2;
		
		int startNo = Math.max(1, reqPage-bothSidePage);
		
		int endNo = Math.min(totalPage, reqPage+bothSidePage);
		
		if(totalPage <= pageNaviSize) {
			startNo = 1;
			endNo = totalPage;
		}else {
			if((reqPage-bothSidePage)<1) {
				endNo += (1-(reqPage-bothSidePage));
			}else if((reqPage+bothSidePage)>totalPage) {
				startNo -= (reqPage+bothSidePage)-totalPage; 
			}
		}
		pageInfo.put("reqPage", reqPage);
		pageInfo.put("startNo", startNo);
		pageInfo.put("endNo", endNo);
		pageInfo.put("totalCount", totalCount);
		pageInfo.put("totalPage", totalPage);
		
		reqSet.put("pageInfo", pageInfo);
		reqSet.put("list", list);
		
		
		
		
		return reqSet;
	}

	public HashMap<String, Object> qnaCommentReportedList(int reqPage) {

		int numPerPage = 10;
		
		
		int end = reqPage * numPerPage;
		int start = end - numPerPage+1;
		


		HashMap<String, Object> qnaCommentReportedListSet = new HashMap<>();
		qnaCommentReportedListSet.put("start", start);
		qnaCommentReportedListSet.put("end", end);
		List<Qna> list = qnaDao.qnaCommentReportedList(qnaCommentReportedListSet);
		

		HashMap<String, Object> reqSet = new HashMap<>();
		
		
		//전체 게시물 수
		int totalCount = qnaDao.qnaCommentReportedTotalCount();
		
		HashMap<String, Integer> pageInfo = new HashMap<>();
		//전체 페이지 수
		int totalPage = (int)(Math.ceil(totalCount/(double)numPerPage));
		
		//페이지네비 길이
		int pageNaviSize = 5;
		
		//양쪽에 올 네비 갯수
		int bothSidePage = (pageNaviSize-1)/2;
		
		int startNo = Math.max(1, reqPage-bothSidePage);
		
		int endNo = Math.min(totalPage, reqPage+bothSidePage);
		
		if(totalPage <= pageNaviSize) {
			startNo = 1;
			endNo = totalPage;
		}else {
			if((reqPage-bothSidePage)<1) {
				endNo += (1-(reqPage-bothSidePage));
			}else if((reqPage+bothSidePage)>totalPage) {
				startNo -= (reqPage+bothSidePage)-totalPage; 
			}
		}
		pageInfo.put("reqPage", reqPage);
		pageInfo.put("startNo", startNo);
		pageInfo.put("endNo", endNo);
		pageInfo.put("totalCount", totalCount);
		pageInfo.put("totalPage", totalPage);
		
		reqSet.put("pageInfo", pageInfo);
		reqSet.put("list", list);
		
		
		
		
		return reqSet;
	}
	
	@Transactional
	public int reportQnaComment(int qnaNo, int qnaCommentNo, int memberNo) {
		HashMap<String, Object> reportQnaCommentSet = new HashMap<>();
		reportQnaCommentSet.put("qnaNo", qnaNo);
		reportQnaCommentSet.put("qnaCommentNo", qnaCommentNo);
		reportQnaCommentSet.put("memberNo", memberNo);
		int count = qnaDao.selectAllQnaCommentReport(reportQnaCommentSet);
		if(count == 1) {
			return 0;
		}else {			
			int result = qnaDao.reportQnaComment(reportQnaCommentSet);
			return result;
		}
	}//댓글 신고

	@Transactional
	public int updateQnaComment(QnaComment qc) {
		int result = qnaDao.updateQnaComment(qc);
		System.out.println(result);
		return result;
	}

	@Transactional
	public int updateQna(Qna q) {
		int result = qnaDao.updateQna(q);
		System.out.println(q);
		return result;
	}

	public HashMap<Object, String> selectSearchQnaList(int reqPage, String menu, String searchContent) {
		HashMap<Object, String> qList = qnaDao.selectSearchQnaList(reqPage, menu, searchContent);
		return qList;
	}
}


