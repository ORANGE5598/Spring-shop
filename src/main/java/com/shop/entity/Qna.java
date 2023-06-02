package com.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.shop.dto.QnaDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Qna extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String qnaTitle;
	
	@Column
	private String qnaContent;
	
	@Column
	private int qnaHits;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	
//	
//	public static Qna toSaveEntity(QnaDTO qnaDTO,Member member) {
//		Qna qna = new Qna();
//		qna.setQnaTitle(qnaDTO.getQnaTitle());
//		qna.setQnaContent(qnaDTO.getQnaContent());
//		qna.setQnaHits(0);
//		return qna;
//	}


	


}
