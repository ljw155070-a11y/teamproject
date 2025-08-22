package kr.co.iei.recipe.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.recipe.model.dao.RecipeDao;

@Service
public class RecipeService {
	@Autowired
	private RecipeDao recipeDao;
}
