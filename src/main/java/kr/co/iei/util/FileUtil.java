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
		//원본 파일명 추출		-> test.txt
		String filename = file.getOriginalFilename();
		String onlyFilename = filename.substring(0,filename.lastIndexOf("."));
		String extention = filename.substring(filename.lastIndexOf("."));
		//실제로 업로드할 파일명 변수를 선언
		String filepath = null;
		//파일명이 중복되면 증가시키면서 뒤에 붙일 변수
		int count = 0;
		
		while(true) {
			if(count==0) {
				filepath = onlyFilename+extention;
			}else {
				filepath = onlyFilename+"_"+count+extention;//test_1.txt
			}
			//위에서 만든 파일명이 사용중인지 체크
			File checkFile = new File(savepath+filepath);
			//해당 파일명으로 파일이 존재하지 않으면 반복문 종료
			if(!checkFile.exists()) {
				break;
			}
			//파일이 존재하면 카운트를 하나 올려서 다시 반복
			count++;
			
		}
		//중복 체크 끝 -> 파일명 결정 및 업로드 수행
		
		File uploadFile = new File(savepath+filepath);
		try {
			//파일 업로드
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
			//첨부파일은 현재 서버 프로그램으로 읽어오기 위한 주스트림 생성
			FileInputStream fis = new FileInputStream(downFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			//읽어온 파일을 사용자에게 내보낼 주스트림 생성 -> response 객체 내부에 존재
			ServletOutputStream sos = response.getOutputStream();
			//속도 개선을 위한 보조스트림 생성
			BufferedOutputStream bos = new BufferedOutputStream(sos);
			
			//다운로드할 파일 이름 처리(사용자가 받을 파일 이름)
			String resFilename = new String(filename.getBytes("UTF-8"),"ISO-8859-1");
			//파일 다운로드를 위한 HTTP Header 설정(응답형식/파일이름)
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="+resFilename);
			
			//파일 읽어서 클라이언트에게 전송
			while(true) {
				int read = bis.read();
				if(read==-1) {
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
