package kr.co.iei.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

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

	public void downloadFile(String savepath, String filepath, String filename, HttpServletResponse response) {
		//다운로드할 파일
		String downFile = savepath+filepath;
				
		try {
			FileInputStream fis = new FileInputStream(downFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ServletOutputStream sos = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(sos);
			String resFilename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
					
			//파일 다운로드를 위한 HTTP header 설정(응답형식/파일이름)
			response.setContentType("application/ocete-stream");
			response.setHeader("Content-Disposition", "attachment;filename="+resFilename);
					
			//파일 읽어서 클라이언트에게 전송
			while(true) {
				int read = bis.read();
				if(read== -1) {
					break;
				}
				bos.write(read);
			}
			bos.close();
			bis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
