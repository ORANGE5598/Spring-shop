package com.shop.dto;

import java.time.LocalDateTime;

import com.shop.entity.ReplyEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplyDTO {
	
	private Long id;
	private String replyWriter;
	private String replyContents;
	private Long qnaId;
	private LocalDateTime replyRegDate;
	
	public static ReplyDTO toReplyDTO(ReplyEntity replyEntity, Long qnaId) {
		ReplyDTO replyDTO = new ReplyDTO();
		replyDTO.setId(replyEntity.getId());
		replyDTO.setReplyWriter(replyEntity.getReplyWriter());
		replyDTO.setReplyContents(replyEntity.getReplyContents());
		replyDTO.setReplyRegDate(replyEntity.getRegDate());
		replyDTO.setQnaId(qnaId);
		return replyDTO;
	}
}