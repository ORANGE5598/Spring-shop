package com.shop.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.CartDTO;
import com.shop.service.CartService;
import com.shop.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CartController {
	
	private final CartService cartService;
	
	@PostMapping("/addCart")
	public void addCart(CartDTO dto, @AuthenticationPrincipal UserAdapter user) {
		
		
		cartService.saveCart(dto, user);
	}
}
