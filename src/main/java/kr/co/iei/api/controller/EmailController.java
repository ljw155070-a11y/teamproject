package kr.co.iei.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.iei.util.EmailSender;

@Controller
@RequestMapping(value = "/emailApi")
public class EmailController {
	
	@Autowired
	private EmailSender emailSender;
	
	@GetMapping(value = "/email")
	public String email() {
		
		return "member/joinFrm";
	}
	
}
