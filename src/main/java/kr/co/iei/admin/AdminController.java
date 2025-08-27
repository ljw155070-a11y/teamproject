package kr.co.iei.admin;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.qna.model.service.QnaService;
import kr.co.iei.recipe.model.service.RecipeService;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private QnaService qnaService;
	
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping(value="/allMember")
	public String allMember(@RequestParam(defaultValue="1") int reqPage, Model model) {
		HashMap<String, Object> reqSet = memberService.selectMemberList(reqPage);
		model.addAttribute("reqSet", reqSet);
		return "admin/allMember";
	}
	@GetMapping(value="/changeLevel")
	public String changeLevel(Member m) {
		int result = memberService.changeLevel(m);
		return "redirect:/admin/allMember";
	}
	@GetMapping(value="/checkedChangeLevel")
	public String checkedChangeLevel(String no, String level) {
		boolean result = memberService.checkedChangeLevel(no,level);
		return "redirect:/admin/allMember";
	}
	@GetMapping(value="/recipeReportedList")
	public String recipeReportedList(@RequestParam(defaultValue="1") int reqPage, Model model) {
		HashMap<String, Object> reqSet = recipeService.recipeReportedList(reqPage);
		model.addAttribute("reqSet", reqSet);
		return "admin/recipeReport";
	}
}
