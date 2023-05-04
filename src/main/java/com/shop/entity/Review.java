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
public class Review extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String reviewTitle;
	
	private String reviewContent;
	
	private int reviewRating;
	
	private String reviewWriter;
	
	private String reviewImg;
	
	public void changeTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}
	
	public void changeContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}
	
	public void changeRating(int reviewRating) {
		this.reviewRating = reviewRating;
	}
	
}
