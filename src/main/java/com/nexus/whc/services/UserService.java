package com.nexus.whc.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexus.whc.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	// ユーザマスタ情報を取得する(削除されていないユーザー)
	public List<Map<String, Object>> allUserInfoPageNation(int pageNumber, int pageSize) {

		/*オフセット数を定義*/
		int offset = pageNumber * pageSize;
		List<Map<String, Object>> allUserPageNationList = userRepository.allUserInfoPageNation(pageSize, offset);

		return allUserPageNationList;
	}

	// ユーザマスタ情報を取得する(削除されていないユーザー)
	public List<Map<String, Object>> allUserList() {

		List<Map<String, Object>> allUserList = userRepository.allUserList();

		return allUserList;
	}

	// ユーザマスタからデータが登録されているか検索する
	public Map<String, Object> userSearch(String userId, String userName, String mailAddress) {

		Map<String, Object> userSearchMap = userRepository.userSearch(userId, userName, mailAddress);

		return userSearchMap;
	}

	/*	// ユーザマスタからデータが登録されているか検索する
		public List<Map<String, Object>> userSearch(String userId, String userName, String mailAddress) {
			
			List<Map<String, Object>> userSearchList = userRepository.userSearch(userId, userName, mailAddress);
			
			return userSearchList;
		}
	*/
	// ユーザマスタ＋権限マスタから検索条件によって情報を取得する
	public List<Map<String, Object>> allUserSearch(String userId, String userName,
			String authStatus, String mailAddress) {

		List<Map<String, Object>> allUserSearchList = userRepository.allUserSearch(userId, userName, authStatus,
				mailAddress);

		return allUserSearchList;
	}

	// ユーザマスタへの新規登録
	public int userRegist(int settingSeqId, String userId, String userName, String password, String authStatus,
			String mailAddress) {
		int result = userRepository.userRegist(settingSeqId, userId, userName, authStatus, password, mailAddress);
		return result;

	}

	// ユーザマスタから選択行削除
	/*	public int userDelete(List<Map<String, Object>> exclusiveDataCheckList) {
			int result = userRepository.userDelete(exclusiveDataCheckList);
			return result;
		}*/
	public List<Map<String, Object>> userDelete(List<Map<String, Object>> registLockDataList) {
		// 削除したデータのリスト
		List<Map<String, Object>> resultList = userRepository.userDelete(registLockDataList);
		// 削除したデータのリストを返す
		return resultList;
	}

	// データ存在確認(排他チェック（削除）)
	public List<Map<String, Object>> dataExistCheck(String[] seqId) {
		List<Map<String, Object>> result = userRepository.dataExistCheck(seqId);
		return result;

	}

	// データがロックテーブルに登録されているか確認（排他チェック（編集ロック確認））
	public List<Map<String, Object>> editLockCheckList(List<Map<String, Object>> dataExistList) {
		List<Map<String, Object>> result = userRepository.editLockCheck(dataExistList);
		return result;
	}

	// ロックテーブルに編集するレコードを登録する。
	public int registLockTable(List<Map<String, Object>> dataExists) {
		int result = userRepository.registLockTable(dataExists);
		return result;
	}

	// ロックテーブルに編集したレコードを削除する。
	public int deleteLockTable(List<Map<String, Object>> dataExists) {
		int result = userRepository.deleteLockTable(dataExists);
		return result;
	}

	// シークエンスIDの最大値を取得する
	public Map<String, Object> maxSeqId() {
		Map<String, Object> maxSeqId = userRepository.maxSeqId();
		return maxSeqId;
	}

	// ユーザ情報取得処理
	public synchronized Map<String, Object> searchUser(String seqId) {
		Map<String, Object> userMap = userRepository.searchUser(seqId);
		return userMap;
	}

	// ユーザ情報更新処理
	public int updateUser(String seqId, String userId, String userName, String authStatus,
			String mailAddress) {
		int result = userRepository.updateUser(seqId, userId, userName, authStatus, mailAddress);
		return result;
	}
}
