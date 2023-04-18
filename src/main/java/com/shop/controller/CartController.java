package com.shop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.CartDTO;
import com.shop.service.CartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CartController {
	
	private final CartService cartService;
	
	@PostMapping("/addCart")
	public void addCart(CartDTO dto, @AuthenticationPrincipal UserAdapter user) {
		cartService.saveCart(dto, user);
	}
	
	@DeleteMapping(value = "/deleteCart/{cartItemId}")
	@ResponseBody
	public ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, @AuthenticationPrincipal UserAdapter user) {
		cartService.deleteCart(cartItemId);
		return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
	}
}
