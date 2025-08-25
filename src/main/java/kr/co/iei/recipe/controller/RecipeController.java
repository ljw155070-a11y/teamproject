package kr.co.iei.recipe.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.iei.recipe.model.service.RecipeService;
import kr.co.iei.recipe.model.vo.Recipe;

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
	public String recipeDetail(Model model,int reqRecipeNo) {
		Recipe recipe = recipeService.recipeDetail(reqRecipeNo);
		model.addAttribute("recipe",recipe);
		System.out.println(recipe);
		return "recipe/detail";
	}
}
