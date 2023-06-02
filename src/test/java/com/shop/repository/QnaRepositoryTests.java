package com.shop.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shop.entity.Qna;

@SpringBootTest
public class QnaRepositoryTests {

	@Autowired
	private QnaRepository qnaRepository;
	
	@Test
	public void insertDummies() {
		IntStream.rangeClosed(1, 1).forEach(i -> {
			Qna qna = Qna.builder().qnaContent("sdfsdfsdfsdf")
					.qnaTitle("타이틀2").build();
			qnaRepository.save(qna);
		});
	}
}
