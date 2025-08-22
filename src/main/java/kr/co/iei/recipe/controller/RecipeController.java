package kr.co.iei.recipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.recipe.model.service.RecipeService;

@Controller
@RequestMapping(value="/recipe")
public class RecipeController {
	@Autowired
	private RecipeService recipeService;
}
