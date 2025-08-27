package kr.co.iei.recipe.model.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias(value="recipe")
public class Recipe {
	private int recipeNo;					// RECIPE_SEQ
	private String recipeType;				// 한식,중식,일식,양식
	private String recipeTitle;				// 레시피 제목
	private String recipeCaution;			// 레시피 주의사항
	private int recipeCookingTime;			// 레시피 조리 시간(분단위), 기본값 0
	private String recipeLevel;				// 하,중,상
	private int recipeViewCount;			// 조회수, 기본 값 0
	private Date recipeWriteDate;			// 작성일 (sysdate)
	private Date recipeUpdateDate;			// 수정일, 기본값 null
	private int memberNo;					// 작성자 번호 (FK)
	private String recipeThumbnailPath;		// 썸네일 경로
	private String recipeRate;
	private String memberNickname;
	private String memberId;
}
