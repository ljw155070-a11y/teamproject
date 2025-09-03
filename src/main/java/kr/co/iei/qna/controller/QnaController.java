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
		System.out.println(qnaNo);
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
	
	@PostMapping(value="/deleteComment")
	public String deleteComment(QnaComment qc) {
		int result = qnaService.deleteQnaComment(qc.getQnaCommentNo());
		return "redirect:/qna/view?qnaNo="+qc.getQnaNo();
	}
	
	@PostMapping(value="/writeFrm")
		public String writeQna(){
		return "qna/writeFrm";
	}
	
	@PostMapping(value="/write")
	public String insertQnaContent(Qna q, Model model) {
		int result = qnaService.insertQnaContent(q);
		model.addAttribute("title", "게시글 등록 완료");
		model.addAttribute("text", "질문 등록 완료");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/qna/list?reqPage=1");
		return "common/msg";
	}
	
	@PostMapping(value="/delete")
	@ResponseBody
	public int deleteQna(int qnaNo) {
		int result = qnaService.deleteQna(qnaNo);
		
		return result;
	}
	
	@PostMapping(value="/reportComment")
	@ResponseBody
	public int reportComment(int qnaNo, int qnaCommentNo, int memberNo) {
		int result = qnaService.reportQnaComment(qnaNo, qnaCommentNo, memberNo);
		return result;
	}
	
	@PostMapping(value="/updateComment")
	@ResponseBody
	public int updateComment(QnaComment qc) {
		System.out.println(qc);
		int result = qnaService.updateQnaComment(qc);
		System.out.println(result);
		return result;
	}
	
	
	@GetMapping(value="/updateFrm")
	public Qna updateFrm(int qnaNo, Model model) {
		System.out.println(qnaNo);
		Qna q = qnaService.selectOneQnaList(qnaNo);
		System.out.println(q);
		model.addAttribute("q", q);
		return q;
	}
	@PostMapping(value="/update")
	public String update(Qna q) {
		System.out.println(q);
		int result = qnaService.updateQna(q);
		System.out.println(result);
		return "redirect:/qna/view?qnaNo="+q.getQnaNo();
	}
	
	
}
