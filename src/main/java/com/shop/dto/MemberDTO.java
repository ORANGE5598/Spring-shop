package com.shop.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/* 
 * MemberDTO
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MemberDTO {
	
	private String id;
	private String password;
	private String name;
	private String email;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	private String address;
	private String phoneNumber;
	private int point;
	
}
