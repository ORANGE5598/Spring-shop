package com.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shop.dto.ReplyDTO;
import com.shop.entity.QnaEntity;
import com.shop.entity.ReplyEntity;
import com.shop.repository.QnaRepository;
import com.shop.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {

	private final ReplyRepository replyRepository;
	private final QnaRepository qnaRepository;

	public Long save(ReplyDTO replyDTO) {
		Optional<QnaEntity> optionalQnaEntity = qnaRepository.findById(replyDTO.getQnaId());
		if (optionalQnaEntity.isPresent()) {
			QnaEntity qnaEntity = optionalQnaEntity.get();
			ReplyEntity replyEntity = ReplyEntity.toSaveEntity(replyDTO, qnaEntity);
			return replyRepository.save(replyEntity).getId();
		} else {
			return null;
		}
	}

	public List<ReplyDTO> findAll(Long qnaId) {
		QnaEntity qnaEntity = qnaRepository.findById(qnaId).get();
		List<ReplyEntity> replyEntityList = replyRepository.findAllByQnaEntityOrderByIdDesc(qnaEntity);
		List<ReplyDTO> replyDTOList = new ArrayList<>();
		for(ReplyEntity replyEntity: replyEntityList) {
			ReplyDTO replyDTO = ReplyDTO.toReplyDTO(replyEntity, qnaId);
			replyDTOList.add(replyDTO);
		}
		return replyDTOList;
	}

}