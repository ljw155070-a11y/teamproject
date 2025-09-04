package kr.co.iei.recipe.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.member.model.vo.Member;
import kr.co.iei.recipe.model.service.RecipeService;
import kr.co.iei.recipe.model.vo.Recipe;
import kr.co.iei.recipe.model.vo.RecipeComment;
import kr.co.iei.recipe.model.vo.RecipeCookingOrder;
import kr.co.iei.recipe.model.vo.RecipeIngredient;
import kr.co.iei.util.FileUtil;

@Controller
@RequestMapping(value="/recipe")
public class RecipeController {
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping(value="/search")
	public String recipeSearch(Model model,int reqPage,String field,String searchInput){
		HashMap<String,Object> searchReqpageSet = recipeService.recipeSearchList(reqPage,field,searchInput);
		model.addAttribute("reqPageSet",searchReqpageSet);
		System.out.println(field);
		System.out.println(searchInput);
		return "recipe/list";
	}
	
	
	@GetMapping(value="/list")
	public String recipeList(Model model,int reqPage, @SessionAttribute(required = false) Member member) {
		//reqPage 의 값에 해당하는 페이지에 리스트를 전달
		HashMap<String,Object> reqPageSet = recipeService.recipeList(reqPage);
		model.addAttribute("reqPageSet",reqPageSet);
		return "recipe/list";
	}
	
	@GetMapping(value="/detail")
	public String recipeDetail(Model model,int reqRecipeNo,@SessionAttribute(required = false) Member member) {
		HashMap<String, Object> recipeDetailSet = recipeService.recipeDetail(reqRecipeNo);
		model.addAttribute("recipeDetailSet",recipeDetailSet);
		System.out.println(recipeDetailSet);
		return "recipe/detail";
	}
	
	@PostMapping(value="/commentInsert")
	public String recipeCommentInsert(RecipeComment rc) {
		int result = recipeService.recipeCommentInsert(rc);
		System.out.println(result);
		return "redirect:/recipe/detail?reqRecipeNo="+rc.getRecipeNo();
	}
	
