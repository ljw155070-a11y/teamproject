package kr.co.iei.qna.model.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="qnaReport")
public class QnaReport {
	private int memberNo;
	private int qnaCommentNo;
	private int qnaNo;
	private Date qnaReportDate;
	private Date qnaCommentReportDate;
}
