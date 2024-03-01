package com.nexus.whc.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nexus.whc.Form.UserForm;
import com.nexus.whc.services.UserService;

/*
 * UserController.java
 * 
 * UserControllerクラス
 */

/*
 * Controllerクラス
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	// データベース操作クラス
	UserService userService;

	@Autowired
	// メッセージ格納
	MessageSource messageSource;

	// ユーザの一覧表示
	@GetMapping("/list")
	public String userList(Model model) {

		// ユーザ情報を取得(削除されていない)
		List<Map<String, Object>> allUserList = userService.allUserInfo();

		// リクエストスコープに保存
		model.addAttribute("allUserList", allUserList);

		// ユーザマスタ一覧に遷移
		return "SMSUS001";
	}

	// ユーザ検索
	@PostMapping("/list")
	public String allUserSearch(@RequestParam(name = "userId", defaultValue = "") String userId,
			@RequestParam(name = "userName", defaultValue = "") String userName,
			@RequestParam(name = "permission", defaultValue = "") String authStatus,
			@RequestParam(name = "mailAddress", defaultValue = "") String mailAddress, Model model) {

		// リクエストスコープに検索条件を保存
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("permission", authStatus);
		model.addAttribute("mailAddress", mailAddress);

		// ユーザを検索
		List<Map<String, Object>> allUserList = userService.allUserSearch(userId, userName, authStatus, mailAddress);

		// 検索結果が0件の場合
		if (allUserList.isEmpty()) {
			String errorMessage = messageSource.getMessage("COM01W001", new String[] { "ユーザーマスタ" },
					Locale.getDefault());
			// リクエストスコープにエラーメッセージを保存
			model.addAttribute("message", errorMessage);
			return "SMSUS001";
		}
		// リクエストスコープに検索結果のリストを保存
		model.addAttribute("allUserList", allUserList);

		// ユーザマスタ一覧に遷移
		return "SMSUS001";

	}

	// ユーザーマスタ登録画面（SMSUS002）表示
	@GetMapping("/regist")
	public String userRegist(@RequestParam(name = "seq_id") String seqId,
			Model model) {
		// リクエストスコープにシークエンスIDを保存
		model.addAttribute("seq_id", seqId);
		// ユーザマスタ登録画面に遷移
		return "SMSUS002";
	}

	// ユーザーマスタ登録画面（SMSUS002）から登録処理
	@PostMapping("/regist")
	public String userRegist(@ModelAttribute UserForm userForm, Model model) {
		/*		public String userRegist(@RequestParam(name = "userId", defaultValue = "") String userId,
						@RequestParam(name = "userName", defaultValue = "") String userName,
						@RequestParam(name = "permission", defaultValue = "") String authStatus,
						@RequestParam(name = "mailAddress", defaultValue = "") String mailAddress, Model model) {
		*/
		// Formオブジェクトから各項目を取得
		String userId = userForm.getUserId();
		String userName = userForm.getUserName();
		String authStatus = userForm.getPermission();
		String mailAddress = userForm.getMailAddress();

		System.out.println(userId);
		System.out.println(userName);
		System.out.println(authStatus);
		System.out.println(mailAddress);

		// リクエストスコープに検索条件を保存
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("permission", authStatus);
		model.addAttribute("mailAddress", mailAddress);

		// ユーザマスタに新規登録
		int result = userService.userRegist(userId, userName, authStatus, mailAddress);
		System.out.println("登録件数:" + result);
		return "redirect:/user/list";
	}
}
