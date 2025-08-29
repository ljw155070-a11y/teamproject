package kr.co.iei.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

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
	public String noticeList(int reqPage, Model model) {
		NoticeListData nl = noticeService.selectNoticeList(reqPage);
		model.addAttribute("list", nl.getList());
		model.addAttribute("list", nl.getPageNav());
		return "notice/list";
	}
}
	
	/*
	@GetMapping(value="/list")
	public String noticeList(Model model) {
		List list = noticeService.selectAll();
		model.addAttribute("list", list);
		return "notice/list";
	}
}
	/*
	@GetMapping(value="/list")
	public String noticeList(int reqPage, Model model) {
		NoticeListData nl = noticeService.selectNoticeList(reqPage);
		model.addAttribute("list", nl.getList());
		model.addAttribute("pageNav", nl.getPageNav());
		return "notice/list";
		
	}
}
	/*
	@GetMapping(value="/view")	
	public String noticeModify(Model model) {
		List list = noticeService.selectNoticeList();
		
//		model.addAttribute("pageNav", ListData.getPageNav());
		return "notice/view";
	}	
	
	@GetMapping(value="/writeFrm")	
	public String noticeWriteFrm(Model model) {
		List list = noticeService.selectNoticeList();
		
//		model.addAttribute("pageNav", ListData.getPageNav());
		return "notice/writeFrm";
	}	
	
	@GetMapping(value="/detail")	
	public String noticeDetail(Model model) {
		List list = noticeService.selectNoticeList();
		
//		model.addAttribute("pageNav", ListData.getPageNav());
		return "notice/detail";
	}	
	
}
*/