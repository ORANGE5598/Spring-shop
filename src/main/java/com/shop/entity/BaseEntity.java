package com.shop.entity;
/*
 *  Entity : Spring에서 테이블의 정보를 1 : 1로 가지고 있는 영속 객체.
 *  이 Entity를 이용해서 테이블 생성은 물론, update, insert, delete도 할 수 있음.
 *  Entity를 이용해서 DB에 in-out 하는 주체는 repository.
 *  
 *  이 중 @MappedSuperClass라는 어노테이션의 역할을 확인.
 *  이 어노테이션이 붙은 클래스는 기본적으로 테이블을 직접 생성하지 않고 
 *  얘를 상속받은 클래스가 그 일을 해줌.
 *  
 *  @ 아래 메인에 리스너가 등록되기 위해서 자신의 클래스에 EntityListener를 등록해야 함. 
 *  또한 이 클래스가 활성화 되어지기 위해서는 main 클래스에 @Auditing...Listener를 적용해줘야함.
 *  그 후 위 @MappedSuperClass 선언된 클래스를 상속받은 Entity가 테이블과 1:1 매핑이 되어지고 
 *  repository를 이용해서 테이블 생성 및 데이터를 in-out 시키는 순서.
 *  
 *  해서 @MappedSuperClass가 선언된 클래스는 상속 목적인 추상으로 선언되는 것이 일반적임.
 */

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
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