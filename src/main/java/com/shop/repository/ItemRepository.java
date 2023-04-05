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
	
	// 테스트중
	@Query(value = "SELECT * FROM Item WHERE i_number > 0 ORDER BY i_number ASC", nativeQuery = true)
	List<Item> getList();
	
	// 테스트중1
	@Query(value = "SELECT * FROM Item WHERE i_number > 0 AND i_category=1 ORDER BY i_number ASC", nativeQuery = true)
	List<Item> getTopList();
	
	// 테스트중4
	@Query(value = "SELECT * FROM Item WHERE i_number > 0 AND i_category=4 ORDER BY i_number ASC", nativeQuery = true)
	List<Item> getBagList();
	
	// 테스트중6
	@Query(value = "SELECT * FROM Item WHERE i_number > 0 AND i_category=6 ORDER BY i_number ASC", nativeQuery = true)
	List<Item> getTechList();
	
	// 상품 총 갯수 리턴
	@Query(value = "SELECT count(*) FROM Item", nativeQuery = true)
	Long getAll();
	
	// 카테고리에 따른 정렬
	@Query("SELECT i FROM Item i WHERE i.iCategory =:iCategory")
	Page<Item> sortCategory(Pageable pageable, @Param("iCategory") Long iCategory);
	
	// 브랜드에 따른 정렬
	@Query("SELECT i FROM Item i WHERE i.brandNumber =:brandNumber")
	Page<Item> sortBrand(Pageable pageable, @Param("brandNumber") Long brandNumber);
	
	// 가격 오름차순에 따른 정렬
	@Query("SELECT i FROM Item i ORDER BY i_price ASC, i_number ASC")
	Page<Item> sortPriceAsc(Pageable pageable);
	
	// 가격 내림차순에 따른 정렬
	@Query("SELECT i FROM Item i ORDER BY i_price DESC, i_number ASC")
	Page<Item> sortPriceDesc(Pageable pageable);
	
}