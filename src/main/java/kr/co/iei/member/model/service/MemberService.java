package kr.co.iei.member.model.service;

import java.util.HashMap;
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
		
		//reqPage = 사용자가 요청한 페이지 번호
		
		//한 페이지에 보여줄 게시물 갯수
		int numPerPage = 10;
		
		
		int end = reqPage * numPerPage;
		int start = end - numPerPage;
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("end", end);
		param.put("start", start);
		
		//전체 게시물 수
		int totalCount = memberDao.selectMemberTotalCount();
		
		//전체 페이지 수
		int totalPage = (int)(Math.ceil(totalCount/(double)numPerPage));
		
		//페이지네비 길이
		int pageNaviSize = 5;
		
		//페이지네비 시작번호
		int pageNo = reqPage-2;
		
		
		return null;
	}

}
