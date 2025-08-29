package kr.co.iei.qna.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.qna.model.dao.QnaDao;
import kr.co.iei.qna.model.vo.Qna;
import kr.co.iei.qna.model.vo.QnaComment;
import kr.co.iei.qna.model.vo.QnaListData;

@Service
public class QnaService {
	@Autowired
	private QnaDao qnaDao;

	public QnaListData selectAllQnaList(int reqPage) {
		int numPerPage = 15; //한 페이지에 보여줄 게시물
		
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
	}

	public Qna selectOneQnaList(int qnaNo) {
		Qna q = qnaDao.selectOneQnaList(qnaNo);
		System.out.println(q);
		if(q != null) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("qnaNo", qnaNo);		
			List<QnaComment> qnaCommentList = qnaDao.selectAllQnaCommentList(param);
			q.setQnaCommentList(qnaCommentList);
		}
		return q;
	}

	public int insertQnaComment(QnaComment qc) {
		System.out.println(qc);
		int result = qnaDao.insertQnaComment(qc);
		return result;
	}

	public int qnaReport(int qnaNo, int memberNo) {
		int count = qnaDao.selectAllQnaReport(qnaNo, memberNo);
		if(count == 0) {
			int result = qnaDao.insertQnaReport(qnaNo, memberNo);			
			return result;
		}else {
			return 0;
		}
	}

	public int deleteQnaComment(int qnaCommentNo) {
		int result = qnaDao.deleteQnaComment(qnaCommentNo);
		return result;
	}
	
}
