package kr.co.iei.member.controller;
import kr.co.iei.notice.controller.NoticeController;
import kr.co.iei.notice.model.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpSession;
import kr.co.iei.HomeController;
import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.Member;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

    private final HomeController homeController;

    private final NoticeController noticeController;

    private final NoticeService noticeService;
	
	@Autowired
	private MemberService memberService;

    MemberController(NoticeService noticeService, NoticeController noticeController, HomeController homeController) {
        this.noticeService = noticeService;
        this.noticeController = noticeController;
        this.homeController = homeController;
    }
	
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
		} else if(member.getSuspendYN() != 0) {
			model.addAttribute("title","계정 이용이 정지되었습니다.");
			model.addAttribute("text","정지사유 : "+member.getSuspendReason()+'\n'+member.getSuspendDays()+"일 이용이 정지되었습니다.");
//			model.addAttribute("text",member.getSuspendDays()+"일 이용이 정지되었습니다.");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/");
			return "common/msg";
		}
		session.setAttribute("member", member);
		return "redirect:/";	
	}
	
	@GetMapping(value = "/agreeFrm")
	public String agreeFrm() {
		return "member/agree";
	}
	
	
	@GetMapping(value = "/joinFrm")
	public String joinFrm() {
		return "member/joinFrm";
	}
	
	@PostMapping(value = "/join")
	public String join(Member m, Model model) {
		int result = memberService.insertMember(m);
		model.addAttribute("title","회원가입 완료");
		model.addAttribute("text","회원가입이 완료.되었습니다.");
		model.addAttribute("icon","success");
		model.addAttribute("loc","/member/loginFrm");
		
		return "common/msg";
	}
	
	@GetMapping(value = "/checkId")
	@ResponseBody
	public int checkId(String memberId) {
		Member m = memberService.selectOneMember(memberId);
		if(m != null) {
			return 1;
		}
		return 0;
	}
	
	@ResponseBody
	@GetMapping(value = "/checkNickname")
	public int checkNickname(String memberNickname) {
		Member m = memberService.selectOneNickname(memberNickname);
		if(m != null) {
			return 1;
		}
		return 0;
	}
	
	
	@ResponseBody
	@GetMapping(value = "/checkEmail")
	public int checkEmail(String memberEmail) {
		Member m = memberService.selectOneEmail(memberEmail);
		if(m != null) {
			return 1;
		}
		return 0;
	}
	
	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
		
	}
	
	@GetMapping(value = "/mypage")
	public String mypage(@SessionAttribute(required = false) Member member, Model model) {
		if(member == null) {
			model.addAttribute("title","로그인 확인.");
			model.addAttribute("text","로그인 후 이용해 주세요.");
			model.addAttribute("icon","error");
			model.addAttribute("loc","/member/loginFrm");
			return "common/msg";
		}
		return "member/mypage";
	}
	
	@PostMapping(value = "/update")
	public String update(Member m, HttpSession session) {
		int result = memberService.updateMember(m);
		
		if(result > 0) {
			Member member = (Member)session.getAttribute("member");
			member.setMemberNickname(m.getMemberNickname());
		}
		
		return "redirect:/member/mypage";
	}
	@GetMapping(value = "/findId")
	public String findId() {
		return "member/findId";
	}
	@ResponseBody
	@GetMapping(value = "/findIdView")
	public String findIdView(String memberEmail) {
		Member member = memberService.findId(memberEmail);
		return member.getMemberId();
	}
	@GetMapping(value = "/findPw")
	public String findPw() {
		return "member/findPw";
	}
	@ResponseBody
	@GetMapping(value = "/findIdEmail")
	public int findIdEmail(String memberEmail, String memberId) {
		Member member = memberService.findIdEmail(memberEmail,memberId);
		System.out.println(member);
		if(member != null) {
			//결과가 있을 떄
			return 1;
		}
//		/결과가 없을 떄
		return 0;
	}
	
	@GetMapping(value = "/newPw")
	public String newPw() {
		return "member/newPw";
	}
	
	
}
