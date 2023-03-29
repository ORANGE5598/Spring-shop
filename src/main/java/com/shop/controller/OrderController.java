package com.shop.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.ItemDTO;
import com.shop.dto.OrderDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.MemberDTO.ResponseDTO;
import com.shop.service.ItemService;
import com.shop.service.MemberService;
import com.shop.service.OrderService;

import lombok.RequiredArgsConstructor;

// 주문페이지, 주문목록 관련 컨트롤러
@RequiredArgsConstructor
@Controller
public class OrderController {
	
	private final OrderService orderService;
	private final ItemService itemService;
	private final MemberService memberService;
	
//	@GetMapping("/orderList")
//	public void orderList(PageRequestDTO pageRequestDTO, Model model) {
//		
//		model.addAttribute("orderList", orderService.getList(pageRequestDTO));
//	}
	
	@GetMapping("/orderDetail")
	public void orderDetail(Long oNumber, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {
		OrderDTO dto = orderService.read(oNumber);
		
		model.addAttribute("dto", dto);
	}
	
	@GetMapping("/orderBy")
	public String orderBy(Long iNumber, Long oCount, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		ItemDTO dto = itemService.order(iNumber);
		
		Long iPrice = dto.getIPrice();
		Long dPrice = dto.getIDeliveryPrice();
		Long member_id = user.getMemberDTO().getId();
		ResponseDTO responseDto = memberService.getById(member_id);
		
		Long totalPrice = iPrice * oCount + dPrice;

		model.addAttribute("ordering", dto);
		model.addAttribute("total", totalPrice);
		model.addAttribute("member", responseDto);
		
		return "content/user/orderBy";
	}
	
}
