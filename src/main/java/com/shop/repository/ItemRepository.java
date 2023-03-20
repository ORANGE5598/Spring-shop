package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	
	// Item의 iNumber를 기준으로 상품 상세를 가져오는 메서드 선언
	@Query("SELECT i FROM Item i WHERE i.iNumber =:iNumber")
	Item getItemByNumber(@Param("iNumber") Long iNumber);
	
}
