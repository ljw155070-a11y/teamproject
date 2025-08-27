package kr.co.iei.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.notice.model.service.NoticeService;
import kr.co.iei.notice.model.vo.NoticeListData;

@Controller
@RequestMapping(value="/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticesService;
	
	@GetMapping(value="/list")
	public String noticeList(Model model) {
		List list = noticesService.selectNoticeList();
		
//		model.addAttribute("list", ListData.getList());
//		model.addAttribute("pageNav", ListData.getPageNav());
		return "notice/list";
	}
	
	}
