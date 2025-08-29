package kr.co.iei.notice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

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
	public String noticeList(int reqPage, Model model) {
		NoticeListData nl = noticeService.selectNoticeList(reqPage);
		model.addAttribute("list", nl.getList());
		model.addAttribute("list", nl.getPageNav());
		return "notice/list";
	}
	
	@GetMapping(value="/writeFrm")
	public String noticeWriteFrm() {
		return "notice/writeFrm";
		
	}

	@GetMapping(value="/write")
	public String noticeWrite(Notice n, MultipartFile[] upfile, Model model) {
		List<NoticeFile> fileList = new ArrayList<NoticeFile>();
		if(!upfile[0].isEmpty()) {
			String savepath = root + "/notice/";
			
			for(MultipartFile file : upfile) {
				String filename = file.getOriginalFilename();
				String filepath = fileUtil.upload(savepath, file);
				NoticeFile noticeFile = new NoticeFile();
				noticeFile.setFilename(filename);
				noticeFile.setFilepath(filepath);
				fileList.add(noticeFile);
			}
		}
		int result = noticeService.insertNotice(n, fileList);
		model.addAttribute("title", "공지사항 작성 완료!");
		model.addAttribute("text", "공지사항이 등록되었습니다.");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/notice/list?reqPage=1");
		return "common/msg";
	}
}

	
	/*
	@GetMapping(value="/list")
	public String noticeList(Model model) {
		List list = noticeService.selectAll();
		model.addAttribute("list", list);
		return "notice/list";
	}
}
	/*
	@GetMapping(value="/list")
	public String noticeList(int reqPage, Model model) {
		NoticeListData nl = noticeService.selectNoticeList(reqPage);
		model.addAttribute("list", nl.getList());
		model.addAttribute("pageNav", nl.getPageNav());
		return "notice/list";
		
	}
}
	/*
	@GetMapping(value="/view")	
	public String noticeModify(Model model) {
		List list = noticeService.selectNoticeList();
		
//		model.addAttribute("pageNav", ListData.getPageNav());
		return "notice/view";
	}	
	
	@GetMapping(value="/writeFrm")	
	public String noticeWriteFrm(Model model) {
		List list = noticeService.selectNoticeList();
		
//		model.addAttribute("pageNav", ListData.getPageNav());
		return "notice/writeFrm";
	}	
	
	@GetMapping(value="/detail")	
	public String noticeDetail(Model model) {
		List list = noticeService.selectNoticeList();
		
//		model.addAttribute("pageNav", ListData.getPageNav());
		return "notice/detail";
	}	
	
}
*/