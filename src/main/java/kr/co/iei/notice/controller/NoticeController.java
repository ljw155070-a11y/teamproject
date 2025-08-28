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
	@GetMapping(value="/writeFrm")
	public String noticeWriteFrm() {
		return "notice/writeFrm";
	}
	@GetMapping(value="/detail")
	public String noticeDetail() {
		return "notice/detail";
	}
	@GetMapping(value="/view")
	public String noticeView(int noticeNo, Member member, Model model) {
		int memberNo = member == null ? 0 : member.getMemberNo();
		
		Notice n = noticeService.selectOneNotice(noticeNo, memberNo);
		if(n == null) {
			model.addAttribute("title", "게시글 조회 실패");
			model.addAttribute("text", "이미 삭제된 게시글 입니다.");
			model.addAttribute("icon", "게시글 조회 실패");
			model.addAttribute("loc", "게시글 조회 실패");
			return "common/msg";
		} else {
			model.addAttribute("n", n);
			return "notice/view";
		}
	}
}
