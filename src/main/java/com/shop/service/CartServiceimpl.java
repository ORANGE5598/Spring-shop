package com.shop.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
	
	@Transactional
	@Override
	public void saveCart(CartDTO dto, @AuthenticationPrincipal UserAdapter user) {
		
		Cart entity = dtoToEntity(dto, user);
		
		cartRepository.save(entity);
	}
	
}
