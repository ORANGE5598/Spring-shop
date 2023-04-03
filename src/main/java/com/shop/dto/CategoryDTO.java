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
public class CategoryDTO {
	
	private Long categoryNumber;
	private String categoryName;
	private String engName;
}
