package kr.co.iei;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//스프링부트 설정파일임을 나타내는 어노테이션
@Configuration
public class WebConfig implements WebMvcConfigurer{
	@Value(value="${file.root}")
	private String root;

	//서버가 관리하는 자원에 대한 접근 설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	/*
	 * registry
	 * 		.addResourceHandler(사용자측에서 입력하는 경로)
	 * 		.addResourceLocations(실제 서버 경로);
	 */
		
		//spring boot 기본 설정(addResourceHandlers를 구현하지 않으면 기본적으로 설정되어있는 세팅)
		//addResourceHandlers를 직접 구현하면 기본 세팅도 사라지므로 기본 세팅 추가
		registry
			.addResourceHandler("/**")
			.addResourceLocations("classpath:/templates/","classpath:/static/");
		registry
			.addResourceHandler("/upload/**")
			.addResourceLocations("file:///"+root+"/");
		
	}
	
}
