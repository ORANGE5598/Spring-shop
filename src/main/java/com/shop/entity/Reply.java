package com.shop.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class Reply extends BaseEntity{

	@Id
	private Long pno;
	
	private String text;
	private String replyer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private QnA qna;
	
	public void changeText(String text) {
		this.text = text;
	}
	
	
}
