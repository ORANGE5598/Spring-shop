package com.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	
	// Item의 iNumber를 기준으로 상품 상세를 가져오는 메서드 선언
	@Query("SELECT i FROM Item i WHERE i.iNumber =:iNumber")
	Item getItemByNumber(@Param("iNumber") Long iNumber);
	
	// Item 상품 전체를 리턴
	@Query(value = "SELECT * FROM Item WHERE i_number > 0 ORDER BY i_number ASC", nativeQuery = true)
	Page<Item> getItem(Pageable pageable);
	
	// 상품 총 갯수 리턴
	@Query(value = "SELECT count(*) FROM Item", nativeQuery = true)
	Long getAll();
	
	// 가격 오름차순에 따른 정렬
	@Query(value = "SELECT * FROM Item WHERE i_number > 0 ORDER BY i_price ASC, i_number ASC", nativeQuery = true)
	Page<Item> getItemPriceAsc(Pageable pageable);
	
	// 가격 내림차순에 따른 정렬
	@Query(value = "SELECT * FROM Item WHERE i_number > 0 ORDER BY i_price DESC, i_number ASC", nativeQuery = true)
	Page<Item> getItemPriceDesc(Pageable pageable);
	
	// iCategory=1인 상품 전체 리턴
	@Query("SELECT i FROM Item i WHERE i.iCategory = 1")
	Page<Item> getTop(Pageable pageable);
	
	// iCategory=2인 상품 전체 리턴
	@Query("SELECT i FROM Item i WHERE i.iCategory = 2")
	Page<Item> getBottom(Pageable pageable);
	
	// iCategory=3인 상품 전체 리턴
	@Query("SELECT i FROM Item i WHERE i.iCategory = 3")
	Page<Item> getFootwear(Pageable pageable);
	
	// iCategory=4인 상품 전체 리턴
	@Query("SELECT i FROM Item i WHERE i.iCategory = 4")
	Page<Item> getBag(Pageable pageable);
	
	// iCategory=5인 상품 전체 리턴
	@Query("SELECT i FROM Item i WHERE i.iCategory = 5")
	Page<Item> getHeadwear(Pageable pageable);
	
	// iCategory=6인 상품 전체 리턴
	@Query("SELECT i FROM Item i WHERE i.iCategory = 6")
	Page<Item> getTech(Pageable pageable);
	
}
