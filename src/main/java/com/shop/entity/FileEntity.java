package com.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name="file")
@Data
public class FileEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="file_id")
	private Long id;
	private String orgNm;
	private String savedPath;
	
	@Builder
	public FileEntity(Long id, String orgNm, String savedPath) {
		this.id = id;
		this.orgNm = orgNm;
		this.savedPath = savedPath;
	}
	
}
