package com.nexus.whc.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommonRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	// データ存在確認(排他チェック（削除）)
	public List<Map<String, Object>> dataExistCheck(String tableName, String[] seqId) {

		// 削除されていないデータを格納する
		List<Map<String, Object>> result = new ArrayList<>();

		// SQL文の作成
		String sql = "SELECT * FROM " + tableName + " WHERE seq_id = ? AND delete_flg = 0";

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
	public List<Map<String, Object>> editLockCheck(String tableName, List<Map<String, Object>> dataExistList) {
		// ロックテーブルに登録されていないデータを登録する
		List<Map<String, Object>> result = new ArrayList<>();

		// SQL文の作成
		String sql = "SELECT * FROM s_lock"
				+ " WHERE s_lock.locking_table_name = ? "
				+ "  AND s_lock.locking_record_id = ?";
		// 選択されたシーケンスID分データが存在するか確認する
		for (Map<String, Object> data : dataExistList) {

			// ?の箇所を置換するデータの配列を定義する
			Object[] param = { tableName, data.get("seq_id") };

			// クエリを実行
			/*result = jdbcTemplate.queryForList(sql, param);*/
			List<Map<String, Object>> exclusiveDataCheckList = jdbcTemplate.queryForList(sql, param);
			// 編集したいレコードがロックテーブルに存在する場合リストに格納
			result.addAll(exclusiveDataCheckList);

		}
		// 実行結果のリストを返す
		return result;
	}

	// TODO ロックテーブルに編集するレコードを登録する。
	public List<Map<String, Object>> registLockTable(String tableName,
			List<Map<String, Object>> exclusiveDataCheckList) {
		// 登録したデータのリスト
		List<Map<String, Object>> resultList = new ArrayList<>();

		// SQL文の作成
		String sql = "INSERT INTO s_lock(locking_table_name, locking_record_id, locking_user_id) "
				+ "VALUES (?, ?, ?)";

		// 登録するデータ分実行する
		for (Map<String, Object> data : exclusiveDataCheckList) {
			// ?の箇所を置換するデータの配列を定義する
			Object[] param = { tableName, data.get("seq_id"), data.get("user_id") };

			// クエリを実行
			int rowsAffected = jdbcTemplate.update(sql, param);

			// 実行結果のデータを作成してリストに追加
			Map<String, Object> resultData = new HashMap<>();
			resultData.put("seq_id", data.get("seq_id"));
			resultData.put("user_id", data.get("user_id"));
			resultList.add(resultData);
		}

		// 登録したデータのリストを返す
		return resultList;
	}

	// 編集済みのレコードをロックテーブルから削除する
	public int deleteLockTable(List<Map<String, Object>> registLockDataList) {
		// 削除した件数
		int result = 0;

		for (Map<String, Object> data : registLockDataList) {
			// SQL文の作成
			String sql = "DELETE FROM s_lock "
					+ "WHERE locking_table_name = ? "
					+ "AND locking_record_id = ? "
					+ "AND locking_user_id = ? ";

			// ?の箇所を置換するデータの配列を定義する
			Object[] params = { "m_user", data.get("seq_id"), data.get("user_id") };

			// クエリを実行
			int rowsAffected = jdbcTemplate.update(sql, params);

			// 各データごとに実行されるので、最後の結果だけでなく、各結果を加算する
			result += rowsAffected;
		}
		// 実行件数を返す
		return result;
	}

}