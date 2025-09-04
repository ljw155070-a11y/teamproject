package kr.co.iei.qna.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.member.model.vo.Member;
import kr.co.iei.qna.model.vo.Qna;
import kr.co.iei.qna.model.vo.QnaComment;
import kr.co.iei.qna.model.vo.QnaListData;

@Mapper
public interface QnaDao {

	int selectQnaTotalCount();

	List selectAllQna(HashMap<String, Object> param);

	Qna selectOneQnaList(int qnaNo);

	List<QnaComment> selectAllQnaCommentList(HashMap<String, Object> param);

	int insertQnaComment(QnaComment qc);

	int insertQnaReport(HashMap<String, Object> qnaReportSet);

	int selectAllQnaReport(HashMap<String, Object> qnaReportSet);

	int deleteQnaComment(int qnaCommentNo);

	int getQnaNo();

	int insertQna(Qna q);

	int deleteQna(int qnaNo);

	int selectAllQnaCommentReport(HashMap<String, Object> reportQnaCommentSet);

	List<Qna> qnaReportedList(HashMap<String, Object> qnaReportedListSet);

	int qnaReportedTotalCount();

	List<Qna> qnaCommentReportedList(HashMap<String, Object> qnaCommentReportedListSet);

	int qnaCommentReportedTotalCount();

	

	int reportQnaComment(HashMap<String, Object> reportQnaCommentSet);

	int updateQnaComment(QnaComment qc);

	int updateQna(Qna q);

	QnaListData selectSearchQnaList(HashMap<String, Object> selectSearchQnaListSet);


}
