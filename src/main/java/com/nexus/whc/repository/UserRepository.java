package com.nexus.whc.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	// ユーザマスタの情報を取得する
	public List<Map<String, Object>> allUserInfo() {

		// SQL文の作成
		String sql = "SELECT * FROM m_user \n"
				+ "INNER JOIN m_authority\n"
				+ "ON m_user.auth_id = m_authority.auth_id\n"
				+ "WHERE delete_flg = 0 \n"
				+ "ORDER BY user_id ASC";

		// クエリを実行
		List<Map<String, Object>> allUserList = jdbcTemplate.queryForList(sql);

		// 取得したリストを返す
		return allUserList;
	}

	// ユーザマスタ＋権限マスタから検索条件によって情報を取得する
	public List<Map<String, Object>> allUserSearch(String userId, String userName,
			String authStatus, String mailAddress) {

		// SQL文の作成
		String sql = "SELECT * FROM m_user \n"
				+ "INNER JOIN m_authority\n"
				+ "ON m_user.auth_id = m_authority.auth_id\n"
				+ "WHERE (user_id = ? \n"
				+ "OR user_name = ? \n"
				+ "OR m_authority.auth_status = ? \n"
				+ "OR mail_address = ? )\n"
				+ "AND delete_flg = 0 \n"
				+ "ORDER BY user_id ASC";

		// 権限名(authStatus)のパラメータの確認
		String reAuthStatus = "";
		switch (authStatus) {
		case "admin":
			reAuthStatus = "管理者";
			break;
		case "user":
			reAuthStatus = "一般";
			break;
		case "all":
			reAuthStatus = "*";
			break;
		}

		// ？の箇所を置換するデータの配列を定義
		Object[] param = { userId, userName, reAuthStatus, mailAddress };

		// クエリを実行
		List<Map<String, Object>> allUserSearchList = jdbcTemplate.queryForList(sql, param);

		// 取得したリストを返す
		return allUserSearchList;
	}

}
