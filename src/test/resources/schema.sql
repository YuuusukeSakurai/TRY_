USE `whc_db`;
CREATE TABLE `m_authority`
(
   `auth_id` INT (11) NOT NULL COMMENT '権限ID',
   `auth_status` VARCHAR (10) NOT NULL COMMENT '権限名' COLLATE 'utf8_general_ci',
   PRIMARY KEY (`auth_id`) USING BTREE,
   UNIQUE INDEX `UQ_AUTH_STATUS` (`auth_status`) USING BTREE
)
COMMENT= '権限マスタ' 
COLLATE= 'utf8_general_ci' 
ENGINE= InnoDB;

USE `whc_db`;
CREATE TABLE `m_user` (
	`seq_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'シーケンスID',
	`user_id` VARCHAR(16) NOT NULL COMMENT 'ユーザID' COLLATE 'utf8_general_ci',
	`user_name` VARCHAR(16) NOT NULL COMMENT 'ユーザ名' COLLATE 'utf8_general_ci',
	`password` VARCHAR(64) NOT NULL COMMENT 'パスワード' COLLATE 'utf8_general_ci',
	`auth_id` INT(11) NOT NULL COMMENT '権限ID',
	`mail_address` VARCHAR(254) NOT NULL COMMENT 'メールアドレス' COLLATE 'utf8_general_ci',
	`delete_flg` BIT(1) NULL DEFAULT NULL COMMENT '削除フラグ',
	`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'レコード作成日付',
	`created_user` VARCHAR(16) NULL DEFAULT NULL COMMENT 'レコード作成ユーザID' COLLATE 'utf8_general_ci',
	`updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'レコード最終更新日付',
	`updated_user` VARCHAR(16) NULL DEFAULT NULL COMMENT 'レコード最終更新ユーザID' COLLATE 'utf8_general_ci',
	PRIMARY KEY (`seq_id`) USING BTREE,
	UNIQUE INDEX `UQ_USER_ID` (`user_id`) USING BTREE
)
COMMENT='ユーザマスタ'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

USE `whc_db`;

CREATE TABLE `s_lock` (
	`seq_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'シーケンスID',
	`locking_table_name` VARCHAR(64) NOT NULL COMMENT 'ロック対象テーブル名' COLLATE 'utf8_general_ci',
	`locking_record_id` INT(11) NOT NULL COMMENT 'ロック対象レコードID',
	`locking_user_id` VARCHAR(16) NOT NULL COMMENT 'ロック実施ユーザID' COLLATE 'utf8_general_ci',
	PRIMARY KEY (`seq_id`) USING BTREE,
	UNIQUE INDEX `UQ_LOCKING_TABLE_NAME_LOCKING_RECORD_ID` (`locking_table_name`, `locking_record_id`) USING BTREE
)
COMMENT='ロック'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;
