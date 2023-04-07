package com.shop.entity;


import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
abstract class BaseEntity {
	
	//@creationTimestamp는 데이터베이스 엔티티의 생성 시간을 추적하는 데 사용됨
	@CreationTimestamp
	@Column(name = "regDate", updatable = false)
	private LocalDateTime regDate;
	
	
	@LastModifiedDate
	@Column(name = "modDate")
	private LocalDateTime modDate;
	
}