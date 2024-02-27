package com.nexus.whc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexus.whc.repository.TopRepository;

/*
 * TopService.java
 * 
 * TopServiceクラス
 */

/*
 * Serviceクラス
 */
@Service
public class TopService {

	private final TopRepository topRepository;

	@Autowired
	public TopService(TopRepository topDao) {
		this.topRepository = topDao;
	}
}