package com.shop.controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.dto.NoticeDto;
import com.shop.service.NoticeService;



@Controller
public class NoticeController {

	@Autowired
	private NoticeService noticeService;

	@GetMapping("/notice")
	public String noticeList(Model model) {
		
	    model.addAttribute("notices", noticeService.getAllNotices());
	    
	    // 현재 인증 정보에서 아이디 추출
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();

	    // 아이디가 admin인 경우에만 isAdmin 변수를 true로 설정
	    model.addAttribute("isAdmin", "admin".equals(username));
	    return "notice_list";
	}

	@PostMapping("/notice/new")
	public String createNotice(@RequestParam String title, @RequestParam String content) {
	    // 현재 인증 정보에서 아이디 추출
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();

	    // 아이디가 admin인 경우에만 공지사항 작성
	    if ("admin".equals(username)) {
	        noticeService.createNotice(new NoticeDto(title, content), username);
	    }
	    return "redirect:/";
	}
	
    @GetMapping("/notice/{id}")
    public String noticeDetail(@PathVariable Long id, Model model) {
        model.addAttribute("notice", noticeService.getNoticeById(id));
        return "notice_detail";
    }
    
    
}