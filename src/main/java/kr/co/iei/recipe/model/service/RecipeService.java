package kr.co.iei.recipe.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.recipe.model.dao.RecipeDao;
import kr.co.iei.recipe.model.vo.Recipe;

@Service
public class RecipeService {
	@Autowired
	private RecipeDao recipeDao;

	public HashMap<String,Object> recipeList(int reqPage) {
		//현재 게시글 총 몇개인가?
		int allRecipeCount=recipeDao.allRecipeCount();
		//34개임
		//1페이지 : 1~10
		//2페이지 : 11~20
		//3페이지 : 21~30
		//4페이지 : 31~34
		//rownum 식 : 
		//		시작 넘버 = (reqPage*10)-9
		//		끝 넘버 = reqPage*10
		int listSize=10;
		
		int endNum=reqPage*listSize;
		int startNum=endNum-listSize+1;
		
		List<Recipe> list = recipeDao.recipeList(startNum,endNum);
		
		//띄워줘야 할 페이지 버튼들 계산
		//전체 게시글 개수(allRecipeCount)/페이지당 띄울 게시글 개수(listSize)
		HashMap<String,Object> reqPageSet = new HashMap<>();
		//key : 
		//1) 현재 요청된 페이지 번호 (reqPage)
		//2) 시작 페이지 번호 (reqPage-2), 만약 0이면 x
		//3) 끝 페이지 번호 (reqPage+2)
		
		HashMap<String,Integer> pageInfo = new HashMap<>();
		int startPageNo;
		int endPageNo;
		int totalPageNo = (int)Math.ceil(allRecipeCount/(double)listSize);
		startPageNo = Math.max(1,reqPage - 2);
		//요청된 페이지를 2로 뺐을 때, 1이 더 크면 1으로 반환
		endPageNo = Math.min(totalPageNo, reqPage+2);
		//요청된 페이지를 2로 더했을 때, 전체 페이지 개수가 더 작으면 그걸로 반환
		
		//시작 페이지, 끝 페이지 준비 완료
		
		pageInfo.put("reqPage", reqPage);				//요청된 페이지 번호
		pageInfo.put("startPageNo", startPageNo);		//현재 기준 시작 페이지 번호
		pageInfo.put("endPageNo", endPageNo);			//현재 기준 끝 페이지 번호
		pageInfo.put("allRecipeCount", allRecipeCount);	//전체 레시피 게시글 수
		pageInfo.put("totalPageNo",totalPageNo);		//전체 페이지 번호
		// 이러면 페이지 준비 끝, 하나의 해시맵으로 합치기
		
		reqPageSet.put("pageInfo",pageInfo);
		reqPageSet.put("list", list);
		
		//reqPageSet으로 값 뽑기 연습
		//현재 페이지 번호
		HashMap<String,Integer> test = (HashMap<String,Integer>)reqPageSet.get("pageInfo");
		System.out.println("현재 요청된 페이지 번호 :"+test.get("reqPage"));
		System.out.println("띄워야할 시작 페이지 번호 :"+test.get("startPageNo"));
		System.out.println("띄워야할 마지막 페이지 번호 :"+test.get("endPageNo"));
		System.out.println("전체 레시피 개수 :"+test.get("allRecipeCount"));
		System.out.println("전체 페이지 개수 :"+test.get("totalPageNo"));
		
		//현재 페이지 번호에 해당하는 리스트
		List<Recipe> test2 = (List<Recipe>)reqPageSet.get("list");
		for(Recipe r : test2) {
			System.out.print(r.getRecipeNo()+"\t");
			System.out.print(r.getRecipeTitle()+"\t");
			System.out.println();
		}
		return reqPageSet;
	}
}
