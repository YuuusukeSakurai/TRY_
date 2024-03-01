package com.nexus.whc.repository;

import java.util.ArrayList;
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
		// TODO 改善点

		// SQL文の作成
		String sql = "SELECT * FROM m_user \n"
				+ "INNER JOIN m_authority \n"
				+ "ON m_user.auth_id = m_authority.auth_id\n"
				+ "WHERE  delete_flg = 0 ";

		// 入力された検索内容を格納する
		List<Object> paramList = new ArrayList<Object>();

		// ユーザIDが存在する場合
		if (!userId.isEmpty()) {
			sql += "AND user_id LIKE ? \n";
			paramList.add("%" + userId + "%");
		}
		// ユーザ名が存在する場合
		if (!userName.isEmpty()) {
			sql += "AND user_name LIKE ? \n";
			paramList.add("%" + userName + "%");
		}
		// 権限が選択されている場合
		if (authStatus.equals("admin")) {
			sql += "AND auth_status = ?";
			authStatus = "管理者";
			paramList.add(authStatus);
		} else if (authStatus.equals("user")) {
			sql += "AND auth_status = ?";
			authStatus = "一般";
			paramList.add(authStatus);
		}

		// メールアドレスが存在する場合
		if (!mailAddress.isEmpty()) {
			sql += "AND mail_address LIKE ?";
			paramList.add("%" + mailAddress + "%");
		}
		// ユーザIDで昇順並び替え
		sql += "ORDER BY user_id ASC;";

		// ？の箇所を置換するデータの配列を定義
		Object[] param = paramList.toArray();
		// クエリを実行
		List<Map<String, Object>> allUserSearchList = jdbcTemplate.queryForList(sql, param);

		// 取得したリストを返す
		return allUserSearchList;
	}

	// ユーザマスタへの新規登録
	public int userRegist(String userId, String userName, String authStatus, String mailAddress) {

		// SQL文の作成
		String sql = "INSERT INTO m_user (user_id, user_name, PASSWORD, auth_id, mail_address, delete_flg) \n"
				+ "VALUES(? , ?,\"77rgzxvbulow9yvlttyvng7zbq9ydtlndpkj2jg05r64iiyned7nnwdcl9hqpn76\","
				+ " ?, ?, 0)";
		// 権限が選択されている場合
		if (authStatus.equals("admin")) {
			authStatus = "0";
		} else if (authStatus.equals("user")) {
			authStatus = "1";
		}

		// ？の箇所を置換するデータの配列を定義
		Object[] param = { userId, userName, authStatus, mailAddress };

		// クエリを実行
		int result = jdbcTemplate.update(sql, param);

		// 実行件数を返す
		return result;

	}
}
