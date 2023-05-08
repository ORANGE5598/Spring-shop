package com.shop.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.dto.OrderDTO;
import com.shop.dto.MemberDTO.RequestDTO;
import com.shop.service.CartService;
import com.shop.service.MemberService;
import com.shop.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@RestController	// 데이터만 주고받는 json
@Log4j2
public class OrderController2 {
	
	private final OrderService orderService;
	private final CartService cartService;
	private final MemberService memberService;
	
	// DB에 상품 정보를 저장하는 페이지를 연결하는 메서드
	@PostMapping({"/insertOrder/{cart_id}"})
	public String insertItem(OrderDTO dto, RedirectAttributes redirectAttributes, RequestDTO requestDTO, @PathVariable("cart_id") Long cart_id) {
		
		Long oNumber = orderService.order(dto);
		
		memberService.changePoint(requestDTO, dto.getMId());
		
		cartService.deleteById(cart_id);
		log.info(cart_id + " 이 삭제됨.");
		
		
		redirectAttributes.addFlashAttribute(oNumber);
		
		return "content/user/orderBy";
	}
	
	@PostMapping("/insertOrder")
	public String insertItem(OrderDTO dto, RedirectAttributes redirectAttributes, RequestDTO requestDTO) {
		Long oNumber = orderService.order(dto);
		
		memberService.changePoint(requestDTO, dto.getMId());
		redirectAttributes.addFlashAttribute(oNumber);
		
		return "content/user/orderBy";
	}
	
}
