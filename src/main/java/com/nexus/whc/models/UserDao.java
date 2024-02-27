package com.nexus.whc.models;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	// ユーザマスタの情報を取得する
	public List<Map<String, Object>> allUserInfo() {

		// SQL文の作成
		String sql = "SELECT * FROM m_user WHERE delete_flg = 0 ORDER BY user_id ASC";

		// クエリを実行
		List<Map<String, Object>> allUserList = jdbcTemplate.queryForList(sql);

		// 取得したリストを返す
		return allUserList;
	}
}
