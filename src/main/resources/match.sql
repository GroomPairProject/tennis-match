CREATE TABLE `admins` (
	`admin_id`	BIGINT	NOT NULL,
	`username`	VARCHAR(20)	NULL,
	`name`	VARCHAR(20)	NULL,
	`phone`	VARCHAR(20)	NULL,
	`email`	VARCHAR(100)	NULL,
	`is_active`	TINYINT(1)	NULL,
	`password`	VARCHAR(256)	NULL,
	`password_miss`	TINYINT	NULL,
	`is_lock`	TINYINT(1)	NULL,
	`profile_img_url`	TEXT	NULL,
	`role`	VARCHAR(20)	NULL,
	`created_at`	DATETIME	NULL,
	`created_by`	BIGINT	NULL,
	`updated_at`	DATETIME	NULL,
	`updated_by`	BIGINT	NULL
);

CREATE TABLE `stadium` (
	`stadium_id`	BIGINT	NOT NULL,
	`name`	VARCHAR(20)	NOT NULL,
	`region`	VARCHAR(20)	NOT NULL,
	`address`	VARCHAR(30)	NOT NULL,
	`detail_address`	VARCHAR(30)	NULL,
	`place_type`	VARCHAR(30)	NULL,
	`is_indoor`	TINYINT(1)	NULL,
	`out_indoor`	TINYINT(1)	NULL,
	`manager`	VARCHAR(10)	NULL,
	`manager_phone_number`	VARCHAR(15)	NULL,
	`created_at`	DATETIME	NULL,
	`created_by`	VARCHAR(20)	NULL,
	`updated_at`	DATETIME	NULL,
	`updated_by`	VARCHAR(20)	NULL
);

CREATE TABLE `authentication_tokens` (
	`token_id`	BIGINT	NOT NULL,
	`refresh_token`	TEXT	NULL,
	`expired_at`	DATETIME	NULL,
	`created_at`	DATETIME	NULL,
	`created_by`	BIGINT	NULL,
	`updated_at`	DATETIME	NULL,
	`updated_by`	BIGINT	NULL,
	`user_id`	BIGINT	NOT NULL
);

CREATE TABLE `applications` (
	`application_id`	BIGINT	NOT NULL,
	`status`	VARCHAR(25)	NOT NULL,
	`is_active`	TINYINT(1)	NOT NULL	DEFAULT 1,
	`is_team`	TINYINT(1)	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`created_by`	BIGINT	NOT NULL,
	`updated_at`	DATETIME	NULL,
	`updated_by`	BIGINT	NULL,
	`user_id`	BIGINT	NULL,
	`division_id`	BIGINT	NOT NULL
);

CREATE TABLE `admin_logs` (
	`log_id`	BIGINT	NOT NULL,
	`logged_at`	DATETIME	NULL,
	`is_login`	TINYINT(1)	NULL,
	`device`	VARCHAR(255)	NULL,
	`is_valid`	TINYINT(1)	NULL,
	`fail_log`	TEXT	NULL,
	`access_ip`	VARCHAR(15)	NULL,
	`admin_id`	BIGINT	NOT NULL
);

CREATE TABLE `matches` (
	`match_id`	BIGINT	NOT NULL,
	`bracket_id`	BIGINT	NOT NULL,
	`match_order`	INT	NOT NULL,
	`scheduled_time`	DATETIME	NULL,
	`actual_start_time`	DATETIME	NULL,
	`actual_end_time`	DATETIME	NULL,
	`side1_player1_id`	BIGINT	NOT NULL,
	`side1_player2_id`	BIGINT	NULL,
	`side2_player1_id`	BIGINT	NULL,
	`side2_player2_id`	BIGINT	NULL,
	`team1_id`	BIGINT	NULL,
	`team2_id`	BIGINT	NULL,
	`win_player1_id`	BIGINT	NULL,
	`win_player2_id`	BIGINT	NULL,
	`win_team_id`	BIGINT	NULL,
	`side1_games`	INT	NULL,
	`side2_games`	INT	NULL,
	`type`	TINYINT(1)	NOT NULL,
	`status`	TINYINT(1)	NOT NULL,
	`is_active`	TINYINT(1)	NULL,
	`created_at`	DATETIME	NOT NULL,
	`created_by`	BIGINT	NOT NULL,
	`updated_at`	DATETIME	NULL,
	`updated_by`	BIGINT	NULL
);

CREATE TABLE `clubs` (
	`club_id`	BIGINT	NOT NULL,
	`club_name`	VARCHAR(20)	NULL,
	`city`	VARCHAR(255)	NULL,
	`district`	VARCHAR(255)	NULL,
	`address_detail`	VARCHAR(255)	NULL,
	`club_gender`	TINYINT	NULL,
	`category`	VARCHAR(20)	NULL,
	`club_div`	VARCHAR(10)	NULL,
	`representative_name`	VARCHAR(20)	NULL,
	`is_active`	TINYINT(1)	NULL,
	`create_date`	DATE	NULL,
	`created_at`	DATETIME	NULL,
	`created_by`	BIGINT	NULL,
	`updated_at`	DATETIME	NULL,
	`updated_by`	BIGINT	NULL
);

