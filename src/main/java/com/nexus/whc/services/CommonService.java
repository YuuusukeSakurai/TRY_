package com.nexus.whc.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexus.whc.repository.CommonRepository;

@Service
public class CommonService {

	@Autowired
	CommonRepository commonRepository;

	// データ存在確認(排他チェック（削除）)
	public List<Map<String, Object>> dataExistCheck(String tableName, String[] seqId) {
		List<Map<String, Object>> result = commonRepository.dataExistCheck(tableName, seqId);
		return result;
	}

	// データがロックテーブルに登録されているか確認（排他チェック（編集ロック確認））
	public List<Map<String, Object>> editLockCheckList(String tableName, List<Map<String, Object>> dataExistList) {
		List<Map<String, Object>> result = commonRepository.editLockCheck(tableName, dataExistList);
		return result;
	}

	// ロックテーブルに編集するレコードを登録する。
	public List<Map<String, Object>> registLockTable(String tableName, List<Map<String, Object>> dataExistList) {
		List<Map<String, Object>> result = commonRepository.registLockTable(tableName, dataExistList);
		return result;
	}

	// 編集済みのレコードをロックテーブルから削除する
	public int deleteLockTable(List<Map<String, Object>> registLockDataList) {
		int result = commonRepository.deleteLockTable(registLockDataList);
		return result;
	}
}