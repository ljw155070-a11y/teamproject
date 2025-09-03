package kr.co.iei.common.model.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias(value="common")
public class Common {
	private String type;
	private int no;
	private String title;
	private Date date;
}
