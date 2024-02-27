package com.nexus.whc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * TopController.java
 * 
 * TopControllerクラス
 */

/*
 * Controllerクラス
 */
@Controller
public class TopController {

	@GetMapping("/SCMCM001")
	public String topList(Model model) {

		return "SCMCM001";
	}
}