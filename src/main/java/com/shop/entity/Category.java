package com.shop.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Builder
public class Category {

	@Id
	private Long categoryNumber;
	private String categoryName;
}

