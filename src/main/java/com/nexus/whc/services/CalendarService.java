package com.nexus.whc.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexus.whc.repository.CalendarRepository;

/*
 * CalendarService.java
 * 
 * CalendarServiceクラス
 */

/*
 * Serviceクラス
 */
@Service
public class CalendarService {

	/* CalendarRepositoryクラス */
	private final CalendarRepository calendarRepository;

	/* CalendarServiceクラス */
	@Autowired
	public CalendarService(CalendarRepository calendarRepository) {
		this.calendarRepository = calendarRepository;
	}

	/**
	 * カレンダー閲覧
	 * 指定されたカレンダー情報を抽出する
	 * @param seq_id
	 * @return 抽出結果のlist
	 */
	public Map<String, Object> searchCalendarData(String seq_id) {
		Map<String, Object> result = calendarRepository.searchCalendarData(seq_id);
		return result;

	}

	/**
	 * カレンダー閲覧
	 * 指定されたカレンダー詳細情報を抽出する
	 * @param seq_id シーケンスID
	 * @return 抽出結果のlist
	 */
	public List<Map<String, Object>> searchCalendarDetails(String seq_id) {
		List<Map<String, Object>> result = calendarRepository.searchCalendarDetails(seq_id);
		return result;
	}

	/**
	 * 顧客選択ダイアログ
	 */
	public List<Map<String, Object>> getClient() {

		List<Map<String, Object>> list = calendarRepository.getClient();

		return list;
	}

	/**
	 * 社員選択ダイアログ用
	 */
	public List<Map<String, Object>> getEmployee() {

		List<Map<String, Object>> list = calendarRepository.getEmployee();

		return list;
	}

}