package com.nexus.whc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexus.whc.services.EmployeeService;

/*
 * EmployeeController.java
 * 
 * EmployeeControllerクラス
 */

/*
 * Controllerクラス
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	/* EmployeeServiceクラス*/
	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("/list")
	public String clientList(Model model) {

		//顧客選択ダイアログ用
//		List<Map<String, Object>> clientList = employeeService.getClient();
//		model.addAttribute("client_list", clientList);

		return "SMSEM001";
	}

}
