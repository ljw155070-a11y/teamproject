package kr.co.iei.member.model.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="memberSuspend")
public class MemberSuspend {
	private int suspendNo;
	private int memberNo;
	private Date suspendDate;
	private int suspendDays;
	private String suspendReason;
}
