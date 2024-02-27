package com.nexus.whc.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/*
 * TopRepository.java
 * 
 * TopRepositoryクラス
 */

/*
 * Repositoryクラス
 */
@Repository
public class TopRepository {
	
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public TopRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}