package kr.co.iei.recipe.model.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value = "recipeCookingOrder")
public class RecipeCookingOrder {
	private int recipeCookingOrder;
	private int recipeNo;
	private String recipeCookingContent;
	private String recipeCookingOrderImgPath;
}
