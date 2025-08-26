package kr.co.iei.recipe.model.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias(value="recipeIngredient")
public class RecipeIngredient {
	private int recipeIngredientNo;
	private int recipeNo;
	private String recipeIngredientName;
	private String recipeIngredientVolume;
}
