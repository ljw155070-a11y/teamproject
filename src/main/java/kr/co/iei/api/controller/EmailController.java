package kr.co.iei.api.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.iei.member.model.service.MemberService;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.util.EmailSender;

@Controller
@RequestMapping(value = "/emailApi")
public class EmailController {
	
	@Autowired
	private EmailSender emailSender;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping(value = "/email")
	public String email() {
		
		return "member/joinFrm";
	}
	
	
	@GetMapping(value="/sendCode")
	@ResponseBody
	public String sendCode(String receiver) {
		
		Member member = new Member();
		
		if(receiver != "") {
			String emailTitle = "레시피허브 인증메일입니다";
			
			Random r = new Random();
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<6; i++) {
				//대문자 r.nextInt(26)+65
				//소문자 r.nextInt(26)+97
				//숫자(0~9) : r.nextInt(10)
				int flag = r.nextInt(3);
				if(flag == 0) {
					int randomCode = r.nextInt(10);
					sb.append(randomCode);
				}else if (flag == 1){
					char randomCode = (char)(r.nextInt(26)+65);
					sb.append(randomCode);
				}else if(flag == 2) {
					char randomCode = (char)(r.nextInt(26)+97);
					sb.append(randomCode);
				}
			}
			String emailContent = "<h1>안녕하세요 레시피허브 입니다.</h1>";
			
			emailContent += "<h3>인증번호 [";
			emailContent += "<span style='color:red;'>";
			emailContent += sb.toString();
			emailContent += "</span>";
			emailContent += "]입니다.</h3>";
			
			emailSender.sendMail(emailTitle,receiver,emailContent);
			
			return sb.toString();
		}else {
			return null;
		}
		
	}
	
}
