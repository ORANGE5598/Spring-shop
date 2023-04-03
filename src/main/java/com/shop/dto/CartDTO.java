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
public class CartDTO {
	
	private Long id;
	private String cName;
	private Long cPrice;
	private String cInfo;
	private String cImg;
	private Long mId;
	private Long count;
	private Long cNumber;
	
}