package kr.co.iei.qna.model.vo;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="qna")
public class Qna {
	private int qnaNo;
	private int memberNo;
	private String qnaTitle;
	private String qnaContent;
	private Date qnaDate;
	private Date qnaUpdateDate;
	private String memberName;
	private List<QnaComment> qnaCommentList;
	private int rNum;
	private int reportCount;
	private String memberId;
}
