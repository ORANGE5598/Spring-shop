package com.shop.dto;

import java.time.LocalDateTime;

import com.shop.entity.Qna;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

	@NoArgsConstructor
	@Getter
	@Setter
	@ToString
	@Data
	public class QnaDTO {
		private Long id;
		private Long memberId;
		private String qnaTitle;
		private String qnaContent;
		private int qnaHits;
		private LocalDateTime regDate;
		private LocalDateTime modDate;
		
		public QnaDTO(Long id, Long memberId, String qnaTitle, String qnaContent, int qnaHits, LocalDateTime regDate,
				LocalDateTime modDate) {
			super();
			this.id = id;
			this.memberId = memberId;
			this.qnaTitle = qnaTitle;
			this.qnaContent = qnaContent;
			this.qnaHits = qnaHits;
			this.regDate = regDate;
			this.modDate = modDate;
		}
		
		public static QnaDTO toQnaDTO(Qna qnaEntity) {
			QnaDTO qnaDTO = new QnaDTO();
			qnaDTO.setId(qnaEntity.getId());
			qnaDTO.setQnaTitle(qnaEntity.getQnaTitle());
			qnaDTO.setQnaContent(qnaEntity.getQnaContent());
			qnaDTO.setModDate(qnaEntity.getModDate());
			qnaDTO.setRegDate(qnaEntity.getRegDate());
			qnaDTO.setQnaHits(qnaEntity.getQnaHits());
			return qnaDTO;
			
		}
	
	
		
	}