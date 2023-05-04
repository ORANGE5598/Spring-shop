package com.shop.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.CartDTO;
import com.shop.dto.PageRequestDTO2;
import com.shop.dto.MemberDTO.ResponseDTO;
import com.shop.dto.NoticeDTO;
import com.shop.service.CartService;
import com.shop.service.MemberService;
import com.shop.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;
	private final MemberService memberService;
	private final CartService cartService;

	@GetMapping("/notice")
	public String noticeList(@ModelAttribute("requestDTO") PageRequestDTO2 pageRequestDTO2, Model model, @AuthenticationPrincipal UserAdapter user) {
		
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
	    model.addAttribute("notices", noticeService.getNotices(pageRequestDTO2));
	    
	    return "board/notice/notice";
	}
    
	@PostMapping("/notice/new")
	public String createNotice(@RequestParam String title, @RequestParam String content) {
	    
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    
	    String contentWithoutHtml = content.replaceAll("<p[^>]*>", "").replaceAll("</p[^>]*>", "");
	    
	    noticeService.createNotice(new NoticeDTO(title, contentWithoutHtml), username);
	    return "redirect:/notice";
	}
	
	@PostMapping("/deleteNotice")
	public String deleteNotice(Long id) {
		
		noticeService.remove(id);
		
		return "redirect:/notice";
	}
	
}