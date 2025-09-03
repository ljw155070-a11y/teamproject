package kr.co.iei.member.model.vo;


import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="joinUserDate")
public class JoinUserDate {
	private String months;
	private int joinCounts;
}
