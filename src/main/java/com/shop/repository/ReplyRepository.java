package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.QnaEntity;
import com.shop.entity.ReplyEntity;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
	List<ReplyEntity> findAllByQnaEntityOrderByIdDesc(QnaEntity qnaEntity);
}