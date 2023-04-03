package com.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shop.dto.ReplyDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="reply_table")
public class ReplyEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 20, nullable = false)
	private String replyWriter;
	
	@Column
	private String replyContents;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qna_id")
	private QnaEntity qnaEntity;

	public static ReplyEntity toSaveEntity(ReplyDTO replyDTO, QnaEntity qnaEntity) {
		ReplyEntity replyEntity = new ReplyEntity();
		replyEntity.setReplyWriter(replyDTO.getReplyWriter());
		replyEntity.setReplyContents(replyDTO.getReplyContents());
		replyEntity.setQnaEntity(qnaEntity);
		return replyEntity;
		
	}
	
	
	
	
	
	
}
