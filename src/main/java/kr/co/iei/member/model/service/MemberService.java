package kr.co.iei.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.admin.AdminController;
import kr.co.iei.member.model.dao.MemberDao;
import kr.co.iei.member.model.vo.MemberListData;

@Service
public class MemberService {
	
	
	@Autowired
	private MemberDao memberDao;
	
	

	public MemberListData selectMemberList(int reqPage) {
		
		int numPerPage = 10;
		
		
		
		return null;
	}

}
