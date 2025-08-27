package kr.co.iei.recipe.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.co.iei.member.model.vo.Member;
import kr.co.iei.recipe.model.service.RecipeService;
import kr.co.iei.recipe.model.vo.Recipe;
import kr.co.iei.recipe.model.vo.RecipeComment;

@Controller
@RequestMapping(value="/recipe")
public class RecipeController {
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping(value="/list")
	public String recipeList(Model model,int reqPage) {
		//reqPage 의 값에 해당하는 페이지에 리스트를 전달
		HashMap<String,Object> reqPageSet = recipeService.recipeList(reqPage);
		model.addAttribute("reqPageSet",reqPageSet);
		return "recipe/list";
	}
	
	@GetMapping(value="/detail")
	public String recipeDetail(Model model,int reqRecipeNo,@SessionAttribute(required = false) Member member) {
		HashMap<String, Object> recipeDetailSet = recipeService.recipeDetail(reqRecipeNo);
		model.addAttribute("recipeDetailSet",recipeDetailSet);
		System.out.println(recipeDetailSet);
		return "recipe/detail";
	}
	
	@PostMapping(value="/commentInsert")
	public String recipeCommentInsert(RecipeComment rc) {
		int result = recipeService.recipeCommentInsert(rc);
		System.out.println(result);
		return "redirect:/recipe/detail?reqRecipeNo="+rc.getRecipeNo();
	}
	
}
