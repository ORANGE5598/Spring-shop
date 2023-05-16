package com.shop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@DeleteMapping("/delete/{cart_id}")
	public ResponseEntity<?> deleteById(@PathVariable("cart_id") Long cart_id){
	    cartService.deleteById(cart_id);
	    return ResponseEntity.noContent().build();
	}
	
	
}
