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
		String sql = "SELECT "
				+ "m_user.seq_id, "
				+ "m_user.user_id, "
				+ "m_user.user_name, "
				+ "m_user.auth_id, "
				+ "m_user.mail_address, "
				+ "m_authority.auth_status "
				+ "FROM m_user "
				+ "LEFT JOIN m_authority ON m_user.auth_id = m_authority.auth_id "
				+ "WHERE delete_flg = 0 "
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

	// ユーザマスタから選択行削除
	public int userDelete(String[] seqId) {
		// 削除した件数
		int result = 0;

		for (String seq_id : seqId) {
			// SQL文の作成
			String sql = "UPDATE m_user "
					+ "SET delete_flg = 1 "
					+ "WHERE seq_id = ?";

			// ?の箇所を置換するデータの配列を定義する
			Object[] param = { seq_id };

			// クエリを実行
			result += jdbcTemplate.update(sql, param);
		}
		// 実行件数を返す
		return result;
	}

	// データ存在確認(排他チェック（削除）)
	public List<Map<String, Object>> dataExistCheck(String[] seqId) {

		// 削除されていないデータを格納する
		List<Map<String, Object>> result = new ArrayList<>();

		// SQL文の作成
		String sql = "SELECT m_user.seq_id, m_user.user_id  FROM m_user WHERE seq_id = ? AND delete_flg = 0";

		// 選択されたシーケンスID分データが存在するか確認する
		for (String seq_id : seqId) {

			// ?の箇所を置換するデータの配列を定義する
			Object[] param = { seq_id };

			// クエリを実行
			List<Map<String, Object>> dataExistList = jdbcTemplate.queryForList(sql, param);

			// 結果が存在すればリストに追加
			if (!dataExistList.isEmpty()) {
				result.addAll(dataExistList);
			}
		}
		// 実行結果のリストを返す
		return result;
	}

	/*	// 編集中かどうかロックテーブルを確認する
		public List<Map<String, Object>> editLockCheck(List<E>) {
			// ロックテーブルに登録されていないデータを登録する
			List<Map<String, Object>> result = new ArrayList<>();
			
			// SQL文の作成
			String sql = "SELECT * FROM s_lock"
			           + "WHERE s_lock.locking_table_name = 'm_user' "
			           + "  AND s_lock.locking_record_id = ?"
			           + "  AND s_lock.locking_user_id = ?";
	
		}*/
}
