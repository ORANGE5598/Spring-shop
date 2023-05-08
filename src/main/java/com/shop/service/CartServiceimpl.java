package com.shop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.shop.config.auth.UserAdapter;
import com.shop.dto.CartDTO;
import com.shop.entity.Cart;
import com.shop.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceimpl implements CartService {
	
	private final CartRepository cartRepository;
	
	@Override
	public List<CartDTO> getCartList(Long id) {
		
		List<Cart> cartList = cartRepository.getCartList(id);
		
		List<CartDTO> result = cartList.stream().map(cart -> EntityToDto(cart)).collect(Collectors.toList());
		
		return result;
	}
	
	@Override
	public void saveCart(CartDTO dto, @AuthenticationPrincipal UserAdapter user) {
		
		Cart entity = dtoToEntity(dto, user);
		
		cartRepository.save(entity);
	}
	
	@Override
	public void deleteById(Long cart_id) {
		Cart entity = cartRepository.findById(cart_id).orElseThrow(() -> new IllegalArgumentException("해당 장바구니 정보 없음."));
		cartRepository.delete(entity);
		
	}
	
	@Override
	public Long getCartCount(Long id) {
		
		Long result = cartRepository.getCartCount(id);
		
		return result;
	}
	
	@Override
	public CartDTO order(Long cNumber) {
		
		Cart result = cartRepository.getCartByNumber(cNumber);
		
		return EntityToDto(result);
	}
}
