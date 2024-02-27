package com.nexus.whc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * ClientController.java
 * 
 * ClientControllerクラス
 */

/*
 * Controllerクラス
 */
@Controller
@RequestMapping("/client")
public class ClientController {
	
	@GetMapping("/list")
	public String clientList(Model model) {

		return "SMSCL001";
	}
}
