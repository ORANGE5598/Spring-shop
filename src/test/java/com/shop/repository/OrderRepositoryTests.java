package com.shop.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.util.StringUtils;

import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.entity.OrderList;

@SpringBootTest
public class OrderRepositoryTests {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	
	// OrderList 테이블에 주문내역을 insert
//	@Test
//	public void insertOrders() {
//		
//		Member member = memberRepository.getMemberByNumber("user10");
//		
//		Item item = itemRepository.getItemByNumber(1L);
//		
//		OrderList order = OrderList.builder()
//				.member(member).item(item).oItemPrice(item.getIPrice()).build();
//		
//		orderRepository.save(order);
//	}
	
	
	// 여러 주문내역을 list로 묶어서 출력 테스트
//	@Transactional
//	@Test
//	public void showList() {
//		
//		Long count = orderRepository.getCountByNumber("user19");
//		
//		// member_id를 기준으로 iNumber를 추출
//		Long[] iNumber = orderRepository.getNumberById("user19");
//		
//		for(int i = 0; i < count; i++) {
//			Long number = iNumber[i]; 
//
//			Optional<OrderList> result = orderRepository.findById(number);
//			
//			System.out.println("oNumber : " + result.get().getONumber());
//			System.out.println("itemName : " + result.get().getItem().getIName());
//			System.out.println("info : " + result.get().getOItemInfo());
//			System.out.println("price : " + result.get().getOItemPrice());
//			System.out.println("memberName : " + result.get().getMember().getName());
//			
//			
//			
//		}
//		
//	}
	
}
