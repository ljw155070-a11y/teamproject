package kr.co.iei.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.Member;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping(value = "/loginFrm")
	public String loginFrm() {
		return "member/login";
	}
	
	@PostMapping(value = "/login")
	public String login(Member m, Model model, HttpSession session) {
		System.out.println(m);
		Member member = memberService.login(m);
		System.out.println(member);
		if(member == null) {
			model.addAttribute("title","로그인 실패");
			model.addAttribute("text","아이디 또는 패스워드를 확인하세요.");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/member/loginFrm");
			return "common/msg";
		}else {
			session.setAttribute("member", member);
			return "redirect:/";
		}
	}
	
	@GetMapping(value = "/agreeFrm")
	public String agreeFrm() {
		return "member/agree";
	}
	
	
	@GetMapping(value = "/joinFrm")
	public String joinFrm() {
		return "member/joinFrm";
	}
	
	
}
