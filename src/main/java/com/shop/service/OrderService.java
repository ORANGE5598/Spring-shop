package com.shop.service;

import com.shop.dto.MemberDTO;
import com.shop.dto.OrderDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageResultDTO;
import com.shop.entity.Member;
import com.shop.entity.OrderList;

public interface OrderService {
	
	// 주문 내역 출력하는 변환 메서드
	default OrderDTO entityToDto(OrderList entity) {
		
		OrderDTO dto = OrderDTO.builder().oNumber(entity.getONumber())
				.iNumber(entity.getINumber()).oCount(entity.getOCount())
				.oItemPrice(entity.getOItemPrice()).oDeliveryPrice(entity.getODeliveryPrice())
				.oTotalPrice(entity.getOTotalPrice()).createdDate(entity.getCreatedDate())
				.oName(entity.getOName()).img(entity.getImg()).updatedDate(entity.getUpdatedDate())
				.oGetPoint(entity.getOGetPoint()).build();
		
		return dto;
	}
	
	default OrderList dtoToEntity(OrderDTO dto) {
		
		OrderList order = OrderList.builder()
				.deliveryMessage(dto.getDeliveryMessage()).detailAddress(dto.getDetailAddress())
				.img(dto.getImg()).mName(dto.getMName())
				.oCount(dto.getOCount()).oDeliveryPrice(dto.getODeliveryPrice())
				.oGetPoint(dto.getOGetPoint()).oItemPrice(dto.getOItemPrice())
				.oName(dto.getOName()).iNumber(dto.getINumber())
				.oTotalPrice(dto.getOTotalPrice()).oUsePoint(dto.getOUsePoint())
				.paymentMethod(dto.getPaymentMethod()).phoneNumber(dto.getPhoneNumber())
				.roadAddress(dto.getRoadAddress())
				.build();
		
		return order;
	}
	
	OrderDTO read(Long oNumber);
	
	Long order(OrderDTO dto);
	
	PageResultDTO<OrderDTO, OrderList> getList(Long id, PageRequestDTO pageRequestDTO);
	
	Long beforeDeposit(Long id);
	
	Long beforeDelivery(Long id);
	
	Long Deliverying(Long id);
	
	Long afterDelivery(Long id);
	
	Long cancleStatus(Long id);
	
	Long exchangeStatus(Long id);
	
	Long returnStatus(Long id);
	
}