CREATE TABLE `division` (
	`division_id`	BIGINT	NOT NULL,
	`tournament_id`	BIGINT	NOT NULL,
	`name`	VARCHAR(20)	NOT NULL,
	`participants`	INT	NULL,
	`max_participants`	INT	NOT NULL,
	`tournament_type`	TINYINT(1)	NOT NULL,
	`tournament_format`	TINYINT(1)	NOT NULL,
	`status`	TINYINT(1)	NOT NULL,
	`tournament_date`	DATE	NOT NULL
);

CREATE TABLE `club_users` (
	`club_user_id`	BIGINT	NOT NULL,
	`user_id`	BIGINT	NULL,
	`admin_id`	BIGINT	NULL,
	`club_id`	BIGINT	NOT NULL
);

CREATE TABLE `user_logs` (
	`log_id`	BIGINT	NOT NULL,
	`logged_at`	DATETIME	NULL,
	`is_login`	TINYINT(1)	NULL,
	`device`	VARCHAR(255)	NULL,
	`is_valid`	TINYINT(1)	NULL,
	`fail_log`	TINYINT(1)	NULL,
	`access_ip`	VARCHAR(15)	NULL,
	`user_id`	BIGINT	NOT NULL
);

CREATE TABLE `users` (
	`user_id`	BIGINT	NOT NULL,
	`name`	VARCHAR(100)	NULL,
	`birth_date`	DATE	NULL,
	`start_at`	DATETIME	NULL,
	`sex`	TINYINT(1)	NULL,
	`phone`	VARCHAR(20)	NULL,
	`email`	VARCHAR(100)	NULL,
	`is_active`	TINYINT(1)	NULL,
	`password`	VARCHAR(256)	NULL,
	`password_miss`	TINYINT	NULL,
	`is_lock`	TINYINT(1)	NULL,
	`profile_img_url`	TEXT	NULL,
	`created_at`	DATETIME	NULL,
	`updated_at`	DATETIME	NULL,
	`created_by`	BIGINT	NULL,
	`updated_by`	BIGINT	NULL,
	`renewal_at`	DATETIME	NULL
);

CREATE TABLE `team_members` (
	`team_id`	VARCHAR(100)	NOT NULL,
	`user_id`	BIGINT	NOT NULL,
	`is_leader`	TINYINT(1)	NOT NULL,
	`name`	VARCHAR(20)	NOT NULL,
	`application_id`	BIGINT	NOT NULL
);

CREATE TABLE `brackets` (
	`bracket_id`	BIGINT	NOT NULL,
	`court_id`	BIGINT	NOT NULL,
	`division_id`	BIGINT	NOT NULL,
	`round_type`	TINYINT(1)	NOT NULL,
	`round_number`	INT	NOT NULL,
	`match_number`	INT	NULL,
	`tournament_date`	DATE	NULL,
	`status`	TINYINT(1)	NOT NULL,
	`is_active`	TINYINT(1)	NOT NULL	DEFAULT 1,
	`created_at`	DATETIME	NOT NULL,
	`created_by`	BIGINT	NOT NULL,
	`updated_at`	DATETIME	NULL,
	`updated_by`	BIGINT	NULL
);

CREATE TABLE `tournaments` (
	`tournament_id`	BIGINT	NOT NULL,
	`name`	VARCHAR(20)	NULL,
	`application_start_at`	DATETIME	NULL,
	`application_end_at`	DATETIME	NULL,
	`start_date`	DATE	NULL,
	`end_date`	DATE	NULL,
	`entry_fee`	INT	NULL,
	`state`	VARCHAR(20)	NULL,
	`is_active`	TINYINT(1)	NULL,
	`description`	VARCHAR(100)	NULL,
	`rule`	VARCHAR(2000)	NULL,
	`created_at`	DATETIME	NULL,
	`created_by`	VARCHAR(20)	NULL,
	`updated_at`	DATETIME	NULL,
	`updated_by`	VARCHAR(20)	NULL,
	`stadium_id`	BIGINT	NOT NULL
);

CREATE TABLE `admin_sessions` (
	`session_id`	CHAR(64)	NOT NULL,
	`access_id`	VARCHAR(15)	NULL,
	`created_at`	DATETIME	NULL,
	`expires_at`	DATETIME	NULL,
	`admin_id`	BIGINT	NOT NULL
);

CREATE TABLE `courts` (
	`court_id`	BIGINT	NOT NULL,
	`number`	INT	NOT NULL,
	`state`	VARCHAR(20)	NOT NULL,
	`surface_type`	VARCHAR(20)	NOT NULL,
	`stadium_id`	BIGINT	NOT NULL
);

ALTER TABLE `admins` MODIFY COLUMN `admin_id` BIGINT PRIMARY KEY AUTO_INCREMENT;

ALTER TABLE `stadium` ADD CONSTRAINT `PK_STADIUM` PRIMARY KEY (
	`stadium_id`
);

ALTER TABLE `authentication_tokens` ADD CONSTRAINT `PK_AUTHENTICATION_TOKENS` PRIMARY KEY (
	`token_id`
);

