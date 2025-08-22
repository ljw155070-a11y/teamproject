package kr.co.iei.qna.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.qna.model.dao.QnaDao;

@Service
public class QnaService {
	@Autowired
	private QnaDao qnaDao;
}
