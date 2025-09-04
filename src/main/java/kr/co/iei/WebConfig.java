package kr.co.iei;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.iei.util.AdminInterceptor;
import kr.co.iei.util.LoginIntercepter;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	@Value(value="${file.root}")
	private String root;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/**")
			.addResourceLocations("classpath:/templates/","classpath:/static/");
		registry
			.addResourceHandler("/upload/**")
			.addResourceLocations("file:///"+root+"/");
		
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginIntercepter())
				.addPathPatterns(
							"/member/logout",
							"/member/mypage",
							"/member/update",
							"/member/deleteMember",
							"/notice/**",
							"/recipe/**",
							"/qna/**",
							"/admin/**"
						)
				.excludePathPatterns(
							"/notice/list",
							"/notice/detail",
							"/notice/filedown",
							"/notice/editor/**",
							"/notice/searchTitle",
							"/qna/list",
							"/qna/view",
							"/recipe/list",
							"/recipe/detail",
							"/recipe/search",
							"/qna/searchList"
						);
		registry.addInterceptor(new AdminInterceptor())
				.addPathPatterns("/admin/**","/notice/**")
				.excludePathPatterns("/notice/list","/notice/detail","/notice/filedown","/notice/editor/**","/notice/searchTitle");
	}
	
}
