package com.nexus.whc.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/*
 * CalendarRepository.java
 * 
 * CalendarRepositoryクラス
 */

/*
 * Repositoryクラス
 */
@Repository
public class CalendarRepository {

	/* JdbcTemplate */
	private final JdbcTemplate jdbcTemplate;

	/* CalendarRepositoryクラス */
	@Autowired
	public CalendarRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * カレンダー閲覧
	 * 指定されたカレンダー情報を抽出するSQLを実行する
	 * @param seq_id
	 * @return 抽出結果のlist
	 */
	public Map<String, Object> searchCalendarData(String seq_id) {

		// SQL文作成
		String sql = "SELECT m_client.client_name,m_calendar.employee_id, "
				+ "m_employee.employee_name AS employee_name, "
				+ "m_calendar.year_month,m_calendar.comment "
				+ "FROM m_calendar "
				+ "INNER JOIN m_client ON m_calendar.client_id = m_client.client_id "
				+ "INNER JOIN m_employee ON m_calendar.employee_id = m_employee.employee_id "
				+ "LEFT JOIN m_user AS created_user ON m_calendar.created_user = created_user.user_id "
				+ "LEFT JOIN m_user AS updated_user ON m_calendar.updated_user = updated_user.user_id "
				+ "WHERE m_calendar.seq_id = ?";

		// パラメータを設定
		Object[] param = { seq_id };

		Map<String, Object> list = jdbcTemplate.queryForMap(sql, param);

		return list;
	}

	/**
	 * カレンダー閲覧
	 * 指定されたカレンダー詳細情報を抽出するSQLを実行する
	 * @param seq_id シーケンスID
	 * @return 抽出結果のlist
	 */
	public List<Map<String, Object>> searchCalendarDetails(String seq_id) {

		// SQL文作成
		String sql = "SELECT DATE, holiday_flg, comment FROM m_calendar_detail WHERE parent_seq_id = ? ";

		// パラメータを設定
		Object[] param = { seq_id };

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, param);

		return list;
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

	/**
	 * 社員選択ダイアログ
	 */
	public List<Map<String, Object>> getEmployee() {

		// SQL文作成
		String sql = "SELECT employee_id,employee_name "
				+ "FROM m_employee "
				+ "WHERE delete_flg = 0";

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		return list;
	}
	
	
}