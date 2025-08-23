package kr.co.iei.recipe.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.recipe.model.dao.RecipeDao;
import kr.co.iei.recipe.model.vo.Recipe;

@Service
public class RecipeService {
	@Autowired
	private RecipeDao recipeDao;

	public HashMap<String, Object> recipeList(int reqPage) {
		//현재 게시글 총 몇개인가?
		int allRecipeCount=recipeDao.allRecipeCount();
		//34개임
		//1페이지 : 1~10
		//2페이지 : 11~20
		//3페이지 : 21~30
		//4페이지 : 31~34
		//rownum 식 : 
		//		시작 넘버 = (reqPage*10)-9
		//		끝 넘버 = reqPage*10
		System.out.println(allRecipeCount);
		List<Recipe> list = recipeDao.recipeList(reqPage);
		for(Recipe r : list) {
			System.out.println("글번호"+r.getRecipeNo());
		}
		return null;
	}
}
