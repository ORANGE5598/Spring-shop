package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.FileEntity;


public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
