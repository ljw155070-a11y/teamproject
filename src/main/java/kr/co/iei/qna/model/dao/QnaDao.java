package kr.co.iei.qna.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.qna.model.vo.Qna;

@Mapper
public interface QnaDao {

	int selectQnaTotalCount();

	

}
