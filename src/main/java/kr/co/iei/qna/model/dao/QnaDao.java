package kr.co.iei.qna.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.qna.model.vo.Qna;

@Mapper
public interface QnaDao {

	int selectQnaTotalCount();

	List selectAllQna(HashMap<String, Object> param);

	

}
