package kr.co.iei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.iei.common.model.service.CommonService;
import kr.co.iei.common.model.vo.Common;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.recipe.model.service.RecipeService;
import kr.co.iei.recipe.model.vo.Recipe;

@Controller
public class HomeController {
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private CommonService commonService;
	
	@GetMapping(value="/")
	public String main(Model model) {
		//최상위
		HashMap<String, Object> topRecipeInfo = new HashMap<>();
		
		Recipe r = recipeService.recipeGradeRankingr();
		Member m = recipeService.recipeGradeRankingm();
		double avgrate = recipeService.recipeGradeRankingDouble();
		
		topRecipeInfo.put("r", r);
		topRecipeInfo.put("m", m);
		topRecipeInfo.put("avgrate", avgrate);
		
		model.addAttribute("topRecipeInfo",topRecipeInfo);
		
		System.out.println(r);
		System.out.println(m);
		System.out.println(avgrate);
		
		
		//한식
		HashMap<String, Object> koTypeOfTopInfo = new HashMap<>();
		
		Recipe koR = recipeService.koTypeOfTopInfoR();
		Member koM = recipeService.koTypeOfTopInfoM();
		double koAvgrate = recipeService.koTypeOfTopInfoAvgrate();
		
		koTypeOfTopInfo.put("koR", koR);
		koTypeOfTopInfo.put("koM", koM);
		koTypeOfTopInfo.put("koAvgrate", koAvgrate);
		
		model.addAttribute("koTypeOfRecipeInfo",koTypeOfTopInfo);
		
		
		//중식
		HashMap<String, Object> cnTypeOfTopInfo = new HashMap<>();
		
		Recipe cnR = recipeService.cnTypeOfTopInfoR();
		Member cnM = recipeService.cnTypeOfTopInfoM();
		double cnAvgrate = recipeService.cnTypeOfTopInfoAvgrate();
		
		cnTypeOfTopInfo.put("cnR", cnR);
		cnTypeOfTopInfo.put("cnM", cnM);
		cnTypeOfTopInfo.put("cnAvgrate", cnAvgrate);
		
		model.addAttribute("cnTypeOfRecipeInfo",cnTypeOfTopInfo);
		
		//일식
		HashMap<String, Object> jpTypeOfTopInfo = new HashMap<>();
		
		Recipe jpR = recipeService.jpTypeOfTopInfoR();
		Member jpM = recipeService.jpTypeOfTopInfoM();
		double jpAvgrate = recipeService.jpTypeOfTopInfoAvgrate();
		
		jpTypeOfTopInfo.put("jpR", jpR);
		jpTypeOfTopInfo.put("jpM", jpM);
		jpTypeOfTopInfo.put("jpAvgrate", jpAvgrate);
		
		model.addAttribute("jpTypeOfRecipeInfo",jpTypeOfTopInfo);
		
		//양식
		HashMap<String, Object> enTypeOfTopInfo = new HashMap<>();
		
		Recipe enR = recipeService.enTypeOfTopInfoR();
		Member enM = recipeService.enTypeOfTopInfoM();
		double enAvgrate = recipeService.enTypeOfTopInfoAvgrate();
		
		enTypeOfTopInfo.put("enR", enR);
		enTypeOfTopInfo.put("enM", enM);
		enTypeOfTopInfo.put("enAvgrate", enAvgrate);
		
		model.addAttribute("enTypeOfRecipeInfo",enTypeOfTopInfo);
		
		ArrayList<Common> commonList = new ArrayList<>();
		System.out.println("컨트롤러 호출됨");
		commonList = commonService.selectAllList();
		model.addAttribute(commonList);
		return "index";
		
		
		
	}
}
