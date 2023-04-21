/*
 * 소셜로그인 API에서 제공
 */

package com.shop.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
/** DB에 들어갈 회원정보 Entity 정의 **/
public class Member extends BaseTimeEntity implements Serializable {
    private static final long serialVersionUID = -588567706409108940L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	/** 회원가입 시 자동으로 부여되는 회원 넘버링 **/
    private Long id;

    @Column(nullable = false, unique = true)
    /** 로그인 ID **/
    private String username;

    @Column
    /** 로그인 패스워드 **/
    private String password;

    @Column(nullable = true)
    /** 회원 이름 **/
    private String name;

    @Column(nullable = false, unique = true)
    /** 회원 이메일 **/
    private String email;
    
    @Column(nullable = true)
    /** 회원 주소 1 **/
    private String address1;
    
    @Column(nullable = true)
    /** 회원 주소 2 **/
    private String address2;
    
    @Column(nullable = true)
    /** 회원 휴대폰번호 **/
    private String phone;
    
    @Column(nullable = true)
    private String gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    /** 회원 권한 **/
    private MemberRole role; // 회원가입하면 무조건 USER로 설정
    
    @Column(nullable = true)
    /** 회원 포인트 **/
    private int point;
    
    /** 프로필 이미지 **/
    private String profile;
    
    /** 회원 정보 업데이트 메서드 **/    
    public void update(String password, String name, String email, String address1, String address2, String phone, String profile) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.phone = phone;
        this.profile = profile;
    }
    
    public void changePoint(int point) {
    	this.point = point;
    }
    
    /** 비밀번호 변경 메서드 **/
    public void updatePassword(String password){
        this.password = password;
    }

    /** 소셜 로그인 시 이미 등록된 회원인 경우 수정 날짜 업데이트, 기존 데이터는 보존 **/
    public Member updateUpdatedDate(){
        this.onPreUpdate(); // 수정 날짜 업데이트
        return this;
    }

}