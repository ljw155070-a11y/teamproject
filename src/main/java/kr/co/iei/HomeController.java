package kr.co.iei;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.iei.recipe.model.service.RecipeService;
import kr.co.iei.recipe.model.vo.Recipe;

@Controller
public class HomeController {
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping(value="/")
	public String main(Model model) {
		Recipe r = recipeService.recipeGradeRanking();
		model.addAttribute("r",r);
		System.out.println(r);
		return "index";
	}
}
