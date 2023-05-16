package com.shop.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.CartDTO;
import com.shop.dto.ItemDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.MemberDTO.ResponseDTO;
import com.shop.service.CartService;
import com.shop.service.ItemService;
import com.shop.service.MemberService;

import lombok.RequiredArgsConstructor;

// 주문페이지, 주문목록 관련 컨트롤러
@RequiredArgsConstructor
@Controller
public class OrderController {
	
	private final ItemService itemService;
	private final MemberService memberService;
	private final CartService cartService;
	
	@GetMapping("/orderBy")
	public String orderBy(Long iNumber, Long oCount, Long dPrice, Long cart_id, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model, @AuthenticationPrincipal UserAdapter user) {
		
		ItemDTO dto = itemService.order(iNumber);
		Long id = user.getMemberDTO().getId();
		
		Long iPrice = dto.getIPrice();
		List<CartDTO> cartDTOList = cartService.getCartList(id);
		ResponseDTO responseDto = memberService.getById(id);
		Long cartCount = cartService.getCartCount(id);
		Long totalPrice = iPrice * oCount + dPrice;
		
		int totalPrice2 = 0;
		for (CartDTO cartDTO : cartDTOList) {
			totalPrice2 += cartDTO.getCPrice() * cartDTO.getCount();
		}
		model.addAttribute("totalPrice", totalPrice2);
		model.addAttribute("ordering", dto);
		model.addAttribute("total", totalPrice);
		model.addAttribute("member", responseDto);
		model.addAttribute("cartList", cartDTOList);
		model.addAttribute("count", cartCount);
		model.addAttribute("cart_id", cart_id);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+ cart_id);
		
		return "content/user/orderBy";
	}
	
}
