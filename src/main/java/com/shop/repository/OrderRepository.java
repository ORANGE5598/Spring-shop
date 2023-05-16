package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.entity.OrderList;

public interface OrderRepository extends JpaRepository<OrderList, Long> {
	
	@Query("SELECT o FROM OrderList o WHERE o.oNumber =:oNumber")
	OrderList getOrderByNumber(@Param("oNumber") Long oNumber);
	
	@Query("SELECT o FROM OrderList o WHERE o.mId =:id")
	List<OrderList> getOrderById(@Param("id") Long oNumber);
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.mId =:id")
	Long countAll(@Param("id") Long oNumber);
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '결제완료' AND o.mId =:id")
	Long donePayment(@Param("id") Long oNumber);
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '배송중' AND o.mId =:id")
	Long deliverying(@Param("id") Long oNumber);
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '배송완료' AND o.mId =:id")
	Long afterDelivery(@Param("id") Long oNumber);
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '반품대기' AND o.mId =:id")
	Long beforeCancle(@Param("id") Long oNumber);
	
	@Query("SELECT count(o) FROM OrderList o WHERE o.deliveryStatus = '반품완료' AND o.mId =:id")
	Long afterCancle(@Param("id") Long oNumber);
	
	@Query("SELECT o FROM OrderList o WHERE o.mId =:id")
	List<OrderList> getImgById(@Param("id") Long id);
}