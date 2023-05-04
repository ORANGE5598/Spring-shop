package com.shop.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.shop.dto.QnaDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "qna_table")
public class QnaEntity extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 20,nullable = false)
	private String qnaWriter;
	@Column
	private String qnaPass;
	@Column
	private String qnaTitle;
	@Column(length = 500)
	private String qnaContents;
	@Column
	private int qnaHits;
	
	@OneToMany(mappedBy = "qnaEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReplyEntity> replyEntityList = new ArrayList<>();
	
	public static QnaEntity toSaveEntity(QnaDTO qnaDTO) {
		QnaEntity qnaEntity = new QnaEntity();
		qnaEntity.setQnaWriter(qnaDTO.getQnaWriter());
		qnaEntity.setQnaPass(qnaDTO.getQnaPass());
		qnaEntity.setQnaTitle(qnaDTO.getQnaTitle());
		qnaEntity.setQnaContents(qnaDTO.getQnaContents());
		qnaEntity.setQnaHits(0);
		return qnaEntity;
	}

	public static QnaEntity toUpdateEntity(QnaDTO qnaDTO) {
		QnaEntity qnaEntity = new QnaEntity();
		qnaEntity.setId(qnaDTO.getId());
		qnaEntity.setQnaWriter(qnaDTO.getQnaWriter());
		qnaEntity.setQnaPass(qnaDTO.getQnaPass());
		qnaEntity.setQnaTitle(qnaDTO.getQnaTitle());
		qnaEntity.setQnaContents(qnaDTO.getQnaContents());
		qnaEntity.setQnaHits(qnaDTO.getQnaHits());
		return qnaEntity;
	}

}
