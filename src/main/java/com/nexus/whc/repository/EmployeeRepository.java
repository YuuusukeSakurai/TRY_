package com.nexus.whc.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/*
 * EmployeeRepository.java
 * 
 * EmployeeRepositoryクラス
 */

/*
 * Repositoryクラス
 */
@Repository
public class EmployeeRepository {

	/* JdbcTemplate */
	private final JdbcTemplate jdbcTemplate;

	/* CalendarRepositoryクラス */
	@Autowired
	public EmployeeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 顧客選択ダイアログ
	 */
	public List<Map<String, Object>> getClient() {

		// SQL文作成
		String sql = "SELECT client_id,client_name "
				+ "FROM m_client "
				+ "WHERE delete_flg = 0";

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		return list;
	}

}
