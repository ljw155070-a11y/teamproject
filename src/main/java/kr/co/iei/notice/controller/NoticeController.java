package kr.co.iei.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.member.model.vo.Member;
import kr.co.iei.notice.model.service.NoticeService;
import kr.co.iei.notice.model.vo.Notice;
import kr.co.iei.notice.model.vo.NoticeListData;

@Controller
@RequestMapping(value="/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping(value="/list")
	public String noticeList(Model model) {
		List list = noticeService.selectNoticeList();
		
//		model.addAttribute("pageNav", ListData.getPageNav());
		return "notice/list";
	}
	
	@GetMapping(value="/modify")	//수정하기
	public String noticeModify(Model model) {
		List list = noticeService.selectNoticeList();
		
//		model.addAttribute("pageNav", ListData.getPageNav());
		return "notice/modify";
	}	
	
	@GetMapping(value="/writeFrm")	//입력하기
	public String noticeWriteFrm(Model model) {
		List list = noticeService.selectNoticeList();
		
//		model.addAttribute("pageNav", ListData.getPageNav());
		return "notice/writeFrm";
	}	
	
	@GetMapping(value="/detail")	//입력하기
	public String noticeDetail(Model model) {
		List list = noticeService.selectNoticeList();
		
//		model.addAttribute("pageNav", ListData.getPageNav());
		return "notice/detail";
	}	
	
}
