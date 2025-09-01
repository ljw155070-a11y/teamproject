package kr.co.iei.notice.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	public String noticeList(@RequestParam(value="reqPage", required=false) Integer reqPage, Model model) {
		if(reqPage == null) reqPage = 1;
		NoticeListData nl = noticeService.selectNoticeList(reqPage);
		model.addAttribute("list", nl.getList());
		model.addAttribute("pageNav", nl.getPageNav());
		return "notice/list";
	}
	
	@GetMapping(value="/writeFrm")
	public String noticeWriteFrm() {
		return "notice/writeFrmEditor";
	}
	
	@PostMapping(value = "/write")
	public String noticeWrite(Notice n, MultipartFile[] upfile, Model model) {
		List<NoticeFile> fileList = new ArrayList<NoticeFile>();
		if (!upfile[0].isEmpty()) {
			String savepath = root + "/notice/";

			for (MultipartFile file : upfile) {
				String filename = file.getOriginalFilename();
				String filepath = fileUtil.upload(savepath, file);
				NoticeFile noticeFile = new NoticeFile();
				noticeFile.setFilename(filename);
				noticeFile.setFilepath(filepath);
				fileList.add(noticeFile);
			}
		}
		int result = noticeService.insertNotice(n, fileList);
		if(result == 1) {
			model.addAttribute("title", "공지사항 작성 완료!");
			model.addAttribute("text", "공지사항이 등록되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/notice/list?reqPage=1");
			return "common/msg";
		}else if(result == 0) {
			model.addAttribute("title", "공지사항 작성 실패!");
			model.addAttribute("text", "공지사항 등록을 실패하였습니다.");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/notice/list?reqPage=1");
			return "common/msg";
		}
		return "notice/list";
	}

	@GetMapping(value = "/detail") // =view
	public String noticeDetail(int noticeNo, @SessionAttribute(required = false) Member member, Model model) {
		int memberNo = member == null ? 0 : member.getMemberNo();
		Notice n = noticeService.selectOneNotice(noticeNo, memberNo);
		if (n == null) {
			model.addAttribute("title", "게시글 조회 실패");
			model.addAttribute("text", "이미 삭제된 게시글입니다.");
			model.addAttribute("icon", "info");
			model.addAttribute("loc", "/notice/list?reqPage=1");
			return "common/msg";
		} else {
			model.addAttribute("n", n);
			return "notice/detail";
		}
	}

	@GetMapping(value="/fileDown")
	public void fileDown(int noticeFileNo, HttpServletResponse response) {
		NoticeFile noticeFile = noticeService.selectOneNoticeFile(noticeFileNo);
		String savepath = root + "/notice/";
		fileUtil.downloadFile(savepath, noticeFile.getFilepath(), noticeFile.getFilename(), response);
	}
	
	@GetMapping(value="/updateFrm")
	public String updateFrm(int noticeNo, Model model) {
		Notice n = noticeService.selectOneNotice(noticeNo, 0);
		model.addAttribute("n", n);
		return "notice/updateFrm";
	}
	@PostMapping(value="/update")
	public String update(Notice n, MultipartFile[] upfile, int[] delFileNo) {
		List<NoticeFile> fileList = new ArrayList<NoticeFile>();
		String savepath = root + "/notice/";
		if(!upfile[0].isEmpty()) {
			for (MultipartFile file : upfile) {
				String filename = file.getOriginalFilename();
				String filepath = fileUtil.upload(savepath, file);
				NoticeFile nf = new NoticeFile();
				nf.setFilename(filename);
				nf.setFilepath(filepath);
				fileList.add(nf);
			}
		}
		List<NoticeFile> delFileList = noticeService.updateNotice(n, fileList, delFileNo);

		for (NoticeFile noticeFile : delFileList) {
			File delFile = new File(savepath + noticeFile.getFilepath());
			delFile.delete();
		}
		return "redirect:/notice/detail?noticeNo=" + n.getNoticeNo();
	}
	@GetMapping(value = "/delete")
	public String delete(int noticeNo, Model model) {
		List<NoticeFile> list = noticeService.deleteNotice(noticeNo);
		String savepath = root + "/notice/";
		for (NoticeFile noticeFile : list) {
			File delFile = new File(savepath + noticeFile.getFilepath());
			delFile.delete();
		}
		model.addAttribute("title", "게시글 삭제 완료");
		model.addAttribute("text", "게시글이 삭제 되었습니다.");
		model.addAttribute("icon", "success");
		model.addAttribute("loc", "/notice/list?reqPage=1");
		return "common/msg";
	}
	
	@PostMapping(value="/editorImg", produces="plain/text;charset=utf-8")
	@ResponseBody
	public String editorImgUpload(MultipartFile upfile) {
		String savepath = root + "/notice/editor/";
		String filepath = fileUtil.upload(savepath, upfile);
		return filepath;
	}
	
}