package com.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ItemDTO {
	
	private Long iNumber;	// 상품고유번호
	private String iName;	// 상품명
	private String iCategory;	// 상품 카테고리
	private Long iPrice;	// 상품 가격
	private Long iDeliveryPrice;	// 배송비
	private String iInfo;	// 상품 설명
	private Long iInstock;	// 재고
	private String iImg;	// 상품 이미지
	
}
