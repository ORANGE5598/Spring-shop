package com.shop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.dto.ReplyDTO;
import com.shop.service.ReplyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("reply")
@RequiredArgsConstructor
public class ReplyController {
	private final ReplyService replyService;

	@PostMapping("/save")
	public ResponseEntity save(@ModelAttribute ReplyDTO replyDTO) {
		
		Long saveResult = replyService.save(replyDTO);
		if(saveResult != null) {
			
			List<ReplyDTO> replyDTOList = replyService.findAll(replyDTO.getQnaId());
			return new ResponseEntity<>(replyDTOList, HttpStatus.OK);
		}else {
			return new ResponseEntity<>("해당게시글이 존재하지 않음", HttpStatus.NOT_FOUND);
		}
		
	}
}