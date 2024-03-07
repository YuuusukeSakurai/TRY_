package com.nexus.whc.controller;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	/*	public String userRegist(@RequestParam(name = "seq_id") String seqId,
				Model model) {*/
	public String userRegist(@ModelAttribute UserForm userForm,
			@RequestParam(name = "seq_id") String seqId,
			Model model) {
		// リクエストスコープにシークエンスIDを保存
		model.addAttribute("seq_id", seqId);
		// ユーザマスタ登録画面に遷移
		return "SMSUS002";
	}

	// ユーザーマスタ登録画面（SMSUS002）から登録処理
	@PostMapping("/regist")
	public String userRegist(@RequestParam(name = "userId", defaultValue = "") String userId,
			@RequestParam(name = "userName", defaultValue = "") String userName,
			@RequestParam(name = "permission", defaultValue = "") String authStatus,
			@RequestParam(name = "mailAddress", defaultValue = "") String mailAddress, Model model,
			RedirectAttributes attr) {

		// エラーメッセージ
		String error = "";
		/*attr.addFlashAttribute("userId", userId);
		attr.addFlashAttribute("userName", userName);
		attr.addFlashAttribute("permission", authStatus);
		attr.addFlashAttribute("mailAddress", mailAddress);*/
		// 未入力項目がある場合
		if (userId.isEmpty()) {
			error += " ユーザID";
		}
		if (userName.isEmpty()) {
			error += " ユーザ名";
		}
		if (mailAddress.isEmpty()) {
			error += " メールアドレス";
		}

		if (!error.isEmpty()) {
			attr.addFlashAttribute("userId", userId);
			attr.addFlashAttribute("userName", userName);
			attr.addFlashAttribute("permission", authStatus);
			attr.addFlashAttribute("mailAddress", mailAddress);
			attr.addFlashAttribute("message", "COM01E001:" + error + "は必ず入力してください。");
			return "redirect:/user/regist?seq_id=0";
		}

		// メールアドレスのフォーマットチェック
		String mailFormat = "^([a-zA-Z0-9])+([a-zA-Z0-9\\._-])*@nexus-nt.co.jp";
		Pattern formatCheck = Pattern.compile(mailFormat);
		if (!formatCheck.matcher(mailAddress).find()) {
			error = messageSource.getMessage("COM01E003", new String[] { "メールアドレスとして正しいフォーマット", "～@nexus-nt.co.jp" },
					Locale.getDefault());
			attr.addFlashAttribute("userId", userId);
			attr.addFlashAttribute("userName", userName);
			attr.addFlashAttribute("permission", authStatus);
			attr.addFlashAttribute("mailAddress", mailAddress);
			attr.addFlashAttribute("message", error);
			return "redirect:/user/regist?seq_id=0";
		}

		// ユーザマスタからデータが登録されているか検索する。
		List<Map<String, Object>> userSearchList = userService.userSearch(userId, userName, mailAddress);

		// TODO 改善
		// すでに登録されている場合
		if (!userSearchList.isEmpty()) {
			String confirmId = userSearchList.get(0).get("user_id").toString();
			String confirmName = userSearchList.get(0).get("user_name").toString();
			String confirmMail = userSearchList.get(0).get("mail_address").toString();

			String item = "";
			String duplicate = "";
			if (confirmId.equals(userId)) {
				item = " ユーザID";
				duplicate = " " + userId;
			}
			if (confirmName.equals(userName)) {
				item += " ユーザ名";
				duplicate += " " + userName;
			}
			if (confirmMail.equals(mailAddress)) {
				item += " メールアドレス";
				duplicate += " " + mailAddress;
			}
			error = messageSource.getMessage("COM01E011",
					new String[] { item, duplicate, "ユーザマスタ" },
					Locale.getDefault());
			attr.addFlashAttribute("userId", userId);
			attr.addFlashAttribute("userName", userName);
			attr.addFlashAttribute("permission", authStatus);
			attr.addFlashAttribute("mailAddress", mailAddress);
			attr.addFlashAttribute("message", error);
			return "redirect:/user/regist?seq_id=0";
		}
		// ユーザマスタに登録されている最大のシークエンスIDを取得する
		Map<String, Object> maxSeqId = userService.maxSeqId();
		// 取得したシークエンスIDに＋1を足す
		int settingSeqId = ((Integer) maxSeqId.get("seq_id")) + 1;

		// パスワードを自動生成する
		SecureRandom secureRandom = new SecureRandom();
		StringBuilder stringBuilder = new StringBuilder();
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		for (int i = 0; i < 64; i++) {
			int index = secureRandom.nextInt(characters.length());
			stringBuilder.append(characters.charAt(index));
		}
		String passWord = stringBuilder.toString();

		// ユーザマスタに新規登録
		int result = userService.userRegist(settingSeqId, userId, userName, authStatus, passWord, mailAddress);
		
		return "redirect:/user/list";
	}

	/*ユーザマスタ一覧(SMSUS001)から選択行削除*/
	@PostMapping("/delete")
	public String userDelete(
			@RequestParam(name = "deleteCheck[]", required = false, defaultValue = "") String[] seqId,
			RedirectAttributes attr) {
		System.out.println("選択行削除処理の開始");

		// エラーメッセージ
		String errorMessage = "";
		System.out.println(Arrays.toString(seqId));

		if (seqId.length == 0) {
			errorMessage = messageSource.getMessage("COM01W003", new String[] {},
					Locale.getDefault());
			// リクエストスコープにエラーメッセージを保存
			attr.addFlashAttribute("message", errorMessage);
			return "redirect:/user/list";
		}
		// 排他チェック（削除）
		List<Map<String, Object>> dataExists = userService.dataExistCheck(seqId);

		System.out.println("排他チェック削除の件数は:" + dataExists.size());

		for (Map<String, Object> list : dataExists) {
			System.out.println("seq_isは:" + list.get("seq_id"));

		}
		// 排他チェック（削除）によって該当データが存在しない場合
		if (dataExists.isEmpty()) {
			errorMessage = messageSource.getMessage("COM01E005", new String[] {},
					Locale.getDefault());
			// リクエストスコープにエラーメッセージを保存
			attr.addFlashAttribute("message", errorMessage);
			// ユーザマスタ一覧(SMSUS001)にリダイレクト
			return "redirect:/user/list";
		}
		System.out.println(dataExists);

		// 排他チェック（編集ロック確認）
		List<Map<String, Object>> exclusiveDataCheckList = userService.editLockCheckList(dataExists);
		/*System.out.println("編集ロックを確認した件数は：" + exclusiveDataCheckList.size());*/
		System.out.println("編集ロックを確認した件数は：" + exclusiveDataCheckList);
		// 他のユーザーが編集の場合（レコードが、ロックテーブルに登録されている場合）
		if (!exclusiveDataCheckList.isEmpty()) {
			/*if (exclusiveDataCheckList.isEmpty()) {*/
			// TODO ユーザー情報の特定
			/*			List<Map<String, Object>> editUserInfo = userService
								.allUserSearch(exclusiveDataCheckList.get(0).get("user_id").toString(), "", "", "");
						System.out.println("こいつが編集中" + editUserInfo);*/
			// エラーメッセージの格納
			errorMessage = messageSource.getMessage("COM01E006", new String[] {},
					Locale.getDefault());
			// リクエストスコープにエラーメッセージを保存
			attr.addFlashAttribute("message", errorMessage);
			// ユーザマスタ一覧(SMSUS001)にリダイレクト
			return "redirect:/user/list";
		}
		// 排他チェック（編集ロック登録）
		int registLockData = userService.registLockTable(dataExists);
		System.out.println("登録件数は:" + registLockData + "件です。");

		// ユーザマスタから選択行削除
		int result = userService.userDelete(dataExists);
		System.out.println("削除件数:" + result);

		// 排他チェック（編集ロック解除）
		int deleteLockData = userService.deleteLockTable(dataExists);
		System.out.println("削除件数:" + deleteLockData);

		// ダイアログで「OK」が押された後、ユーザマスタ一覧(SMSUS001)にリダイレクト
		return "redirect:/user/list";
	}

}
