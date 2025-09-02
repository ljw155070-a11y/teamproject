package kr.co.iei.notice.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

import jakarta.servlet.http.HttpServletResponse;
import kr.co.iei.member.model.vo.Member;
import kr.co.iei.notice.model.service.NoticeService;
import kr.co.iei.notice.model.vo.Notice;
import kr.co.iei.notice.model.vo.NoticeFile;
import kr.co.iei.notice.model.vo.NoticeListData;
import kr.co.iei.util.FileUtil;

@Controller
@RequestMapping(value="/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	@Value("${file.root}")
	private String root;
	
	@Autowired
	private FileUtil fileUtil;
	
	@GetMapping(value="/list")
	public String list(int reqPage, Model model) {
		NoticeListData nl = noticeService.selectNoticeList(reqPage);
		model.addAttribute("list", nl.getList());
		model.addAttribute("pageNav", nl.getPageNav());
		return "notice/list";
	}
	@GetMapping(value="/searchTitle")
	public String searchTitle(String searchTitle, int reqPage, Model model) {
		if(!searchTitle.isEmpty()) {
			NoticeListData nl = noticeService.searchTitle(reqPage, searchTitle);
			if(nl != null) {
				model.addAttribute("list", nl.getList());
				model.addAttribute("pageNav", nl.getPageNav());
			}else {
				model.addAttribute("list", "작성된 게시글이 존재하지 않습니다.");
			}
		}else {
			model.addAttribute("list", "작성된 게시글이 존재하지 않습니다.");
		}
		return "notice/list";
	}
	@GetMapping(value="/detail")
	public String detail(int noticeNo, Model model) {
		Notice notice = noticeService.selectOnetNotice(noticeNo);
		model.addAttribute("notice", notice);
		return "notice/detail";
	}
	@GetMapping(value="/delete")
	public String delete(int noticeNo, Model model) {
		int result = noticeService.deleteNotice(noticeNo);
		if(result > 0) {
			model.addAttribute("title", "삭제 성공");
			model.addAttribute("text", "게시글이 삭제되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/notice/list?reqPage=1");
			return "common/msg";
		}else {
			model.addAttribute("title", "삭제 실패");
			model.addAttribute("text", "잠시후 다시 시도해 주세요.");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/notice/view?noticeNo="+noticeNo);
			return "common/msg";
		}
	}
	@GetMapping(value="/writeFrm")
	public String writeFrm(@SessionAttribute Member member, Model model) {
		return "notice/writeFrm";
	}
	
	@PostMapping(value="/editorImg", produces="plain/text;charset=utf-8")
	@ResponseBody
	 public String editorImgUpload(MultipartFile upfile) {
	 	String savepath = root + "/notice/editor/";
		String filepath = fileUtil.upload(savepath, upfile);
		return filepath;
	}
	@PostMapping(value = "/write")
	public String write(Notice notice, Model model, MultipartFile[] upfile) {
		System.out.println(11);
		List<NoticeFile> fileList = new ArrayList<NoticeFile>();
	
		if (!upfile[0].isEmpty()) {
			
			String savepath = root + "/notice/";

			for (MultipartFile file : upfile) {
				
				String filename = file.getOriginalFilename();
				String filepath = fileUtil.upload(savepath, file);
				
				NoticeFile noticeFile = new NoticeFile();
				noticeFile.setFilename(filename);
				noticeFile.setFilepath(filepath);
				System.out.println("공지사항 파일 : "+noticeFile);
				fileList.add(noticeFile);
			}
		}
		int result = noticeService.insertNotice(notice, fileList);
		model.addAttribute("title", "공지사항 작성 완료!");
		model.addAttribute("text", "공지사항이 등록되었습니다.");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/notice/list?reqPage=1");
		return "common/msg";
	}
	@GetMapping(value="/updateFrm")
	public String updateFrm(int noticeNo, Model model) {
		Notice notice = noticeService.selectOnetNotice(noticeNo);
		model.addAttribute("notice", notice);
		return "notice/updateFrm";
	}
	@PostMapping(value="/update")
	public String update(Notice notice, MultipartFile[] upfile, int[] delFileNo) {
		// 새로 추가한 파일 업로드
		List<NoticeFile> fileList = new ArrayList<NoticeFile>();
		String savepath = root + "/notice/";
		if (!upfile[0].isEmpty()) {
			for (MultipartFile file : upfile) {
				String filename = file.getOriginalFilename();
				String filepath = fileUtil.upload(savepath, file);
				NoticeFile nf = new NoticeFile();
				nf.setFilename(filename);
				nf.setFilepath(filepath);
				fileList.add(nf);
			}
		}
		List<NoticeFile> delFileList = noticeService.updateNotice(notice, fileList, delFileNo);

		for (NoticeFile noticeFile : delFileList) {
			File delFile = new File(savepath + noticeFile.getFilepath());
			delFile.delete();
		}

		return "redirect:/notice/detail?noticeNo=" + notice.getNoticeNo();
	}
	
	@GetMapping(value="/fileDown")
	public void fileDown(int noticeFileNo, HttpServletResponse response) {
		NoticeFile noticeFile = noticeService.selectOneNoticeFile(noticeFileNo);
		String savepath = root + "/notice/";
		fileUtil.downloadFile(savepath, noticeFile.getFilepath(), noticeFile.getFilename(), response);
	}
	
	}

