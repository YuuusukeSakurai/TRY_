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
	public List<Map<String, Object>> allUserInfo() {
		List<Map<String, Object>> allUserList = userRepository.allUserInfo();
		return allUserList;
	}

	// ユーザマスタ＋権限マスタから検索条件によって情報を取得する
	public List<Map<String, Object>> allUserSearch() {
		List<Map<String, Object>> allUserSearchList = userRepository.allUserSearch();
		return allUserSearchList;
	}
}
