package kr.co.iei.recipe.model.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="recipeComment")
public class RecipeComment {
	private int recipeCommentNo;
	private int memberNo;
	private String recipeCommentContent;
	private Date recipeCommentDate;
	private int recipeNo;
	private Date recipeCommentUpdateDate;
	private String memberNickname;
	private int rNum;
	private int reportCount;
	public String memberId;
}
