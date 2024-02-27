package com.nexus.whc.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexus.whc.repository.EmployeeRepository;

/*
 * Serviceクラス
 */
@Service
public class EmployeeService {
	/* EmployeeRepositoryクラス */
	private final EmployeeRepository employeeRepository;

	/* EmployeeServiceクラス */
	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}


	/**
	 * 顧客選択ダイアログ
	 */
	public List<Map<String, Object>> getClient() {

		List<Map<String, Object>> list = employeeRepository.getClient();

		return list;
	}
	
}
