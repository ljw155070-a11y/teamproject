package kr.co.iei.qna.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.qna.model.dao.QnaDao;
import kr.co.iei.qna.model.vo.Qna;
import kr.co.iei.qna.model.vo.QnaListData;

@Service
public class QnaService {
	@Autowired
	private QnaDao qnaDao;

	public QnaListData selectAllQnaList(int reqPage) {
		int numPerPage = 10;
		
		int endPage = reqPage*numPerPage;
		int startPage = endPage-numPerPage +1;
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("endPage", endPage);
		param.put("startPage", startPage);
		
		int totalCount = qnaDao.selectQnaTotalCount();
		
		int totalPage = (int)Math.ceil(totalCount/(double)numPerPage);
		
		int pageNaviSize = 5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize + 1;
		
		String pageNavi = "<ul class='qna-paging'>";
		if(pageNo != 1) {			
			pageNavi += "<li>";
			pageNavi += "<a class='qnaPage' href='/qna/list?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>chevron-left</span>";
			pageNavi += "</li>";
		}
		
		for(int i=0;i<pageNaviSize;i++) {
			pageNavi += "<li>";
			if(pageNo == reqPage) {
				pageNavi += "<a class='qnaPage active-page' href='/qna/list?reqPage="+pageNo+"'>";
			}else {
				pageNavi += "<a class='qnaPage' href='qna/list?reqPage="+pageNo+"'>";
			}
			pageNavi += pageNo;
			pageNavi += "</a>";
			pageNavi += "</li>";
			
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		if(pageNo > totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='qnaPage' href='qna/list?reqPage="+pageNo+"'>";
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
		return q;
	}

	

	

	

	
}
