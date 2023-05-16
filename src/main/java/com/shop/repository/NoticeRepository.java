package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}