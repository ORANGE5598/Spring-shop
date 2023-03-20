package com.shop.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
public class Notice extends BaseEntity{

	@Id
	private Long nno; 
	
	private String nTitle;
	private String nContent;
	private String nWriter;
	
	public void changeNoticeTitle(String nTitle) {
		this.nTitle = nTitle;
	}
	
	public void changeNoticeContent(String nContent) {
		this.nContent = nContent;
	}
}
