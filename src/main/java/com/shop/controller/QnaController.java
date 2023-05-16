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
import com.shop.dto.ReplyDTO;
import com.shop.service.QnaService;
import com.shop.service.ReplyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("qna")
public class QnaController {
	private final QnaService qnaService;
	private final ReplyService replyService;

	@GetMapping("/home")
	public String index() {
		return "/board/qna/qna_index";
	}
	@GetMapping("/save")
	public String saveForm() {
		return "/board/qna/qna_save";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute QnaDTO qnaDTO) {
		qnaService.save(qnaDTO);

		return "/board/qna/qna_index";
	}

	@GetMapping("/")
	public String findAll(Model model) {

		// db에서 전체게시글데이터가져와서 list.html에 보여주기
		List<QnaDTO> qnaDTOList = qnaService.findAll();
		model.addAttribute("qnaList", qnaDTOList);
		return "/board/qna/qna_list";
	}

	@GetMapping("/{id}")
	public String findById(@PathVariable Long id, Model model,
							@PageableDefault(page=1) Pageable pageable) {

		qnaService.updateHits(id);
		QnaDTO qnaDTO = qnaService.findById(id);
		
		List<ReplyDTO> replyDTOList = replyService.findAll(id);
		model.addAttribute("replyList", replyDTOList);
		
		model.addAttribute("qna", qnaDTO);
		model.addAttribute("page",pageable.getPageNumber());
		return "/board/qna/qna_detail";
	}

	@GetMapping("update/{id}")
	public String updateForm(@PathVariable Long id, Model model) {
		QnaDTO qnaDTO = qnaService.findById(id);
		model.addAttribute("qnaUpdate", qnaDTO);
		return "/board/qna/qna_update";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute QnaDTO qnaDTO, Model model) {
		QnaDTO qna = qnaService.update(qnaDTO);
		model.addAttribute("qna", qna);
		return "/board/qna/qna_detail";

	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		qnaService.delete(id);
		return "redirect:/qna/paging";

	}

	@GetMapping("/paging")
	public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {

		Page<QnaDTO> qnaList = qnaService.paging(pageable);
		int blockLimit = 3;
		int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; 																									// ~~
		int endPage = ((startPage + blockLimit - 1) < qnaList.getTotalPages()) ? startPage + blockLimit - 1
				: qnaList.getTotalPages();

		// page 갯수 20개
		// 현재 사용자가 3페이지
		// 1 2 3
		// 현재 사용자가 7페이지
		// 7 8 9
		// 보여지는 페이지 갯수 3개
		// 총 페이지 갯수 8개

		model.addAttribute("qnaList", qnaList);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "/board/qna/qna_paging";

	}
}