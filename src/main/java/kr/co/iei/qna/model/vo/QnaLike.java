package kr.co.iei.qna.model.vo;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="qnaLike")
public class QnaLike {
	private int qnaNo;
	private int memberNo;
}
