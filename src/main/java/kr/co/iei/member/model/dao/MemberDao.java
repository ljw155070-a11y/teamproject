package kr.co.iei.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {

	List selectAllMember();

	int selectMemberTotalCount();

}
