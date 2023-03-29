package com.shop.service;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shop.dto.OrderDTO;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageResultDTO;
import com.shop.entity.OrderList;
import com.shop.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	
//	@Override
//	public PageResultDTO<OrderDTO, OrderList> getList(PageRequestDTO pageRequestDTO) {
//
//		Function<OrderList, OrderDTO> fn = (en -> entityToDto(en));
//		
//		String id = "user10";
//		
//		Page<OrderList> result = orderRepository.getListById(id, pageRequestDTO.getPageable(Sort.by("regDate").ascending()));
//		
//		return new PageResultDTO<>(result, fn);
//	}
	
	@Override
	public OrderDTO read(Long oNumber) {
		
		OrderList result = orderRepository.getOrderByNumber(oNumber);
		
		return entityToDto(result);
	}
	
	@Override
	public Long order(OrderDTO dto) {
		
		OrderList entity = dtoToEntity(dto).builder()
				.deliveryMessage(dto.getDeliveryMessage()).detailAddress(dto.getDetailAddress())
				.img(dto.getImg()).mId(dto.getMId()).mName(dto.getMName())
				.oCount(dto.getOCount()).oDeliveryPrice(dto.getODeliveryPrice())
				.oGetPoint(dto.getOGetPoint()).oItemPrice(dto.getOItemPrice())
				.oName(dto.getOName()).iNumber(dto.getINumber())
				.oTotalPrice(dto.getOTotalPrice()).oUsePoint(dto.getOUsePoint())
				.paymentMethod(dto.getPaymentMethod()).phoneNumber(dto.getPhoneNumber())
				.roadAddress(dto.getRoadAddress()).deliveryStatus("배송준비중")
				.build();
		
		orderRepository.save(entity);
		
		return entity.getONumber();
	}

	@Override
	public PageResultDTO<OrderDTO, OrderList> getList(Long id, PageRequestDTO pageRequestDTO) {
		
		Function<OrderList, OrderDTO> fn = (en -> entityToDto(en));
		
		Page<OrderList> result = orderRepository.getOrderById(id, pageRequestDTO.getPageable(Sort.by("updated_date").ascending()));
		
		return new PageResultDTO<>(result, fn);
	}
	
	@Override
	public Long beforeDeposit(Long id) {
		
		Long result = orderRepository.getBeforeDeposit(id);
		
		return result;
	}
	
	@Override
	public Long beforeDelivery(Long id) {
		
		Long result = orderRepository.getBeforeDelivery(id);
		
		return result;
	}
	
	@Override
	public Long Deliverying(Long id) {
		
		Long result = orderRepository.getDeliverying(id);
		
		return result;
	}
	
	@Override
	public Long afterDelivery(Long id) {
		
		Long result = orderRepository.getAfterDelivery(id);
		
		return result;
	}

	@Override
	public Long cancleStatus(Long id) {
		
		Long result = orderRepository.getCancle(id);
		
		return result;
	}

	@Override
	public Long exchangeStatus(Long id) {
		
		Long result = orderRepository.getExchange(id);
		
		return result;
	}

	@Override
	public Long returnStatus(Long id) {
		
		Long result = orderRepository.getReturn(id);
		
		return result;
	}
	
}
