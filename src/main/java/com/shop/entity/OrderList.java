package com.shop.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderList extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long oNumber;	// 주문번호
	
	private Long oItemPrice;	// 상품가격
	private Long oCount;	// 주문 갯수
	private Long oTotalPrice;	// 최종결제금액
	private Long oDeliveryPrice;	// 배송비
	private Long mId;	// 주문자아이디
	private String mName;	// 주문자명
	private String oName;	// 주문상품명
	private String iNumber;	// 상품번호
	private String img;	// 상품 이미지
	private String deliveryMessage;
	private String detailAddress;
	private String paymentMethod;
	private String phoneNumber;
	private String roadAddress;
	private String deliveryStatus;
	private String oSize;
	
	public void changeDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	
}