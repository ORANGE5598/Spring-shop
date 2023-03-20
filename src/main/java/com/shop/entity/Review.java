package com.shop.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Review {
	
	@Id
	private Long rno;
	
	private String rTitle;
	private String rContent;
	@ManyToOne(fetch = FetchType.LAZY)
	private Member rWirter;
	private String rImg;
	
	public void changerTitle(String title) {
		this.rTitle = title;
	}
	
	public void cangerContent(String content) {
		this.rContent = content;
	}

}
