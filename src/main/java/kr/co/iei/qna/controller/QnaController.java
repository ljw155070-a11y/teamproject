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

@Controller
@RequestMapping(value="/qna")
public class QnaController {
	@Autowired
	private QnaService qnaService;
	
	@GetMapping(value="/qnaList") //qna 페이징
	public String qnaList(int reqPage, Model model) {
		HashMap<String, Object> qnaPageSet = qnaService.selectAllQnaList(reqPage);
		model.addAttribute("qnaPageSet", qnaPageSet);
		return "qna/list";
	}
}
