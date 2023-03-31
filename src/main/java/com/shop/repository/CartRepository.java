package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
	@Query(value = "SELECT c FROM Cart c WHERE c.mId =:id")
	List<Cart> getCartList(@Param("id") Long id);
	
}
