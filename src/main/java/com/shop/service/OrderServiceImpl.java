package com.shop.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
				.oItemPrice(dto.getOItemPrice()).oTotalPrice(dto.getOTotalPrice())
				.oName(dto.getOName()).iNumber(dto.getINumber()).oSize(dto.getOSize())
				.paymentMethod(dto.getPaymentMethod()).phoneNumber(dto.getPhoneNumber())
				.roadAddress(dto.getRoadAddress()).deliveryStatus("결제완료")
				.build();
		
		orderRepository.save(entity);
		
		return entity.getONumber();
	}
	
	// 수정 필요
	@Override
	public Long modify(OrderDTO dto, Long oNumber) {
		
		OrderList entity = orderRepository.getById(oNumber);
		entity.changeDeliveryStatus(dto.getDeliveryStatus());
		
		orderRepository.save(entity);
		
		return entity.getONumber();
		
	}
	
	
	@Override
	public PageResultDTO<OrderDTO, OrderList> getList(Long id, PageRequestDTO pageRequestDTO) {
		
		Function<OrderList, OrderDTO> fn = (en -> entityToDto(en));
		
		Page<OrderList> result = orderRepository.getOrderById(id, pageRequestDTO.getPageable(Sort.by("updatedDate").ascending()));
		
		return new PageResultDTO<>(result, fn);
	}
	
	@Override
	public List<OrderDTO> getAllList() {
		
		List<OrderList> orderList = orderRepository.findAll();
		
		List<OrderDTO> result = orderList.stream().map(order -> entityToDto(order)).collect(Collectors.toList());
		
		return result;
	}
	
	@Override
	public Long getAllCount() {
		
		Long result = orderRepository.count();
		
		
		return result;
	}
	
	@Override
	public Long allStatus(Long id) {
		
		Long result = orderRepository.countAll(id);
		
		return result;
	}
	
	@Override
	public Long deliverying(Long id) {
		
		Long result = orderRepository.deliverying(id);
		
		return result;
	}
	
	@Override
	public Long afterDelivery(Long id) {
		
		Long result = orderRepository.afterDelivery(id);
		
		return result;
	}

	@Override
	public Long beforeCancle(Long id) {
		
		Long result = orderRepository.beforeCancle(id);
		
		return result;
	}

	@Override
	public Long afterCancle(Long id) {
		
		Long result = orderRepository.afterCancle(id);
		
		return result;
	}
	
}
