package kr.co.iei.qna.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.member.model.vo.Member;
import kr.co.iei.qna.model.vo.Qna;
import kr.co.iei.qna.model.vo.QnaComment;

@Mapper
public interface QnaDao {

	int selectQnaTotalCount();

	List selectAllQna(HashMap<String, Object> param);

	Qna selectOneQnaList(int qnaNo);

	List<QnaComment> selectAllQnaCommentList(HashMap<String, Object> param);

	int insertQnaComment(QnaComment qc);

	int insertQnaReport(int qnaNo, int memberNo);

	int selectAllQnaReport(int qnaNo, int memberNo);

	int deleteQnaComment(int qnaCommentNo);

	int getQnaNo();

	int insertQna(Qna q);

	int deleteQna(int qnaNo);

	int selectAllQnaCommentReport(int qnaNo, int qnaCommentNo, int memberNo);

	List<Qna> qnaReportedList(HashMap<String, Object> qnaReportedListSet);

	int qnaReportedTotalCount();

	List<Qna> qnaCommentReportedList(HashMap<String, Object> qnaCommentReportedListSet);

	int qnaCommentReportedTotalCount();

	

	int reportQnaComment(int qnaNo, int qnaCommentNo, int memberNo);

	int updateQnaComment(QnaComment qc);

	int updateQna(Qna q);


}
