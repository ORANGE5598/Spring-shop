package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.entity.Notice;



@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
