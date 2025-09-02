package kr.co.iei;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.iei.member.model.vo.Member;
import kr.co.iei.recipe.model.service.RecipeService;
import kr.co.iei.recipe.model.vo.Recipe;

@Controller
public class HomeController {
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping(value="/")
	public String main(Model model) {
		Recipe r = recipeService.recipeGradeRankingr();
		Member m = recipeService.recipeGradeRankingm();
		double avgrate = recipeService.recipeGradeRankingDouble(); 
		model.addAttribute("r",r);
		model.addAttribute("m",m);
		model.addAttribute("avgrate",avgrate);
		System.out.println(r);
		System.out.println(m);
		System.out.println(avgrate);
		return "index";
	}
}