	@ResponseBody
	@PostMapping(value="/gradeInsert")
	public String recipeGradeInsert(int recipeNo,int memberNo, int recipeRate) {
		int result = recipeService.recipeGradeInsert(recipeNo,memberNo,recipeRate);
		if(result>0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@ResponseBody
	@PostMapping(value="report")
	public String recipeReport(int recipeNo,int memberNo) {
		int result = recipeService.recipeReport(recipeNo,memberNo);
		if(result>0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@GetMapping(value="/insertFrm")
	public String recipeInsertFrm(Model model, @SessionAttribute Member member) {
		System.out.println(member);
		model.addAttribute(member);
		return "recipe/insertFrm";
	}
	
	@ResponseBody
	@PostMapping(value="/delete")
	public String recipeDelete(int recipeNo) {
		System.out.println("게시글 삭제 컨트롤러 호출됨");
		System.out.println(recipeNo);
		int result = recipeService.recipeDelete(recipeNo);
		System.out.println(result);
		if(result>0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@ResponseBody
	@PostMapping(value="/deleteComment")
	public HashMap<String, Object> recipeDeleteComment(int recipeCommentNo,int recipeNo) {
		System.out.println("게시글 댓글 삭제 컨트롤러 호출됨");
		System.out.println(recipeCommentNo);
		HashMap<String, Object> recipeCommentDeleteResult = recipeService.recipeCommentDelete(recipeCommentNo,recipeNo);
		System.out.println(recipeCommentDeleteResult);
		return recipeCommentDeleteResult;
	}
	
	@ResponseBody
	@PostMapping(value="commentReport")
	public String recipeCommentReport(int recipeCommentNo,int memberNo) {
		int result = recipeService.recipeCommentReport(recipeCommentNo,memberNo);
		if(result>0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	//우선 파일 경로 저장시켜야 함
	@Value("${file.root}")
	private String root;
	
	//파일 업로드 및 다운로드 관련 기능이 구현된 클래스의 메소드를 Autowired로 생성한다.
	@Autowired
	private FileUtil fileUtil;
	

	
	@PostMapping(value="/insert")
	public String insertRecipe(Recipe r, 
								MultipartFile recipeThumbnailFile,
								String[] recipeIngredientName, 
								String[] recipeIngredientVolume, 
								String[] recipeCookingContent, 
								MultipartFile[] recipeCookingOrderImgFile,
								@SessionAttribute Member member,
								Model model) {
		
		r.setMemberNo(member.getMemberNo());
		//전달 받은 정보 형태부터 확인
		/*
		 * System.out.println(r);
		System.out.println(recipeIngredientName[0]);
		System.out.println(recipeIngredientName[1]);
		System.out.println(recipeIngredientVolume[0]);
		System.out.println(recipeIngredientVolume[1]);
		System.out.println(recipeCookingContent[0]);
		System.out.println(recipeCookingContent[1]);
		System.out.println(recipeThumbnailFile);
		 */
 
		/*
		 * 레시피
		 * 	-RECIPE_NO				: 현재 0임 자동 채번 - 
			-RECIPE_TYPE				: r에 있음
			-RECIPE_TITLE			: r에 있음
			-RECIPE_CAUTION			: r에 있음
			-RECIPE_COOKING_TIME		: r에 있음
			-RECIPE_LEVEL			: r에 있음
			RECIPE_VIEW_COUNT		: r에 있으나 초기값 0
			RECIPE_WRITE_DATE		: sysdate 자동
			RECIPE_UPDATE_DATE		: null
			-MEMBER_NO				: r에 있음
			RECIPE_THUMBNAIL_PATH	: null 세팅해야함
		 * 
		 */
		if(!recipeThumbnailFile.isEmpty()) {
			String thumbSavepath = root+"/recipe/";
			String filename = recipeThumbnailFile.getOriginalFilename();
			String filepath = fileUtil.upload(thumbSavepath,recipeThumbnailFile);
			System.out.println("/upload/recipe/"+filepath);
			r.setRecipeThumbnailPath("/upload/recipe/"+filepath);
		}//비어있으면 기본 이미지 경로로 세팅해야 함
		//이러면 r 은 준비 완료
		
		//재료랑 조리 순서 준비해야함
		ArrayList<RecipeIngredient> ingredientList = new ArrayList<>();
		System.out.println("1길이"+recipeIngredientName.length);
		System.out.println("2길이"+recipeIngredientVolume.length);
		
			for(int i=0; i<recipeIngredientName.length; i++) {
				String name = recipeIngredientName[i];
				String vol=null;
				if(recipeIngredientVolume.length!=0) {
					vol  = recipeIngredientVolume[i];
				}
				RecipeIngredient ri = new RecipeIngredient();
				/*
				 * 재료
				 * 	RECIPE_INGREDIENT_NO		: 자동 채번
					RECIPE_NO					: 서비스에서 r부터 집어넣고 no를 전달 받아야함
					RECIPE_INGREDIENT_NAME		: ri에 있음
					RECIPE_INGREDIENT_VOLUME	: ri에 있음
				 * 
				 * 
				 */
				ri.setRecipeIngredientName(name);
				ri.setRecipeIngredientVolume(vol);
				ingredientList.add(ri);
			}
		
		ArrayList<RecipeCookingOrder> cookingOrderList = new ArrayList<>(); 
		String cookingOrderfileSavepath = root+"/recipe/";
		if(recipeCookingContent != null) {
			for(int i=0; i<recipeCookingContent.length; i++) {
				String content = recipeCookingContent[i];
				String filename = recipeCookingOrderImgFile[i].getOriginalFilename();
				String filepath = fileUtil.upload(cookingOrderfileSavepath,recipeCookingOrderImgFile[i]);
				System.out.println(content);
				System.out.println(filepath);
				RecipeCookingOrder rco = new RecipeCookingOrder();
				/*
				 * 	RECIPE_COOKING_ORDER			: i+1 값으로 저장해서 넘겨줌
					RECIPE_NO						: 서비스에서 r부터 집어넣고 no 전달받아야함 
					RECIPE_COOKING_CONTENT			: rco에서 줌
					RECIPE_COOKING_ORDER_IMG_PATH	: rco 에서 줌
				 */
				
				rco.setRecipeCookingOrder(i+1);
				rco.setRecipeCookingContent(content);
				rco.setRecipeCookingOrderImgPath("/upload/recipe/"+filepath);
				cookingOrderList.add(rco);
			}
		}
		
		int result = recipeService.insertRecipe(r, ingredientList, cookingOrderList);
		if (result > 1) {
	        model.addAttribute("title", "레시피 등록 완료");
	        model.addAttribute("text", "레시피가 등록되었습니다.");
	        model.addAttribute("icon", "success");
	        model.addAttribute("loc", "/recipe/list?reqPage=1");
	    } else {
	        model.addAttribute("title", "레시피 등록 실패");
	        model.addAttribute("text", "잠시 후 다시 시도해주세요.");
	        model.addAttribute("icon", "error");
	        model.addAttribute("loc", "/recipe/insertFrm");
	    }
		
		return "common/msg";
	}
	
	@GetMapping(value="/editFrm")
	public String editRecipeFrm(Model model,int recipeNo) {
		System.out.println("수정 컨트롤러 호출됨");
		System.out.println(recipeNo);
		Recipe r = recipeService.editRecipeInfo(recipeNo);
		ArrayList<RecipeIngredient> riList = recipeService.editRecipeIngredientInfo(recipeNo);
		ArrayList<RecipeCookingOrder> rcoList = recipeService.editRecipeCookingOrderInfo(recipeNo);
		
		System.out.println("수정 컨트롤러에서 수신한 내용 : ");
		System.out.println(r);
		System.out.println(riList);
		System.out.println(rcoList);
		
		model.addAttribute(r);
		model.addAttribute(riList);
		model.addAttribute(rcoList);
		
		return "recipe/editFrm";
	}
	
	@PostMapping(value="/edit")
	public String editRecipe(int recipeNo,String recipeTitle,String[] recipeIngredientName,String[] recipeIngredientVolume,String recipeCaution,Model model) {
		//레시피 재료는 그냥 해당하는 레시피 넘버 싹 삭제하고, 다시 insert 하자...
		ArrayList<RecipeIngredient> ingredientList = new ArrayList<>();
		if(recipeIngredientName[0] != null) {
			for(int i=0; i<recipeIngredientName.length; i++) {
				String name = recipeIngredientName[i];
				String vol  = recipeIngredientVolume[i];
				RecipeIngredient ri = new RecipeIngredient();
				/*
				 * 재료
				 * 	RECIPE_INGREDIENT_NO		: 자동 채번
					RECIPE_NO					: 서비스에서 r부터 집어넣고 no를 전달 받아야함
					RECIPE_INGREDIENT_NAME		: ri에 있음
					RECIPE_INGREDIENT_VOLUME	: ri에 있음
				 * 
				 * 
				 */
				ri.setRecipeIngredientName(name);
				ri.setRecipeIngredientVolume(vol);
				ingredientList.add(ri);
			}
		}
		//나머지는 업데이트
		int result = recipeService.editRecipe(recipeNo,recipeTitle,ingredientList,recipeCaution);
		if (result > 3) {
	        model.addAttribute("title", "레시피 수정 완료");
	        model.addAttribute("text", "레시피가 수정되었습니다.");
	        model.addAttribute("icon", "success");
	        model.addAttribute("loc", "/recipe/detail?reqRecipeNo="+recipeNo);
	    } else {
	        model.addAttribute("title", "레시피 등록 실패");
	        model.addAttribute("text", "잠시 후 다시 시도해주세요.");
	        model.addAttribute("icon", "error");
	        model.addAttribute("loc", "/recipe/detail?reqRecipeNo="+recipeNo);
	    }
		return "common/msg";
	}
	
	@ResponseBody
	@PostMapping(value="editComment")
	public String editComment(int recipeCommentNo,int recipeNo,String editText) {
		System.out.println(recipeCommentNo);
		System.out.println(recipeNo);
		System.out.println(editText);
		int result = recipeService.editComment(recipeCommentNo,recipeNo,editText);
		String r = null;
		if(result==1) {
			r = "success";
		}else {
			r = "fail"; 
		}
		return r;
	}
}
		
		
		
	
	/*
	 * 	public String boardWrite(Board b, MultipartFile[] upfile, Model model) {
		System.out.println(b);
		System.out.println(upfile.length);
		List<BoardFile> fileList = new ArrayList<BoardFile>();
		if(!upfile[0].isEmpty()) {
			String savepath = root+"/board/";
			for(MultipartFile file : upfile) {
				String filename = file.getOriginalFilename();
				String filepath = fileUtil.upload(savepath, file);
				BoardFile boardFile = new BoardFile();
				boardFile.setFilename(filename);
				boardFile.setFilepath(filepath);
				fileList.add(boardFile);
			}
		}
		int result = boardService.insertBoard(b,fileList);
		model.addAttribute("title","자유게시판 작성 완료");
		model.addAttribute("text","자게 등록 완료");
		model.addAttribute("icon","success");
		model.addAttribute("loc","/notice/list?reqPage=1");
		return "common/msg";
	}
	 * 
	 * */

