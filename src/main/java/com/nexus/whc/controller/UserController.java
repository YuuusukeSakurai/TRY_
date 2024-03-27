package com.nexus.whc.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nexus.whc.services.CommonService;
import com.nexus.whc.services.UserService;

/*
 * UserController.java
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
	// データベース操作クラス
	CommonService commonService;

	@Autowired
	// メッセージ格納クラス
	MessageSource messageSource;

	// ユーザマスタテーブル名
	String userMaster = "m_user";

	// ユーザマスタ一覧(SMSUS001.html)にユーザ情報を表示する上限
	int pageSize = 20;

	// ユーザマスタ一覧(SMSUS001.html)表示
	@GetMapping("/list")
	public String userList(
			@RequestParam(defaultValue = "1") int page,
			Model model) {

		// ユーザ情報を取得(削除されていない)
		List<Map<String, Object>> allUserList = userService.allUserInfoPageNation(page - 1, pageSize);

		// 削除されていないユーザ数を集計
		int totalUser = userService.allUserList().size();
		// ページ数の算出
		int totalPages = (int) Math.ceil((double) totalUser / pageSize);
		int currentPage = page + 1;

		// リクエストスコープに保存
		model.addAttribute("allUserList", allUserList);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		// 一覧モード
		model.addAttribute("mode", "view");
		// ユーザマスタ一覧に遷移
		return "SMSUS001";
	}

	// ユーザ検索(SMSUS001)
	/*@PostMapping("/list")*/
	@PostMapping("/search")
	public String allUserSearch(
			@RequestParam(name = "userId", defaultValue = "") String userId,
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

	// ユーザーマスタ登録画面（SMSUS002.html）表示
	@GetMapping("/regist")
	public String userRegist(
			@RequestParam(name = "seq_id") String seqId,
			RedirectAttributes attr,
			Model model) {
		// エラーメッセージ
		String error = "";
		// リクエストスコープにシークエンスIDを保存
		model.addAttribute("seq_id", seqId);
		// ユーザマスタ一覧(SMSUS001.html)から新規登録ボタン押下
		if (seqId.equals("0")) {
			/*マップの要素が全て空文字のマップを受け取る*/
			Map<String, Object> userMap = userService.searchUser(seqId);

			/*シーケンスIDとマップをスコープに保存*/
			model.addAttribute("seq_id", seqId);
			model.addAttribute("userMap", userMap);
			// ユーザーマスタ登録画面（SMSUS002.html）表示
			return "SMSUS002";
		}

		// 排他チェック（削除）
		String[] seqID = new String[] { seqId };
		List<Map<String, Object>> dataExists = userService.dataExistCheck(seqID);
		System.out.println("排他チェックでデータを確認できた件数は:" + dataExists.size());
		System.out.println(dataExists);

		// 排他チェック（削除）によって該当データが存在しない場合
		if (dataExists.isEmpty()) {
			error = messageSource.getMessage("COM01E005", new String[] {},
					Locale.getDefault());
			// リクエストスコープにエラーメッセージを保存
			attr.addFlashAttribute("message", error);
			// ユーザマスタ一覧(SMSUS001)にリダイレクト
			return "redirect:/user/list";
		}
		// 排他チェック（編集ロック確認）
		List<Map<String, Object>> exclusiveDataCheckList = commonService.editLockCheckList(userMaster, dataExists);
		System.out.println(exclusiveDataCheckList);
		// 他のユーザーが編集の場合（レコードが、ロックテーブルに登録されている場合）
		if (!exclusiveDataCheckList.isEmpty()) {
			// エラーメッセージの格納
			error = messageSource.getMessage("COM01E006", new String[] {}, Locale.getDefault());
			// リクエストスコープにエラーメッセージを保存
			attr.addFlashAttribute("message", error);
			// ユーザマスタ一覧(SMSUS001)にリダイレクト
			return "redirect:/user/list";
		}

		// ユーザーリンクからユーザ情報を取得する
		Map<String, Object> userMap = userService.searchUser(seqId);

		/*引数で受け取ったシーケンスIDのユーザー情報をスコープに保存*/
		model.addAttribute("seq_id", seqId);
		model.addAttribute("userMap", userMap);
		return "SMSUS002";
	}

	// ユーザーマスタ登録画面（SMSUS002）から登録処理
	@PostMapping("/regist")
	public String userRegist(
			@RequestParam(name = "seq_id") String seqId,
			@RequestParam(name = "userId", defaultValue = "") String userId,
			@RequestParam(name = "userName", defaultValue = "") String userName,
			@RequestParam(name = "permission") String authStatus,
			@RequestParam(name = "mailAddress", defaultValue = "") String mailAddress,
			@RequestParam(name = "regist", defaultValue = "") String registButton,
			@RequestParam(name = "nextRegist", defaultValue = "") String nextRegistButton,
			@RequestParam(name = "update", defaultValue = "") String updateButton,
			@RequestParam(name = "cancel", defaultValue = "") String cancelButton,
			Model model,
			RedirectAttributes attr) {

		// エラーメッセージ
		String error = "";

		// キャンセル押下時
		if (cancelButton.equals("cancel")) {
			List<Map<String, Object>> list = new ArrayList<>();
			Map<String, Object> map = new HashMap<>();
			map.put("user_id", userId);
			map.put("seq_id", seqId);
			list.add(map);
			commonService.deleteLockTable(list);
			return "redirect:/user/list";
		}
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
		// エラーメッセージの表示
		if (!error.isEmpty()) {
			attr.addFlashAttribute("message", "COM01E001:" + error + "は必ず入力してください。");
			// 登録or登録して次へ押下してエラーメッセージの表示
			if (registButton.equals("regist") || nextRegistButton.equals("nextRegist")) {
				return "redirect:/user/regist?seq_id=0";
			} else {
				/* シーケンスIDが一致するユーザー情報を取得 */
				Map<String, Object> userMap = userService.searchUser(seqId);

				/*引数で受け取ったシーケンスIDのユーザー情報をスコープに保存*/
				attr.addFlashAttribute("seq_id", seqId);
				attr.addFlashAttribute("userMap", userMap);

				return "redirect:/user/regist?seq_id=" + seqId;
			}
		}

		// メールアドレスのフォーマットチェック
		String mailFormat = "^([a-zA-Z0-9])+([a-zA-Z0-9\\._-])*@nexus-nt.co.jp";
		Pattern formatCheck = Pattern.compile(mailFormat);

		if (!formatCheck.matcher(mailAddress).find()) {
			error = messageSource.getMessage("COM01E003", new String[] { "メールアドレスとして正しいフォーマット", "～@nexus-nt.co.jp" },
					Locale.getDefault());
			attr.addFlashAttribute("message", error);
			if (registButton.equals("regist") || nextRegistButton.equals("nextRegist")) {
				return "redirect:/user/regist?seq_id=0";
			} else {
				/* シーケンスIDが一致するユーザー情報を取得 */
				Map<String, Object> userMap = userService.searchUser(seqId);

				/*引数で受け取ったシーケンスIDのユーザー情報をスコープに保存*/
				attr.addFlashAttribute("seq_id", seqId);
				attr.addFlashAttribute("userMap", userMap);

				return "redirect:/user/regist?seq_id=" + seqId;

			}
		}
		System.out.println("登録ボタン：" + registButton);
		// 新規登録ルート
		if (registButton.equals("regist") || nextRegistButton.equals("nextRegist")) {
			// すでに登録されている場合
			List<Map<String, Object>> userCheckList = userService.allUserList();
			for (Map<String, Object> userCheckMap : userCheckList) {
				String registUserId = userCheckMap.get("user_id").toString();
				String registMailAddress = userCheckMap.get("mail_address").toString();

				// ユーザIDの重複チェック
				if (userId.equals(registUserId)) {
					error = messageSource.getMessage("COM01E011", new String[] { "ユーザIDに入力した", userId, "ユーザマスタ" },
							Locale.getDefault());
					attr.addFlashAttribute("message", error);
					return "redirect:/user/regist?seq_id=0";
				}
				// メールアドレスの重複チェック
				if (mailAddress.equals(registMailAddress)) {
					error = messageSource.getMessage("COM01E011", new String[] { "ユーザIDに入力した", userId, "ユーザマスタ" },
							Locale.getDefault());
					attr.addFlashAttribute("message", error);
					return "redirect:/user/regist?seq_id=0";
				}
			}
			// ユーザマスタに登録されている最大のシークエンスIDを取得する
			Map<String, Object> maxSeqId = userService.maxSeqId();
			// 取得したシークエンスIDに＋1を足す
			int settingSeqId = ((Integer) maxSeqId.get("seq_id")) + 1;

			// パスワードを生成
			String passWord = passWord();

			// ユーザマスタに新規登録
			int result = userService.userRegist(settingSeqId, userId, userName, authStatus, passWord, mailAddress);
			System.out.println("登録件数:" + result);
			// 登録して次へボタン押下時
			if (nextRegistButton.equals("nextRegist")) {
				return "redirect:/user/regist?seq_id=0";
			}
			return "redirect:/user/list";
		}
		if (updateButton.equals("update")) {
			System.out.println("更新処理スタート");
			int result = userService.updateUser(seqId, userId, userName, authStatus, mailAddress);
			System.out.println("更新件数:" + result);
		}
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
		System.out.println("選択されているシークエンスIDは" + Arrays.toString(seqId));

		// 選択しないで削除した場合
		if (seqId.length == 0) {
			errorMessage = messageSource.getMessage("COM01W003", new String[] {},
					Locale.getDefault());
			// リクエストスコープにエラーメッセージを保存
			attr.addFlashAttribute("message", errorMessage);
			return "redirect:/user/list";
		}
		// 排他チェック（削除）
		List<Map<String, Object>> dataExists = commonService.dataExistCheck(userMaster, seqId);
		System.out.println("削除されていないリストは" + dataExists);
		// 排他チェック（削除）によって該当データが存在しない場合
		if (dataExists.isEmpty()) {
			errorMessage = messageSource.getMessage("COM01E005", new String[] {},
					Locale.getDefault());
			// リクエストスコープにエラーメッセージを保存
			attr.addFlashAttribute("message", errorMessage);
			// ユーザマスタ一覧(SMSUS001)にリダイレクト
			return "redirect:/user/list";
		}

		// 排他チェック（編集ロック確認）
		List<Map<String, Object>> exclusiveDataCheckList = commonService.editLockCheckList(userMaster, dataExists);
		// 他のユーザーが編集の場合（レコードが、ロックテーブルに登録されている場合）
		if (!exclusiveDataCheckList.isEmpty()) {
			// エラーメッセージの格納
			errorMessage = messageSource.getMessage("COM01E006", new String[] {}, Locale.getDefault());
			// リクエストスコープにエラーメッセージを保存
			attr.addFlashAttribute("message", errorMessage);
			// ユーザマスタ一覧(SMSUS001)にリダイレクト
			return "redirect:/user/list";
		}
		// 排他チェック（編集ロック登録）
		List<Map<String, Object>> registLockDataList = commonService.registLockTable(userMaster, dataExists);
		System.out.println("排他チェック（編集ロック登録）しているリストは" + registLockDataList);

		// ユーザマスタから選択行削除
		List<Map<String, Object>> deleteUserList = userService.userDelete(registLockDataList);
		System.out.println("削除したユーザーのシークエンスID:" + deleteUserList);

		// 排他チェック（編集ロック解除）
		int deleteLockData = commonService.deleteLockTable(registLockDataList);
		System.out.println("ロックテーブルから削除した件数:" + deleteLockData);

		// ダイアログで「OK」が押された後、ユーザマスタ一覧(SMSUS001)にリダイレクト
		return "redirect:/user/list";
	}

	// パスワード生成するメソッド
	public String passWord() {
		SecureRandom secureRandom = new SecureRandom();
		StringBuilder stringBuilder = new StringBuilder();
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		for (int i = 0; i < 64; i++) {
			int index = secureRandom.nextInt(characters.length());
			stringBuilder.append(characters.charAt(index));
		}
		String passWord = stringBuilder.toString();
		return passWord;
	}

}
