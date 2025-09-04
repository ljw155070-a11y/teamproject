package kr.co.iei.member.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.member.model.vo.JoinUserDate;
import kr.co.iei.member.model.vo.Member;

@Mapper
public interface MemberDao {


	int selectMemberTotalCount();

	List<Member> selectMemberList(HashMap<String, Object> selectMemberListSet);

	Member login(Member m);

	int changeLevel(Member m);

	int insertMember(Member m);

	Member selectOneMember(Member m);

	Member selectOneNickname(Member m);

	Member selectOneEmail(Member m);

	int suspendMember(HashMap<String, Object> suspendMemberSet);

	int searchMemberTotalCount(String memberNickname);

	List<Member> searchMemberList(HashMap<String, Object> searchMemberListSet);

	int updateMember(Member m);

	List<JoinUserDate> monthlyJoinUsers();

	Member findMemberId(Member m);

	Member findIdEmail(Member m);

	int newPw(Member m);

	int deleteMember(int memberNo);

	List selectAllRecipe();

	List headerSearch();

	

	



}
