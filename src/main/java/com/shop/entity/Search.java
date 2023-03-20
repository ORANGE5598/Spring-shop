package com.shop.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class Search {
	
	@Id
	private String keyword;
	private Long count;
	
}
