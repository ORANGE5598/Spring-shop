package com.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.dto.QnaDTO;
import com.shop.entity.Member;
import com.shop.entity.Qna;
import com.shop.repository.MemberRepository;
import com.shop.repository.QnaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class QnaService {
	
	private final QnaRepository qnaRepository;
	private final MemberRepository memberRepository;
	
	
	public void save(QnaDTO qnaDTO) {
		
//		Optional<Member> memberOptional = memberRepository.findById(qnaDTO.getMemberId());
//		
//		if (memberOptional.isPresent()) {
//			Member member = memberOptional.get();
//			//Qna qna = Qna.toSaveEntity(qnaDTO, member);
//			
//			
//		}
		Qna qna = new Qna();
		qna.setQnaTitle(qnaDTO.getQnaTitle());
		qna.setQnaContent(qnaDTO.getQnaContent());
		qna.setModDate(qnaDTO.getModDate());
		qna.setRegDate(qnaDTO.getRegDate());
		qna.setQnaHits(0);
		qnaRepository.save(qna);

	}
	
	
	@Autowired
	public List<QnaDTO> findAll() {
		 log.info("findAll method called!!!!"); // 로깅 추가
		
		List<Qna> qnaEntityList = qnaRepository.findAll();
		List<QnaDTO> qnaDtoList = new ArrayList<>();
		
		for(Qna QnaEntity : qnaEntityList) {
			qnaDtoList.add(QnaDTO.toQnaDTO(QnaEntity));
		}
		return qnaDtoList;
		

	}
	
	
	
	
	
}
