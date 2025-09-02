package kr.co.iei.recipe.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
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

	int recipeGradeInsert(int recipeNo, int memberNo, int recipeRate);

	int recipeGradeSelect(int recipeNo, int memberNo);

	int recipeGradeUpdate(int recipeNo, int memberNo, int recipeRate);

	int recipeReportSelect(int recipeNo, int memberNo);

	int recipeReport(int recipeNo, int memberNo);

	int recipeDelete(int recipeNo);

	int recipeCommentReport(int recipeCommentNo, int memberNo);

	int recipeCommentReportSelect(int recipeCommentNo, int memberNo);

	int recipeCommentDelete(int recipeCommentNo);

	int recipeRInsert(Recipe r);

	int recipeNoCreate();

	int recipeRIInsert(RecipeIngredient ri);

	int recipeRCOLInsert(RecipeCookingOrder rco);

	HashMap<String, Object> recipeTitleSearchList(int reqPage, String searchInput);

	int titleSearchCount(String searchInput);

	int ingredientSearchCount(String searchInput);

	int writerSearchCount(String searchInput);

	List<Recipe> titleSearchList(int startNum, int endNum, String searchInput);

	List<Recipe> ingredientSearchList(int startNum, int endNum, String searchInput);

	List<Recipe> writerSearchList(int startNum, int endNum, String searchInput);

	Recipe editRecipeInfo(int recipeNo);

	ArrayList<RecipeIngredient> editRecipeIngredientInfo(int recipeNo);

	ArrayList<RecipeCookingOrder> editRecipeCookingOrderInfo(int recipeNo);
	int recipeCommentReportedTotalCount();

	List<Member> recipeCommentReportedList(int start, int end);

	int deleteIngredient(int recipeNo);

	int recipeUpdate(int recipeNo, String recipeTitle, String recipeCaution);

	int editComment(int recipeCommentNo, int recipeNo, String editText);

	Recipe recipeGradeRanking();
	

	
}
