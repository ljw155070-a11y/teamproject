package kr.co.iei.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.member.model.vo.Member;

@Mapper
public interface MemberDao {


	int selectMemberTotalCount();

	List<Member> selectMemberList(int start, int end);

	Member login(Member m);

	int changeLevel(Member m);

	int insertMember(Member m);

	Member selectOneMember(Member m);

	Member selectOneNickname(Member m);

	Member selectOneEmail(Member m);

	int suspendMember(int memberNo, int suspendDays, String suspendReason);

	int searchMemberTotalCount(String memberNickname);

	List<Member> searchMemberList(int start, int end, String memberNickname);

	int updateMember(Member m);

	Member findMemberId(Member m);



}
