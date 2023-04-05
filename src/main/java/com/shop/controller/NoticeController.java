package com.shop.controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.CartDTO;
import com.shop.dto.NoticeDto;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.MemberDTO.ResponseDTO;
import com.shop.service.CartService;
import com.shop.service.MemberService;
import com.shop.service.NoticeService;
import com.shop.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;
	private final MemberService memberService;
	private final CartService cartService;

	@GetMapping("/notice")
	public String noticeList(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		
		Long id = user.getMemberDTO().getId();
		ResponseDTO member = memberService.getById(id);
		
		Long cartCount = cartService.getCartCount(id);
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		
		int totalPrice = 0;
		for (CartDTO cart : cartDTOList) {
			totalPrice += cart.getCPrice() * cart.getCount();
		}
		
		model.addAttribute("member", member);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cartList", cartDTOList);
		model.addAttribute("count", cartCount);
		
	    model.addAttribute("notices", noticeService.getAllNotices());
	    
	    // 현재 인증 정보에서 아이디 추출
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();

	    // 아이디가 admin인 경우에만 isAdmin 변수를 true로 설정
	    model.addAttribute("isAdmin", "admin".equals(username));
	    return "board/notice/notice";
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
	    return "redirect:/notice";
	}
	
    @GetMapping("/notice/{id}")
    public String noticeDetail(@PathVariable Long id, Model model) {
        model.addAttribute("notice", noticeService.getNoticeById(id));
        return "notice_detail";
    }
    
    
}