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
		
		
		return null;
	}

	

	

	

	
}
