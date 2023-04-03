package com.shop.dto;

import java.time.LocalDateTime;

import com.shop.entity.QnaEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	@ToString
	public class QnaDTO {
		private Long id;
		private String qnaWriter;
		private String qnaPass;
		private String qnaTitle;
		private String qnaContents;
		private int qnaHits;
		private LocalDateTime qnaRegDate;
		private LocalDateTime qnaModDate;
		
		public QnaDTO(Long id, String qnaWriter, String qnaTitle, int qnaHits, LocalDateTime qnaRegDate) {
			
			this.id = id;
			this.qnaWriter = qnaWriter;
			this.qnaTitle = qnaTitle;
			this.qnaHits = qnaHits;
			this.qnaRegDate = qnaRegDate;
		}
		
		//엔티티를 dto로 변환하기
		public static QnaDTO toQnaDTO(QnaEntity qnaEntity) {
			QnaDTO qnaDTO = new QnaDTO();
			qnaDTO.setId(qnaEntity.getId());
			qnaDTO.setQnaWriter(qnaEntity.getQnaWriter());
			qnaDTO.setQnaPass(qnaEntity.getQnaPass());
			qnaDTO.setQnaTitle(qnaEntity.getQnaTitle());
			qnaDTO.setQnaContents(qnaEntity.getQnaContents());
			qnaDTO.setQnaHits(qnaEntity.getQnaHits());
			qnaDTO.setQnaRegDate(qnaEntity.getRegDate());
			qnaDTO.setQnaModDate(qnaEntity.getModDate());
			return qnaDTO;
		}
	
	
	
		
	}