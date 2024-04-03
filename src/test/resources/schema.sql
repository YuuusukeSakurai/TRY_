CREATE TABLE IF NOT EXISTS m_authority
(
   `auth_id` INT auto_increment PRIMARY KEY NOT NULL,
   `auth_status` VARCHAR (10) NOT NULL
);
CREATE TABLE IF NOT EXISTS m_user
(
   `seq_id` INT (11) PRIMARY KEY NOT NULL,
   `user_id` VARCHAR (16) NOT NULL,
   `user_name` VARCHAR (16) NOT NULL,
   `password` VARCHAR (64) NOT NULL,
   `auth_id` INT (11) NOT NULL,
   `mail_address` VARCHAR (254) NOT NULL,
   `delete_flg` BIT (1) NULL DEFAULT NULL,
   `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
   `created_user` VARCHAR (16) NULL DEFAULT NULL,
   `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   `updated_user` VARCHAR (16) NULL DEFAULT NULL,
   UNIQUE INDEX `UQ_USER_ID` (`user_id`)
);
CREATE TABLE IF NOT EXISTS s_lock
(
   `seq_id` INT (11) PRIMARY KEY NOT NULL,
   `locking_table_name` VARCHAR (64) NOT NULL,
   `locking_record_id` INT (11) NOT NULL,
   `locking_user_id` VARCHAR (16) NOT NULL,
   UNIQUE INDEX `UQ_LOCKING_TABLE_NAME_LOCKING_RECORD_ID`
   (
      `locking_table_name`,
      `locking_record_id`
   )
);