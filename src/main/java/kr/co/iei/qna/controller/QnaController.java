package kr.co.iei.qna.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.qna.model.service.QnaService;
import kr.co.iei.qna.model.vo.Qna;
import kr.co.iei.qna.model.vo.QnaListData;

@Controller
@RequestMapping(value="/qna")
public class QnaController {
	@Autowired
	private QnaService qnaService;
	
	
	@GetMapping(value="/qnaList")
	public String qnaList(int reqPage, Model model) {
		
		QnaListData qld = qnaService.selectAllQnaList(reqPage);
		model.addAttribute("list", qld.getList());
		model.addAttribute("pageNavi", qld.getPageNavi());
		return "qna/list";
	}
}
