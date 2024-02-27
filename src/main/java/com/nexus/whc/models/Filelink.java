package com.nexus.whc.models;

import java.sql.Date;

/*
 * Filelink.java
 *
 * Bean定義(S_FILELINKテーブル)
 */

/*
 * Bean定義
 */
public class Filelink {

	// シーケンスID
	private int seqId;

	// 掲載日付
	private Date publicationDate;

	// タイトル
	private String title;

	// 内容
	private String content;

	// 削除フラグ
	private boolean deleteFlg;

	// レコード作成日付
	private Date createdAt;

	// レコード作成ユーザID
	private String createdUser;

	// レコード最終更新日付
	private Date updatedAt;

	// レコード最終更新ユーザId
	private String updatedUser;

	public Filelink() {
	}

	public Filelink(int seqId, Date publicationDate, String title, String content,
			boolean deleteFlg, Date createdAt, String createdUser, Date updatedAt, String updatedUser) {
		super();
		this.seqId = seqId;
		this.publicationDate = publicationDate;
		this.title = title;
		this.content = content;
		this.deleteFlg = deleteFlg;
		this.createdAt = createdAt;
		this.createdUser = createdUser;
		this.updatedAt = updatedAt;
		this.updatedUser = updatedUser;
	}

	public int getSeqId() {
		return seqId;
	}

	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(boolean deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
}