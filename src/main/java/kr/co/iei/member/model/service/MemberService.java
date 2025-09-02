package kr.co.iei.member.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.member.model.dao.MemberDao;
import kr.co.iei.member.model.vo.Member;

@Service
public class MemberService {
	
	@Autowired
	private MemberDao memberDao;	

	public HashMap<String, Object> selectMemberList(int reqPage) {
		
		//reqPage = 사용자가 요청한 페이지 번호
		
		//한 페이지에 보여줄 게시물 갯수
		int numPerPage = 10;
		
		
		int end = reqPage * numPerPage;
		int start = end - numPerPage+1;
		
		List<Member> list = memberDao.selectMemberList(start, end);
		
		HashMap<String, Object> reqSet = new HashMap<>();
		
		
		//전체 게시물 수
		int totalCount = memberDao.selectMemberTotalCount();
		
		HashMap<String, Integer> pageInfo = new HashMap<>();
		//전체 페이지 수
		int totalPage = (int)(Math.ceil(totalCount/(double)numPerPage));
		
		//페이지네비 길이
		int pageNaviSize = 5;
		
		//양쪽에 올 네비 갯수
		int bothSidePage = (pageNaviSize-1)/2;
		
		int startNo = Math.max(1, reqPage-bothSidePage);
		
		int endNo = Math.min(totalPage, reqPage+bothSidePage);
		
		if(totalPage <= pageNaviSize) {
			startNo = 1;
			endNo = totalPage;
		}else {
			if((reqPage-bothSidePage)<1) {
				endNo += (1-(reqPage-bothSidePage));
			}else if((reqPage+bothSidePage)>totalPage) {
				startNo -= (reqPage+bothSidePage)-totalPage; 
			}
		}
		pageInfo.put("reqPage", reqPage);
		pageInfo.put("startNo", startNo);
		pageInfo.put("endNo", endNo);
		pageInfo.put("totalCount", totalCount);
		pageInfo.put("totalPage", totalPage);
		
		reqSet.put("pageInfo", pageInfo);
		reqSet.put("list", list);
		
		
		
		
		return reqSet;
	}



	public Member login(Member m) {
		Member member = memberDao.login(m);
		return member;
	}


	@Transactional
	public int changeLevel(Member m) {
		int result = memberDao.changeLevel(m);
		return result;
	}



	@Transactional
	public int insertMember(Member m) {
		int result = memberDao.insertMember(m);
		return result;
	}



	public Member selectOneMember(String checkId) {
		Member m = new Member();
		m.setMemberId(checkId);
		Member member = memberDao.selectOneMember(m);
		return member;
	}



	public Member selectOneNickname(String checkNickname) {
		 Member m = new Member();
		 m.setMemberNickname(checkNickname);
		 Member member = memberDao.selectOneNickname(m);
		return member;
	}



	public Member selectOneEmail(String checkEmail) {
		Member m = new Member();
		m.setMemberEmail(checkEmail);
		Member member = memberDao.selectOneEmail(m);
		return member;
	}


	@Transactional
	public int suspendMember(int memberNo, int suspendDays, String suspendReason) {
		int result = memberDao.suspendMember(memberNo, suspendDays, suspendReason);
		return result;
	}



	public HashMap<String, Object> searchMemberList(int reqPage, String memberNickname) {
		int numPerPage = 10;
		
		
		int end = reqPage * numPerPage;
		int start = end - numPerPage+1;
		
		List<Member> list = memberDao.searchMemberList(start, end, memberNickname);
		
		HashMap<String, Object> reqSet = new HashMap<>();
		
		
		//전체 게시물 수
		int totalCount = memberDao.searchMemberTotalCount(memberNickname);
		
		HashMap<String, Integer> pageInfo = new HashMap<>();
		//전체 페이지 수
		int totalPage = (int)(Math.ceil(totalCount/(double)numPerPage));
		
		//페이지네비 길이
		int pageNaviSize = 5;
		
		//양쪽에 올 네비 갯수
		int bothSidePage = (pageNaviSize-1)/2;
		
		int startNo = Math.max(1, reqPage-bothSidePage);
		
		int endNo = Math.min(totalPage, reqPage+bothSidePage);
		
		if(totalPage <= pageNaviSize) {
			startNo = 1;
			endNo = totalPage;
		}else {
			if((reqPage-bothSidePage)<1) {
				endNo += (1-(reqPage-bothSidePage));
			}else if((reqPage+bothSidePage)>totalPage) {
				startNo -= (reqPage+bothSidePage)-totalPage; 
			}
		}
		pageInfo.put("reqPage", reqPage);
		pageInfo.put("startNo", startNo);
		pageInfo.put("endNo", endNo);
		pageInfo.put("totalCount", totalCount);
		pageInfo.put("totalPage", totalPage);
		
		reqSet.put("pageInfo", pageInfo);
		reqSet.put("list", list);
		
		return reqSet;
		
	}


	@Transactional
	public int updateMember(Member m) {
		int result = memberDao.updateMember(m);
		return result;
	}
//	public Member findMemberId(String memberEmail) {
//		Member m = new Member();
//		m.setMemberEmail(memberEmail);
//		Member member = memberDao.findMemberId(m);
//		return member;
//	} 모달로 하기



	public Member findId(String memberEmail) {
		Member m = new Member();
		m.setMemberEmail(memberEmail);
		Member member = memberDao.findMemberId(m);
		return member;
		
	}

}
