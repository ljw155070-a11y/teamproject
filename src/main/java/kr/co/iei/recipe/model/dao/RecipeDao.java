package kr.co.iei.recipe.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.recipe.model.vo.Recipe;

@Mapper
public interface RecipeDao {

	List<Recipe> recipeList(int startNum, int endNum);

	int allRecipeCount();

	Recipe recipeDetail(int reqRecipeNo);
	
}
