package kr.co.iei.admin;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.MemberListData;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping(value="/allMember")
	public String allMember(@RequestParam(defaultValue="1") int reqPage, Model model) {
		HashMap<String, Object> reqSet = memberService.selectMemberList(reqPage);
		model.addAttribute("reqSet", reqSet);
		return "admin/allMember";
	}
	
}
