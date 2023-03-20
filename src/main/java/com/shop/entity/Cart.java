package com.shop.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class Cart {

	@Id
	private Long cId;
	private String cItemName;
	private Long cPrice;
	private String cInfo;
	private String cImg;
}
