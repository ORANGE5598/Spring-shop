package com.shop.dto;

import com.shop.entity.Item;

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
	private Long iCategory;	// 상품 카테고리
	private Long iPrice;	// 상품 가격
//	private Long iDeliveryPrice;	// 배송비
	private String iInfo;	// 상품 설명
	private Long iInstock;	// 재고
	private String iImg;	// 상품 이미지
	private String iSize;	// 상품 사이즈
	private String brand;	// 브랜드
	
	public ItemDTO(Item item) {
	    // Item 객체의 필드를 ItemDTO 객체의 필드로 복사합니다.
	    this.iNumber = item.getINumber();
	    this.iName = item.getIName();
	    this.iPrice = item.getIPrice();
	    this.iImg = item.getIImg();
	    this.iInstock = item.getIInstock();
	    this.iSize = item.getISize();
//	    this.iDeliveryPrice = item.getIDeliveryPrice();
	}
}
