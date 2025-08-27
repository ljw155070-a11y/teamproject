package kr.co.iei.recipe.model.vo;

import java.sql.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeReport {
	private int recipeNo;
	private int memberNo;
	private Date recipeReportDate;
}

