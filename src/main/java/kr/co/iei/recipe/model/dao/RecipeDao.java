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

	List<Recipe> recipeList(HashMap<String, Object> recipeListSet);

	int allRecipeCount();

	Recipe recipeDetail(int reqRecipeNo);

	List<RecipeIngredient> recipeIngredientList(int reqRecipeNo);

	List<RecipeCookingOrder> recipeCookingOrderList(int reqRecipeNo);

	List<RecipeComment> recipeCommentList(int reqRecipeNo);


	List<Member> recipeReportedList(HashMap<String, Object> recipeReportedListSet);

	int recipeReportedTotalCount();

	int recipeCommentInsert(RecipeComment rc);

	int recipeGradeInsert(HashMap<String, Object> recipeGradeInsertSet);

	int recipeGradeSelect(HashMap<String,Object> recipeGradeInsertSet2);

	int recipeGradeUpdate(HashMap<String, Object> recipeGradeInsertSet);

	int recipeReportSelect(HashMap<String, Object> recipeReportSet);

	int recipeReport(HashMap<String, Object> recipeReportSet);

	int recipeDelete(int recipeNo);

	int recipeCommentReport(HashMap<String, Object> recipeCommentReportSet);

	int recipeCommentReportSelect(HashMap<String, Object> recipeCommentReportSet);

	int recipeCommentDelete(int recipeCommentNo);

	int recipeRInsert(Recipe r);

	int recipeNoCreate();

	int recipeRIInsert(RecipeIngredient ri);

	int recipeRCOLInsert(RecipeCookingOrder rco);

	int titleSearchCount(String searchInput);

	int ingredientSearchCount(String searchInput);

	int writerSearchCount(String searchInput);


	List<Recipe> ingredientSearchList(HashMap<String, Object> recipeSearchListSet);

	List<Recipe> writerSearchList(HashMap<String, Object> recipeSearchListSet);

	Recipe editRecipeInfo(int recipeNo);

	ArrayList<RecipeIngredient> editRecipeIngredientInfo(int recipeNo);

	ArrayList<RecipeCookingOrder> editRecipeCookingOrderInfo(int recipeNo);
	int recipeCommentReportedTotalCount();

	List<Member> recipeCommentReportedList(HashMap<String, Object> recipeCommentReportedListSet);

	int deleteIngredient(int recipeNo);


	Recipe recipeGradeRankingr();

	Member recipeGradeRankingm();

	double recipeGradeRankingDouble();


	List<Recipe> dailyRecipe(String recipeType);

	Recipe koTypeOfTopInfoR();

	Member koTypeOfTopInfoM();

	double koTypeOfTopInfoAvgrate();

	Recipe cnTypeOfTopInfoR();

	Member cnTypeOfTopInfoM();

	double cnTypeOfTopInfoAvgrate();

	Recipe jpTypeOfTopInfoR();

	Member jpTypeOfTopInfoM();

	double jpTypeOfTopInfoAvgrate();

	Recipe enTypeOfTopInfoR();

	Member enTypeOfTopInfoM();

	double enTypeOfTopInfoAvgrate();

	int editComment(HashMap<String, Object> editCommentSet);

	int recipeUpdate(HashMap<String, Object> editRecipeSet);

	List<Recipe> titleSearchList(HashMap<String, Object> recipeSearchListSet);
	

	
}
