package kr.co.iei.qna.model.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="qnaComment")
public class QnaComment {
	private int qnaCommentNo;
	private int memberNo;
	private int qnaNo;
	private String qnaCommentContent;
	private Date qnaCommentDate;
	private Date qnaCommentUpdateDate;
}
