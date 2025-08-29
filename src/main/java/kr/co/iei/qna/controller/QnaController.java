package kr.co.iei.qna.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.iei.member.model.vo.Member;
import kr.co.iei.qna.model.service.QnaService;
import kr.co.iei.qna.model.vo.Qna;
import kr.co.iei.qna.model.vo.QnaComment;
import kr.co.iei.qna.model.vo.QnaListData;

@Controller
@RequestMapping(value="/qna")
public class QnaController {
	@Autowired
	private QnaService qnaService;
	
	
	@GetMapping(value="/list")
	public String qnaList(int reqPage, Model model) {	
		QnaListData qld = qnaService.selectAllQnaList(reqPage);
		model.addAttribute("list", qld.getList());
		model.addAttribute("pageNavi", qld.getPageNavi());
		return "qna/list";
	}
	
	@GetMapping(value="/view")
	public String qnaView(Model model, int qnaNo, @SessionAttribute(required=false) Member member) {
		
		Qna q = qnaService.selectOneQnaList(qnaNo);
		System.out.println(q);
		model.addAttribute("q", q);
		return "qna/view";
	}
	
	@PostMapping(value="/comment")
	public String insertQnaComment(QnaComment qc) {
		int result = qnaService.insertQnaComment(qc);
		return "redirect:/qna/view?qnaNo="+qc.getQnaNo();
	}
	
	@PostMapping(value="/report")
	@ResponseBody
	public int qnaReport(int qnaNo, int memberNo) {
		int result = qnaService.qnaReport(qnaNo, memberNo);
		return result;
	}
	
	@GetMapping(value="deleteComment")
	public String deleteComment(QnaComment qc) {
		int result = qnaService.deleteQnaComment(qc.getQnaCommentNo());
		return "/";
	}
}
