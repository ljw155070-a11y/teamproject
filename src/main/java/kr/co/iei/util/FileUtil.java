package kr.co.iei.util;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {
	public String upload(String savepath, MultipartFile file) {
		// 원본파일명 추출		test.txt
		String filename = file.getOriginalFilename();
		String onlyFilename = filename.substring(0, filename.lastIndexOf("."));
		String extention = filename.substring(filename.lastIndexOf("."));
		String filepath = null;
		int count = 0;
		
		while(true) {
			if(count == 0) {
				filepath = onlyFilename + extention; 		//test.txt
			}else {
				filepath = onlyFilename+"_"+count+extention;//text_1.txt
			}
			//위에서 만든 파일명이 사용중인지 체크
			File checkFile = new File(savepath+filepath);
			if(!checkFile.exists()) {
				break;
			}
			count++;
		}
		File uploadFile = new File(savepath+filepath);
		
		try {
			file.transferTo(uploadFile);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filepath;
	}
}
