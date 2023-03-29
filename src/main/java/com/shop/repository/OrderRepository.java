package com.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.entity.OrderList;

public interface OrderRepository extends JpaRepository<OrderList, Long> {
	
	@Query("SELECT o FROM OrderList o WHERE o.oNumber =:oNumber")
	OrderList getOrderByNumber(@Param("oNumber") Long oNumber);
	
	@Query("SELECT o FROM OrderList o WHERE o.mId =:id")
	Page<OrderList> getOrderById(@Param("id") Long oNumber, Pageable pageable);
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '입금확인' AND o.mId =:id")
	Long getBeforeDeposit(@Param("id") Long oNumber);
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '배송준비중' AND o.mId =:id")
	Long getBeforeDelivery(@Param("id") Long oNumber);
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '배송중' AND o.mId =:id")
	Long getDeliverying(@Param("id") Long oNumber);
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '배송완료' AND o.mId =:id")
	Long getAfterDelivery(@Param("id") Long oNumber);
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '취소' AND o.mId =:id")
	Long getCancle(@Param("id") Long oNumber);
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '교환' AND o.mId =:id")
	Long getExchange(@Param("id") Long oNumber);
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '반품' AND o.mId =:id")
	Long getReturn(@Param("id") Long oNumber);
	
	
}