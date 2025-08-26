package kr.co.iei.recipe.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.recipe.model.dao.RecipeDao;
import kr.co.iei.recipe.model.vo.Recipe;
import kr.co.iei.recipe.model.vo.RecipeComment;
import kr.co.iei.recipe.model.vo.RecipeCookingOrder;
import kr.co.iei.recipe.model.vo.RecipeIngredient;

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
		int pageCounts = 5;	//한 페이지에 선택 가능한 페이지를 몇개 출력할지
		int pageWindowDiv=(pageCounts-1)/2;	//현재 기준 양옆에 몇개가 와야할지
		startPageNo = Math.max(1,reqPage - pageWindowDiv);
		//요청된 페이지를 2로 뺐을 때, 1이 더 크면 1으로 반환
		//startPageNo 가 1이면 reqPage-2
		endPageNo = Math.min(totalPageNo, reqPage+pageWindowDiv);
		//요청된 페이지를 2로 더했을 때, 전체 페이지 개수가 더 작으면 그걸로 반환
		//이 때, 보정을 해줘야될게 페이지는 무조건 5개 출력

		/*
		 * ★ 쉬운 버전으로 생각하기
		 * (endPageNo-startPageNo+1) < pageCounts 이면 
		 * reqPage 기준 -2 +2 한 것 중 어디가 보정되었는 지 확인하고, 
		 * 보정된 값만큼 int a로 저장 보정되지 않은 쪽에 a 값만큼 연산, 
		 * 이 때 연산값이 totalPageNo 보다 같거나 크고, 
		 * 1보다 작거나 같으면 보정하지 않음
		 * int shortCount = pageCounts - (endPageNo-startPageNo+1)
		 * 최대 페이지 : 5
		 * 
		 * 	요청 페이지 : 2
		 * 	출력 페이지 : 1 [2] 3 4 5 (뒤에 +1)
		 * 
		 *	요청 페이지 : 1
		 * 	출력 페이지 : [1] 2 3 4 5 (뒤에 +2)
		 * 
		 * 	요청 페이지 : 4
		 * 	출력 페이지 : 1 2 3 [4] 5 (앞에 -1)
		 * 
		 * 	요청 페이지 : 5
		 * 	출력 페이지 : 1 2 3 4 [5] (앞에 -2)
		 * 
		 * 최대 페이지 : 8
		 * 	요청 페이지 2
		 * 	출력 페이지 1 [2] 3 4 5 (뒤에 +1)
		 * 
		 * 	요청 페이지 1
		 * 	출력 페이지 [1] 2 3 4 5 (뒤에 +2)
		 * 
		 * 	요청 페이지 7
		 * 	출력 페이지 4 5 6 [7] 8 (앞에 -1) 
		 * 
		 * 	요청 페이지 8
		 * 	출력 페이지 4 5 6 7 [8] (앞에 -2)  
		 */
		if(totalPageNo<=pageCounts) {
			//전체 페이지 개수가 지정한 페이지 개수보다 작으면 일단 전부 출력
			startPageNo = 1;
			endPageNo = totalPageNo;
		}else {
			//
			if((reqPage - pageWindowDiv)<1) {
				endPageNo += (1-(reqPage - pageWindowDiv));
			}else if((reqPage + pageWindowDiv)>totalPageNo) {
				startPageNo -= (reqPage + pageWindowDiv)-totalPageNo;
			}
		}
		
		/*
		 * ★★ 생각하다 포기한거
		if ((endPageNo-startPageNo+1) < pageCounts) {
			//띄워주는 페이지 개수가 5개보다 작으면
			if(startPageNo == 1) {
				//시작 페이지가 1인건지 확인하고, 시작 쪽에서 빠진거면 
				//빠진 만큼 뒤쪽에 추가
				endPageNo+=Math.abs(reqPage-2);
			}else if(endPageNo == totalPageNo) {
				//끝 페이지가 1인건지 확인하고, 끝 쪽에서 빠진거면
				//빠진만큼 앞 쪽에 추가
				startPageNo+=Math.abs(endPageNo-(reqPage+2));
			}
		}
		* ★★ 생각하다 포기한거
		*/
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
			System.out.print(r.getMemberNickname()+"\t");
			System.out.println();
		}
		return reqPageSet;
	}

	public HashMap<String,Object> recipeDetail(int reqRecipeNo) {
		Recipe recipeDetail = recipeDao.recipeDetail(reqRecipeNo);
		List<RecipeIngredient> recipeIngredientList = recipeDao.recipeIngredientList(reqRecipeNo);
		List<RecipeCookingOrder> recipeCookingOrderList = recipeDao.recipeCookingOrderList(reqRecipeNo);
		List<RecipeComment> recipeCommentList = recipeDao.recipeCommentList(reqRecipeNo);
		
		HashMap<String, Object> recipeDetailSet = new HashMap<>();
		recipeDetailSet.put("recipeDetail", recipeDetail);
		recipeDetailSet.put("recipeIngredientList", recipeIngredientList);
		recipeDetailSet.put("recipeCookingOrderList", recipeCookingOrderList);
		recipeDetailSet.put("recipeCommentList", recipeCommentList);
		System.out.println("레시피 상세 내용 : "+recipeDetail);
		System.out.println("레시피 재료 내용 : " + recipeIngredientList);
		System.out.println("레시피 조리 순서 : " + recipeCookingOrderList);
		System.out.println("레시피 조리 순서 : " + recipeCommentList);
		return recipeDetailSet;
	}
}
