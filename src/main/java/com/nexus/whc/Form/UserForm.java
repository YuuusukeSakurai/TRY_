package com.nexus.whc.Form;

import javax.validation.constraints.NotBlank;

public class UserForm {

	// ユーザーID
	@NotBlank
	private String userId;

	// ユーザー名
	@NotBlank
	private String userName;

	// 権限
	private String permission;

	// メールアドレス
	@NotBlank
	private String mailAddress;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

}
