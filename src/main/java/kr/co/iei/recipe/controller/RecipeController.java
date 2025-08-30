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
	
	/*
	
	@PostMapping(value="/insert")
	public String insertRecipe(Recipe r, String[] recipeCookingContent, MultipartFile[] recipeCookingOrderImgPath, @SessionAttribute Member member) {
		RecipeIngredient ri = new RecipeIngredient();
		
		
		
	}
	*/
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
}
