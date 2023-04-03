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
public class OrderDTO {
	
	private Long oNumber;
	private Long oItemPrice;
	private Long oCount;
	private Long oTotalPrice;
	private Long oDeliveryPrice;
	private Long mId;
	private String mName;
	private String phoneNumber;
	private String oName;
	private String iNumber;
	private String img;
	private String roadAddress;
	private String detailAddress;
	private String deliveryMessage;
	private String paymentMethod;
	private String createdDate;
	private String updatedDate;
	private String deliveryStatus;
	private String oSize;
}
