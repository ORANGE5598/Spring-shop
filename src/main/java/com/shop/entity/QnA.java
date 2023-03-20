package com.shop.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
public class QnA extends BaseEntity{

	@Id
	private Long qno;
	
	private String qTitle;
	private String qContent;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Member qWriter;
	
	public void changeTitle(String title) {
		this.qTitle = title;
	}
	
	public void changeContent(String content) {
		this.qContent = content;
	}
}
