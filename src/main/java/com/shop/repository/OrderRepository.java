package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.entity.OrderHistory;

public interface OrderRepository extends JpaRepository<OrderHistory, Long> {
	
	// Item의 iNumber를 기준으로 주문내역을 가져오는 메서드 선언
	@Query("SELECT o FROM OrderHistory o WHERE o.oNumber =:oNumber")
	OrderHistory getItemByNumber(@Param("oNumber") Long oNumber);
	
}