ALTER TABLE `applications` ADD CONSTRAINT `PK_APPLICATIONS` PRIMARY KEY (
	`application_id`
);

ALTER TABLE `admin_logs` ADD CONSTRAINT `PK_ADMIN_LOGS` PRIMARY KEY (
	`log_id`
);

ALTER TABLE `matches` ADD CONSTRAINT `PK_MATCHES` PRIMARY KEY (
	`match_id`
);

ALTER TABLE `clubs` ADD CONSTRAINT `PK_CLUBS` PRIMARY KEY (
	`club_id`
);

ALTER TABLE `division` ADD CONSTRAINT `PK_DIVISION` PRIMARY KEY (
	`division_id`
);

ALTER TABLE `club_users` ADD CONSTRAINT `PK_CLUB_USERS` PRIMARY KEY (
	`club_user_id`
);

ALTER TABLE `user_logs` ADD CONSTRAINT `PK_USER_LOGS` PRIMARY KEY (
	`log_id`
);

ALTER TABLE `users` ADD CONSTRAINT `PK_USERS` PRIMARY KEY (
	`user_id`
);

ALTER TABLE `team_members` ADD CONSTRAINT `PK_TEAM_MEMBERS` PRIMARY KEY (
	`team_id`,
	`user_id`
);

ALTER TABLE `brackets` ADD CONSTRAINT `PK_BRACKETS` PRIMARY KEY (
	`bracket_id`
);

ALTER TABLE `tournaments` ADD CONSTRAINT `PK_TOURNAMENTS` PRIMARY KEY (
	`tournament_id`
);

ALTER TABLE `admin_sessions` ADD CONSTRAINT `PK_ADMIN_SESSIONS` PRIMARY KEY (
	`session_id`
);

ALTER TABLE `courts` ADD CONSTRAINT `PK_COURTS` PRIMARY KEY (
	`court_id`
);

ALTER TABLE `authentication_tokens` ADD CONSTRAINT `FK_users_TO_authentication_tokens_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `users` (
	`user_id`
);

ALTER TABLE `applications` ADD CONSTRAINT `FK_users_TO_applications_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `users` (
	`user_id`
);

ALTER TABLE `applications` ADD CONSTRAINT `FK_division_TO_applications_1` FOREIGN KEY (
	`division_id`
)
REFERENCES `division` (
	`division_id`
);

ALTER TABLE `admin_logs` ADD CONSTRAINT `FK_admins_TO_admin_logs_1` FOREIGN KEY (
	`admin_id`
)
REFERENCES `admins` (
	`admin_id`
);

ALTER TABLE `matches` ADD CONSTRAINT `FK_brackets_TO_matches_1` FOREIGN KEY (
	`bracket_id`
)
REFERENCES `brackets` (
	`bracket_id`
);

ALTER TABLE `division` ADD CONSTRAINT `FK_tournaments_TO_division_1` FOREIGN KEY (
	`tournament_id`
)
REFERENCES `tournaments` (
	`tournament_id`
);

ALTER TABLE `club_users` ADD CONSTRAINT `FK_users_TO_club_users_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `users` (
	`user_id`
);

ALTER TABLE `club_users` ADD CONSTRAINT `FK_admins_TO_club_users_1` FOREIGN KEY (
	`admin_id`
)
REFERENCES `admins` (
	`admin_id`
);

ALTER TABLE `club_users` ADD CONSTRAINT `FK_clubs_TO_club_users_1` FOREIGN KEY (
	`club_id`
)
REFERENCES `clubs` (
	`club_id`
);

ALTER TABLE `user_logs` ADD CONSTRAINT `FK_users_TO_user_logs_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `users` (
	`user_id`
);

ALTER TABLE `team_members` ADD CONSTRAINT `FK_users_TO_team_members_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `users` (
	`user_id`
);

ALTER TABLE `team_members` ADD CONSTRAINT `FK_applications_TO_team_members_1` FOREIGN KEY (
	`application_id`
)
REFERENCES `applications` (
	`application_id`
);

ALTER TABLE `brackets` ADD CONSTRAINT `FK_courts_TO_brackets_1` FOREIGN KEY (
	`court_id`
)
REFERENCES `courts` (
	`court_id`
);

ALTER TABLE `brackets` ADD CONSTRAINT `FK_division_TO_brackets_1` FOREIGN KEY (
	`division_id`
)
REFERENCES `division` (
	`division_id`
);

ALTER TABLE `tournaments` ADD CONSTRAINT `FK_stadium_TO_tournaments_1` FOREIGN KEY (
	`stadium_id`
)
REFERENCES `stadium` (
	`stadium_id`
);

ALTER TABLE `admin_sessions` ADD CONSTRAINT `FK_admins_TO_admin_sessions_1` FOREIGN KEY (
	`admin_id`
)
REFERENCES `admins` (
	`admin_id`
);

ALTER TABLE `courts` ADD CONSTRAINT `FK_stadium_TO_courts_1` FOREIGN KEY (
	`stadium_id`
)
REFERENCES `stadium` (
	`stadium_id`
);

