package kr.co.iei.recipe.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.member.model.vo.Member;
import kr.co.iei.recipe.model.vo.Recipe;
import kr.co.iei.recipe.model.vo.RecipeComment;
import kr.co.iei.recipe.model.vo.RecipeCookingOrder;
import kr.co.iei.recipe.model.vo.RecipeIngredient;

@Mapper
public interface RecipeDao {

	List<Recipe> recipeList(int startNum, int endNum);

	int allRecipeCount();

	Recipe recipeDetail(int reqRecipeNo);

	List<RecipeIngredient> recipeIngredientList(int reqRecipeNo);

	List<RecipeCookingOrder> recipeCookingOrderList(int reqRecipeNo);

	List<RecipeComment> recipeCommentList(int reqRecipeNo);


	List<Member> recipeReportedList(int start, int end);

	int recipeReportedTotalCount();

	int recipeCommentInsert(RecipeComment rc);

	
}
