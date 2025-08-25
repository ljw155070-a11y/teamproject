package kr.co.iei.qna.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.qna.model.dao.QnaDao;
import kr.co.iei.qna.model.vo.Qna;

@Service
public class QnaService {
	@Autowired
	private QnaDao qnaDao;

	public HashMap<String, Object> selectAllQnaList(int reqPage) {
		int qnaPageSize = 10;
		
		int endPage = reqPage*qnaPageSize;
		int startPage = endPage-qnaPageSize+1;
		
		List<Qna> qnaList = qnaDao.selectAllQnaList(endPage, startPage);
		
		HashMap<String, Object> qnaPageSet = new HashMap<String, Object>();
		qnaPageSet.put("endPage", endPage);
		qnaPageSet.put("startPage", startPage);
		return qnaPageSet;
	}

	

	

	
}
