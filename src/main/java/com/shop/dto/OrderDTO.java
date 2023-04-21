package com.shop.dto;

import javax.validation.constraints.NotBlank;

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
//	@NotBlank(message = "전화번호 입력.")
	private String phoneNumber;
	private String oName;
	private String iNumber;
	private String img;
//	@NotBlank(message = "도로명주소 입력.")
	private String roadAddress;
//	@NotBlank(message = "상세주소 입력.")
	private String detailAddress;
	private String deliveryMessage;
	private String paymentMethod;
	private String createdDate;
	private String updatedDate;
	private String deliveryStatus;
	private String oSize;
}