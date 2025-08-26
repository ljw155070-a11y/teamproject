package kr.co.iei.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.member.model.vo.Member;

@Mapper
public interface MemberDao {


	int selectMemberTotalCount();

	List<Member> selectMemberList(int start, int end);

	Member login(Member m);


}
