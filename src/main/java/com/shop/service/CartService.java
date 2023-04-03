package com.shop.service;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.CartDTO;
import com.shop.entity.Cart;

public interface CartService {
	
	default Cart dtoToEntity(CartDTO dto, @AuthenticationPrincipal UserAdapter user) {
	
		Long id = user.getMemberDTO().getId();
		
		Cart cart = Cart.builder()
				.cImg(dto.getCImg()).cInfo(dto.getCInfo())
				.cName(dto.getCName()).cPrice(dto.getCPrice())
				.mId(id).count(dto.getCount())
				.cNumber(dto.getCNumber())
				.build();
	
		return cart;
	}
	
	default CartDTO EntityToDto(Cart cEntity) {
		
		CartDTO cartDTO = CartDTO.builder()
				.id(cEntity.getId()).cImg(cEntity.getCImg())
				.cInfo(cEntity.getCInfo()).cName(cEntity.getCName())
				.cPrice(cEntity.getCPrice()).count(cEntity.getCount())
				.cNumber(cEntity.getCNumber()).mId(cEntity.getMId())
				.build();
		
		return cartDTO;
	}
	
	List<CartDTO> getCartList(Long id);
	
	void saveCart(CartDTO dto, @AuthenticationPrincipal UserAdapter user);
	
	Long getCartCount(Long id);
	
	CartDTO order(Long cNumber);
	
}
