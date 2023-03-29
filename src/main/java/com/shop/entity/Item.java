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
public class Item extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long iNumber;
	private String iName;
	private String brand;
	private Long iCategory;
	private Long iPrice;
	private Long iDeliveryPrice;
	private String iInfo;
	private Long iInstock;
	private String iImg;
	private String iSize;
	
	
	public void changeImg(String iImg) {
		this.iImg = iImg;
	}
	
	public void changePrice(Long iPrice) {
		this.iPrice = iPrice;
	}
	
	public void changeInstock(Long iInstock) {
		this.iInstock = iInstock;
	}
	
	public void changeInfo(String iInfo) {
		this.iInfo = iInfo;
	}
	
	public void changeSize(String iInfo) {
		this.iInfo = iInfo;
	}
	
	public void changeDeliveryPrice(Long iDeliveryPrice) {
		this.iDeliveryPrice = iDeliveryPrice;
	}
	
}
