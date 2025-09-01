package kr.co.iei.member.model.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias(value = "member")
public class Member {
	private int memberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberNickname;
	private String memberEmail;
	private Date memberJoinDate;
	private String memberLevel;
	private int recipeReportCount;
	private int recipeCommentReportCount;
	private int qnaReportCount;
	private int qnaCommentReportCount;
	private int allReportCount;
	private int suspendDays;
	private String suspendReason;
}
