package com.nexus.whc.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	// ユーザマスタの情報を取得する（ページネーション）
	public List<Map<String, Object>> allUserInfoPageNation(int pageSiza, int offset) {

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
				+ "ORDER BY user_id ASC "
				+ "LIMIT ? "
				+ "OFFSET ?";

		// クエリを実行
		List<Map<String, Object>> allUserPageNationList = jdbcTemplate.queryForList(sql, pageSiza, offset);

		// 取得したリストを返す
		return allUserPageNationList;
	}

	// ユーザマスタの情報を取得する
	public List<Map<String, Object>> allUserList() {

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

	// ユーザマスタからデータが登録されているか検索する
	public Map<String, Object> userSearch(String userId, String userName, String mailAddress) {

		// SQL文の作成
		String sql = "SELECT seq_id, user_id, user_name, mail_address "
				+ "FROM m_user "
				+ "WHERE user_id = ? "
				+ "OR  user_name = ? "
				+ "OR  mail_address = ? ";

		// ？の箇所を置換するデータの配列を定義
		Object[] param = { userId, userName, mailAddress };
		try {
			Map<String, Object> userSearchMap = jdbcTemplate.queryForMap(sql, param);
			return userSearchMap;
		} catch (EmptyResultDataAccessException e) {
			// 登録されていない場合
			return Collections.emptyMap();
		}
	}

	/*	// ユーザマスタからデータが登録されているか検索する
		public List<Map<String, Object>> userSearch(String userId, String userName, String mailAddress) {
			// SQL文の作成
			String sql = "SELECT seq_id, user_id, user_name, mail_address "
					+ "FROM m_user "
					+ "WHERE user_id = ? "
					+ "OR  user_name = ? "
					+ "OR  mail_address = ? ";
			
			// ？の箇所を置換するデータの配列を定義
			Object[] param = { userId, userName, mailAddress };
			
			// クエリを実行
			List<Map<String, Object>> userSearchList = jdbcTemplate.queryForList(sql, param);
			
			// 検索結果を返す
			return userSearchList;
		}
	*/
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
	public int userRegist(int settingSeqId, String userId, String userName, String password, String authStatus,
			String mailAddress) {

		// SQL文の作成
		String sql = "INSERT INTO m_user (seq_id, user_id, user_name, password, auth_id, mail_address, delete_flg) "
				+ "VALUES (?, ?, ?, ?, ?, ?, 0);";

		// 権限が選択されている場合
		if (authStatus.equals("admin")) {
			authStatus = "0";
		} else if (authStatus.equals("user")) {
			authStatus = "1";
		}

		// ？の箇所を置換するデータの配列を定義
		Object[] param = { settingSeqId, userId, userName, password, authStatus, mailAddress };

		// クエリを実行
		int result = jdbcTemplate.update(sql, param);

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

	// 編集中かどうかロックテーブルを確認する
	public List<Map<String, Object>> editLockCheck(List<Map<String, Object>> dataExistList) {
		// ロックテーブルに登録されていないデータを登録する
		List<Map<String, Object>> result = new ArrayList<>();

		// SQL文の作成
		String sql = "SELECT * FROM s_lock"
				+ " WHERE s_lock.locking_table_name = ? "
				+ "  AND s_lock.locking_record_id = ?"
				+ "  AND s_lock.locking_user_id = ?";
		// 選択されたシーケンスID分データが存在するか確認する
		for (Map<String, Object> list : dataExistList) {

			// ?の箇所を置換するデータの配列を定義する
			Object[] param = { "m_user", list.get("seq_id"), list.get("user_id") };

			// クエリを実行
			/*result = jdbcTemplate.queryForList(sql, param);*/
			List<Map<String, Object>> exclusiveDataCheckList = jdbcTemplate.queryForList(sql, param);
			// TODO ロックテーブルにないデータだけリストに格納したい
			result.addAll(exclusiveDataCheckList);

		}
		// 実行結果のリストを返す
		return result;

	}

	// ロックテーブルに編集するレコードを登録する。
	public int registLockTable(List<Map<String, Object>> exclusiveDataCheckList) {
		// 登録した件数
		int result = 0;

		// SQL文の作成
		String sql = "INSERT INTO s_lock(locking_table_name,locking_record_id,locking_user_id) \n"
				+ "VALUES (?,?,?)";
		// 登録するデータ分実行する
		for (Map<String, Object> list : exclusiveDataCheckList) {

			// ?の箇所を置換するデータの配列を定義する
			System.out.println("登録するシークエンスID:" + list.get("seq_id"));
			System.out.println("登録するユーザID:" + list.get("user_id"));
			Object[] param = { "m_user", list.get("seq_id"), list.get("user_id") };

			// クエリを実行
			result = jdbcTemplate.update(sql, param);
		}
		// 実行件数を返す
		return result;
	}

	// ユーザマスタから選択行削除
	public List<Map<String, Object>> userDelete(List<Map<String, Object>> registLockDataList) {
		// 削除したデータのリスト
		List<Map<String, Object>> resultList = new ArrayList<>();

		for (Map<String, Object> data : registLockDataList) {
			// SQL文の作成

			// SQL文の作成
			String sql = "UPDATE m_user "
					+ "SET delete_flg = 1 "
					+ "WHERE seq_id = ?";

			// ?の箇所を置換するデータの配列を定義する
			Object[] params = { data.get("seq_id") };

			// クエリを実行
			int rowsAffected = jdbcTemplate.update(sql, params);
			// 実行結果のデータを作成してリストに追加
			Map<String, Object> resultData = new HashMap<>();
			resultData.put("seq_id", data.get("seq_id"));
			resultList.add(resultData);
		}
		// 削除したデータのリストを返す
		return resultList;
	}

	// ロックテーブルに編集したレコードを削除する
	public int deleteLockTable(List<Map<String, Object>> dataExists) {
		// 削除した件数
		int result = 0;
		for (Map<String, Object> list : dataExists) {
			// SQL文の作成
			String sql = "DELETE FROM s_lock "
					+ "WHERE locking_table_name = ? "
					+ "AND locking_record_id = ? "
					+ "AND locking_user_id = ? ";

			// ?の箇所を置換するデータの配列を定義する
			Object[] param = { "m_user", list.get("seq_id"), list.get("user_id") };

			// クエリを実行
			result = jdbcTemplate.update(sql, param);
		}
		// 実行件数を返す
		return result;
	}

	// シークエンスIDの最大値を取得する
	public Map<String, Object> maxSeqId() {

		// SQL文の作成
		String sql = "SELECT seq_id FROM m_user WHERE seq_id = (SELECT MAX(seq_id) FROM m_user)";

		// クエリの実行
		Map<String, Object> maxSeqId = jdbcTemplate.queryForMap(sql);
		return maxSeqId;
	}

	// ユーザー情報取得処理
	public Map<String, Object> searchUser(String seqId) {

		/*マップ型のインスタンスを生成*/
		Map<String, Object> userMap = new HashMap<>();

		/*新規登録の時はマップの各値に空文字を格納し返す*/
		if (seqId.equals("0")) {
			userMap.put("seq_id", "0");
			userMap.put("user_id", "");
			userMap.put("user_name", "");
			userMap.put("auth_id", "");
			userMap.put("mail_address", "");
			return userMap;
		}

		/*SQL文の作成*/
		String sql = "SELECT * FROM m_user WHERE delete_flg != 1 AND seq_id = ?";

		/*?の箇所を置換するデータの配列を定義*/
		Object[] param = { seqId };

		/*クエリを実行*/
		userMap = jdbcTemplate.queryForMap(sql, param);
		/*取得したマップを返す*/
		return userMap;
	}

	// 更新処理
	public int updateUser(String seqId, String userId, String userName, String authStatus,
			String mailAddress) {
		// SQL文の作成
		String sql = "UPDATE m_user "
				+ "SET user_name = ?, auth_id = ?, mail_address = ? "
				+ "WHERE seq_id = ? AND user_id = ?";

		// 権限が選択されている場合
		if (authStatus.equals("admin")) {
			authStatus = "0";
		} else if (authStatus.equals("user")) {
			authStatus = "1";
		}

		// ？の箇所を置換するデータの配列を定義
		Object[] param = { userName, authStatus, mailAddress, seqId, userId };

		// クエリを実行
		int result = jdbcTemplate.update(sql, param);

		// 実行件数を返す
		return result;
	}
}
