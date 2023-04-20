package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shop.entity.Item;

public interface AdminRepository extends JpaRepository<Item, Long> {
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '결제완료'")
	long donePayment();
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '배송중'")
	long deliverying();
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '배송완료'")
	long afterDelivery();
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '반품대기'")
	long beforeCancle();
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '반품완료'")
	long afterCancle();
	
}