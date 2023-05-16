package com.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.dto.QnaDTO;
import com.shop.entity.QnaEntity;
import com.shop.repository.QnaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaService {

	private final QnaRepository qnaRepository;

	public void save(QnaDTO qnaDTO) {
		QnaEntity qnaEntity = QnaEntity.toSaveEntity(qnaDTO);

		qnaRepository.save(qnaEntity);

	}

	public List<QnaDTO> findAll() {
		List<QnaEntity> qnaEntityList = qnaRepository.findAll();
		List<QnaDTO> qnaDTOList = new ArrayList<>();
		for (QnaEntity qnaEntity : qnaEntityList) {
			qnaDTOList.add(QnaDTO.toQnaDTO(qnaEntity));
		}
		return qnaDTOList;
	}

	@Transactional
	public void updateHits(Long id) {
		qnaRepository.updateHits(id);

	}

	public QnaDTO findById(Long id) {
		Optional<QnaEntity> optioanlQnaEntity = qnaRepository.findById(id);
		if (optioanlQnaEntity.isPresent()) {
			QnaEntity qnaEntity = optioanlQnaEntity.get();
			QnaDTO qnaDTO = QnaDTO.toQnaDTO(qnaEntity);
			return qnaDTO;
		} else {
			return null;

		}
	}

	public QnaDTO update(QnaDTO qnaDTO) {
		QnaEntity qnaEntity = QnaEntity.toUpdateEntity(qnaDTO);
		qnaRepository.save(qnaEntity);
		return findById(qnaDTO.getId());
	}

	public void delete(Long id) {
		qnaRepository.deleteById(id);

	}

	public Page<QnaDTO> paging(Pageable pageable) {
		int page = pageable.getPageNumber()-1;
		int pageLimti = 3;
		Page<QnaEntity> qnaEntities = qnaRepository.findAll(PageRequest.of(page, pageLimti,Sort.by(Sort.Direction.DESC,"id")));
		
		Page<QnaDTO> qnaDTOS = qnaEntities.map(qna -> new QnaDTO(qna.getId(),qna.getQnaWriter(),qna.getQnaTitle(),qna.getQnaHits(),qna.getRegDate()));
		return qnaDTOS;
	}

}