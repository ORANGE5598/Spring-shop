package com.shop.dto;

import java.time.LocalDateTime;

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
public class ReviewDTO2 {
	
	private Long id;
	
	private LocalDateTime regDate;
	
	private LocalDateTime modDate;
	
	private String reviewTitle;
	
	private String reviewContent;
	
	private int reviewRating;
	
	private String reviewWriter;
	
	private String reviewImg;
}
