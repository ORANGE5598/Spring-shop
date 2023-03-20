package com.shop.member.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/* 
 * MemberDTO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
	
	private String id;
	private String pw;
	private String name;
	private String email;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}
