package kr.co.iei.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.JoinUserDate;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.qna.model.service.QnaService;
import kr.co.iei.qna.model.vo.QnaComment;
import kr.co.iei.recipe.model.service.RecipeService;
import kr.co.iei.recipe.model.vo.Recipe;

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
		model.addAttribute("reqPage", reqPage);
		return "admin/allMember";
	}
	@GetMapping(value="/changeLevel")
	public String changeLevel(Member m, int reqPage) {
		int result = memberService.changeLevel(m);
		return "redirect:/admin/allMember?reqPage="+reqPage;
	}
	@GetMapping(value="/recipeReport")
	public String recipeReportedList(@RequestParam(defaultValue="1") int reqPage, Model model) {
		HashMap<String, Object> reqSet = recipeService.recipeReportedList(reqPage);
		model.addAttribute("reqSet", reqSet);
		return "admin/recipeReport";
	}
	@GetMapping(value="/recipeCommentReport")
	public String recipeCommentReportedList(@RequestParam(defaultValue = "1")int reqPage, Model model) {
		HashMap<String, Object> reqSet = recipeService.recipeCommentReportedList(reqPage);
		model.addAttribute("reqSet", reqSet);
		return "admin/recipeCommentReport";
	}
	@PostMapping(value="/suspendMember")
	public String suspendMember(int memberNo, int suspendDays, String suspendReason) {
		int result = memberService.suspendMember(memberNo, suspendDays, suspendReason);
		return "redirect:/admin/allMember";
	}
	@GetMapping(value="/searchMember")
	public String searchMember(@RequestParam(defaultValue="1") int reqPage, String memberNickname, Model model) {
		HashMap<String, Object> reqSet = memberService.searchMemberList(reqPage, memberNickname);
		model.addAttribute("reqSet", reqSet);
		model.addAttribute("reqPage", reqPage);
		model.addAttribute("memberNickname", memberNickname);
		return "admin/allMember";
	}
	@GetMapping(value="/qnaReport")
	public String qnaReportedList(@RequestParam(defaultValue = "1")int reqPage, Model model) {
		HashMap<String, Object> reqSet = qnaService.qnaReportedList(reqPage);
		model.addAttribute("reqSet", reqSet);
		return "admin/qnaReport";
	}
	@GetMapping(value="/qnaCommentReport")
	public String qnaCommentReportedList(@RequestParam(defaultValue = "1")int reqPage, Model model) {
		HashMap<String, Object> reqSet = qnaService.qnaCommentReportedList(reqPage);
		model.addAttribute("reqSet", reqSet);
		return "admin/qnaCommentReport";
	}
	@ResponseBody
	@PostMapping(value="/recipeDelete")
	public String recipeDelete(int recipeNo) {
		int result = recipeService.recipeDelete(recipeNo);
		if(result>0) {
			return "success";
		}else {
			return "fail";
		}
	}
	@ResponseBody
	@PostMapping(value="/deleteComment")
	public HashMap<String, Object> recipeDeleteComment(int recipeNo, int recipeCommentNo) {
		HashMap<String, Object> recipeDeleteCommentResult = recipeService.recipeCommentDelete(recipeCommentNo, recipeNo);
		return recipeDeleteCommentResult;
	}
	@ResponseBody
	@PostMapping(value="/qnaDelete")
	public String qnaDelete(int qnaNo) {
		int result = qnaService.deleteQna(qnaNo);
		if(result>0) {
			return "success";			
		}else {
			return "fail";
		}
	}
	@ResponseBody
	@PostMapping(value="/qnaCommentDelete")
	public String qnaCommentDelete(QnaComment qc) {
		int result = qnaService.deleteQnaComment(qc.getQnaCommentNo());
		if(result>0) {
			return "success";
		}else {
			return "fail";
		}
	}
	@GetMapping(value="/checkedDeleteRecipe")
	public String checkedDeleteRecipe(String no) {
		boolean result = recipeService.checkedDeleteRecipe(no);
		return "redirect:/admin/recipeReport";
	}
	@GetMapping(value="/webStatistics")
	public String webStatistics(Model model) {
		List<JoinUserDate> lists = memberService.monthlyJoinUsers();
		List<String> months = new ArrayList<>();
		List<Integer> counts = new ArrayList<>();
		for(JoinUserDate list : lists) {
			months.add(list.getMonths());
			counts.add(list.getJoinCounts());
		}
		model.addAttribute("months", months);
		model.addAttribute("counts", counts);
		
		
		return "admin/webStatistics";
	}
	@ResponseBody
	@GetMapping(value="/dailyRecipe")
	public Map<String, Object> dailyRecipe(String recipeType) {
		List<Recipe> recipeLists = recipeService.dailyRecipe(recipeType);
		List<String> dd = new ArrayList<>();
		List<Integer> counts = new ArrayList<>();
		for(Recipe recipe : recipeLists) {
			dd.add(recipe.getDd());
			counts.add(recipe.getRecipeCount());
		}
		Map<String, Object> param = new HashMap<>();
		param.put("dd", dd);
		param.put("counts", counts);
		
		return param;
		
	}
}
