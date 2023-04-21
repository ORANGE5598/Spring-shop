package com.shop.service;

import java.util.List;

import com.shop.dto.OrderDTO;
import com.shop.entity.OrderList;

public interface OrderService {
	
	// 주문 내역 출력하는 변환 메서드
	default OrderDTO entityToDto(OrderList entity) {
		
		OrderDTO dto = OrderDTO.builder().oNumber(entity.getONumber())
				.iNumber(entity.getINumber()).oCount(entity.getOCount()).mName(entity.getMName())
				.oItemPrice(entity.getOItemPrice()).oDeliveryPrice(entity.getODeliveryPrice())
				.oTotalPrice(entity.getOTotalPrice()).createdDate(entity.getCreatedDate())
				.oName(entity.getOName()).img(entity.getImg()).oSize(entity.getOSize())
				.updatedDate(entity.getUpdatedDate()).deliveryStatus(entity.getDeliveryStatus())
				.build();
		
		return dto;
	}
	
	default OrderList dtoToEntity(OrderDTO dto) {
		
		OrderList order = OrderList.builder()
				.deliveryMessage(dto.getDeliveryMessage()).detailAddress(dto.getDetailAddress())
				.img(dto.getImg()).mName(dto.getMName())
				.oCount(dto.getOCount()).oDeliveryPrice(dto.getODeliveryPrice())
				.oItemPrice(dto.getOItemPrice()).oTotalPrice(dto.getOTotalPrice())
				.oName(dto.getOName()).iNumber(dto.getINumber())
				.paymentMethod(dto.getPaymentMethod()).phoneNumber(dto.getPhoneNumber())
				.roadAddress(dto.getRoadAddress()).oSize(dto.getOSize())
				.build();
		
		return order;
	}
	
	OrderDTO read(Long oNumber);
	
	Long order(OrderDTO dto);
	
	Long modify(OrderDTO dto, Long oNumber);	// 수정 필요
	
	List<OrderDTO> getList(Long id);
	
	List<OrderDTO> getAllList();
	
	Long getAllCount();
	
	Long allStatus(Long id);
	
	Long donePayment(Long id);
	
	Long deliverying(Long id);
	
	Long afterDelivery(Long id);
	
	Long beforeCancle(Long id);
	
	Long afterCancle(Long id);
	
	List<OrderList> getImgList(Long id);
	
}