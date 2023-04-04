package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.entity.Item;

public interface AdminRepository extends JpaRepository<Item, Long> {
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '배송중'")
	Long deliverying();
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '배송완료'")
	Long afterDelivery();
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '반품대기'")
	Long beforeCancle();
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '반품완료'")
	Long afterCancle();
	
}
