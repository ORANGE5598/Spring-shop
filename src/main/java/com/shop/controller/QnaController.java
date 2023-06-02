package com.shop.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.dto.QnaDTO;
import com.shop.service.QnaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class QnaController {
	
	private final QnaService qnaService;
	

    private void getQnaList(Model model) {
        List<QnaDTO> qnaDTOList = qnaService.findAll();
        model.addAttribute("qnaList", qnaDTOList);
    }

    @PostMapping("/qna")
    public String save(@ModelAttribute QnaDTO qnaDTO, Model model) {
        log.info("QnaDTO: " + qnaDTO);
        qnaService.save(qnaDTO);
        getQnaList(model);
        return "qnaindex";
    }

    @GetMapping("/qna")
    public String findAll(Model model) {
        getQnaList(model);
        return "qnaindex";
    }
}