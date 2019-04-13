CREATE DATABASE IF NOT EXISTS gm_robot_db;
USE gm_robot_db;

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id`          INT(11)     NOT NULL AUTO_INCREMENT,
  `parent_id`   INT(11)              DEFAULT NULL,
  `cat_type`    CHAR(10)    NOT NULL,
  `name`        VARCHAR(60) NOT NULL,
  `icon`        VARCHAR(30)          DEFAULT NULL,
  `description` VARCHAR(400)         DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  KEY `cat_type` (`cat_type`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`                  INT(11)    NOT NULL AUTO_INCREMENT,
  `name`                VARCHAR(10)         DEFAULT NULL,
  `email`               VARCHAR(60)         DEFAULT NULL,
  `mobile`              VARCHAR(20)         DEFAULT NULL,
  `active`              TINYINT(1) NOT NULL DEFAULT 1,
  `entry_datetime`      DATETIME            DEFAULT NULL,
  `last_login_datetime` DATETIME            DEFAULT NULL,
  `last_login_ip`       VARCHAR(16)         DEFAULT NULL,
  `last_login_type`     CHAR(10)            DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `active` (`active`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `id`             INT(11)     NOT NULL AUTO_INCREMENT,
  `user_id`        INT(11)     NOT NULL,
  `login_id`       VARCHAR(20) NOT NULL,
  `role`           VARCHAR(20)          DEFAULT NULL,
  `login_type`     CHAR(10)    NOT NULL,
  `login_pass`     VARCHAR(32)          DEFAULT NULL,
  `entry_datetime` DATETIME             DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `login_id_type` (`login_id`, `login_type`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id`          INT(11)     NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(20) NOT NULL,
  `description` VARCHAR(255)         DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` INT(11) NOT NULL,
  `role_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;

-- //
DROP TABLE IF EXISTS `point`;
CREATE TABLE `point` (
  `id`      INT(11) NOT NULL AUTO_INCREMENT,
  `x_value` DOUBLE(16, 10)   DEFAULT 0,
  `y_value` DOUBLE(16, 10)   DEFAULT 0,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `sensor_operation`;
CREATE TABLE `sensor_operation` (
  `id`          INT(11) NOT NULL AUTO_INCREMENT,
  `sensor_type` VARCHAR(20)      DEFAULT NULL,
  `name`        VARCHAR(200)     DEFAULT NULL,
  `duration`    FLOAT(6, 2)      DEFAULT 0,
  `is_pause`    BOOLEAN          DEFAULT FALSE,
  `loc_id`      INT(11)          DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;

-- //
DROP TABLE IF EXISTS `detect_technics`;
CREATE TABLE `detect_technics` (
  `id`              INT(11)      NOT NULL AUTO_INCREMENT,
  `name`            VARCHAR(100) NOT NULL,
  `track_type`      VARCHAR(20)           DEFAULT NULL,
  `track_width`     FLOAT(6, 2)           DEFAULT NULL,
  `track_gap`       FLOAT(6, 2)           DEFAULT NULL,
  `create_datetime` TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;

-- //
DROP TABLE IF EXISTS `mission`;
CREATE TABLE `mission` (
  `id`               INT(11)      NOT NULL AUTO_INCREMENT,
  `axes_frame_id`    INT(11)      NOT NULL,
  `name`             VARCHAR(100) NOT NULL,
  `type`             CHAR(20)     NOT NULL,
  `vehicle_speed`    FLOAT(8, 4)           DEFAULT 0,
  `x_distance`       FLOAT(8, 4)           DEFAULT 0,
  `y_distance`       FLOAT(8, 4)           DEFAULT 0,
  `ori_point_offset` INT(11)               DEFAULT NULL,
  `track_gap`        INT(11)               DEFAULT NULL,
  `direction`        INT(11)               DEFAULT 0,
  `create_date_time` TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `axes_frame_id` (`axes_frame_id`),
  KEY `name` (`name`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `mission_tracks`;
CREATE TABLE `mission_tracks` (
  `mission_id`        INT(11) NOT NULL,
  `location_point_id` INT(11) NOT NULL,
  PRIMARY KEY (`mission_id`, `location_point_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `mission_control_list`;
CREATE TABLE `mission_control_list` (
  `mission_id`       INT(11) NOT NULL,
  `control_point_id` INT(11) NOT NULL,
  PRIMARY KEY (`mission_id`, `control_point_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `inspec_record`;
CREATE TABLE `inspec_record` (
  `dbId`       INT(11)      NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(200) NOT NULL,
  `id`         INT(11)      NOT NULL,
  `mission_id` INT(11)      NOT NULL,
  `start_time` LONG         NOT NULL,
  `pauseTime`  LONG         NULL,
  `end_time`   LONG                  DEFAULT NULL,
  `comments`   TINYTEXT              DEFAULT NULL,
  PRIMARY KEY (`dbId`),
  KEY `mission_id` (`mission_id`),
  KEY `id` (`id`),
  KEY `name` (`name`)

)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `control_point`;
CREATE TABLE `control_point` (
  `id`          INT(11)  NOT NULL AUTO_INCREMENT,
  `orders`      INT(11)           DEFAULT NULL,
  `is_detect`   BOOLEAN  NOT NULL,
  `location_id` INT(11)  NOT NULL,
  `point_type`  CHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `orders` (`orders`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `record_tracks`;
CREATE TABLE `record_tracks` (
  `record_id`         INT(11) NOT NULL,
  `location_point_id` INT(11) NOT NULL,
  PRIMARY KEY (`record_id`, `location_point_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `mission_action`;
CREATE TABLE `mission_action` (
  `id`     INT(11) NOT NULL AUTO_INCREMENT,
  `action` VARCHAR(50)      DEFAULT NULL,
  `time`   LONG             DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `record_actions`;
CREATE TABLE `record_actions` (
  `record_id` INT(11) NOT NULL,
  `action_id` INT(11) NOT NULL,
  PRIMARY KEY (`record_id`, `action_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;
-- //
DROP TABLE IF EXISTS `axes_frame_define`;
CREATE TABLE `axes_frame_define` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(100) NOT NULL,
  `org_nav_id`  INT(11)               DEFAULT NULL,
  `xdir_nav_id` INT(11)               DEFAULT NULL,
  `ydir_nav_id` INT(11)               DEFAULT NULL,
  `gps_pta_id`  INT(11)               DEFAULT NULL,
  `gps_ptb_id`  INT(11)               DEFAULT NULL,
  `gps_ptc_id`  INT(11)               DEFAULT NULL,

  PRIMARY KEY (`id`),
  KEY `name` (`name`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `robot_location_point`;
CREATE TABLE `robot_location_point` (
  `id`            INT(11)      NOT NULL AUTO_INCREMENT,
  `longitude`     DOUBLE(16, 10)        DEFAULT 0,
  `latitude`      DOUBLE(16, 10)        DEFAULT 0,
  `high`          DOUBLE(16, 10)        DEFAULT 0,
  `longitude_map` DOUBLE(16, 10)        DEFAULT 0,
  `latitude_map`  DOUBLE(16, 10)        DEFAULT 0,
  `high_map`      DOUBLE(16, 10)        DEFAULT 0,
  `local_x`       DOUBLE(16, 10)        DEFAULT 0,
  `local_y`       DOUBLE(16, 10)        DEFAULT 0,
  `local_z`       DOUBLE(16, 10)        DEFAULT 0,
  `time`          LONG                  DEFAULT NULL,
  `track_name`    VARCHAR(100) NULL     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `gps_track`;
CREATE TABLE `gps_track` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(100) NULL     DEFAULT NULL,
  `create_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `detection_image`;
CREATE TABLE `detection_image` (
  `id`            INT(11)        NOT NULL AUTO_INCREMENT,
  `record_id`     INT(11)        NOT NULL,
  `location_id`   INT(11)                 DEFAULT NULL,
  `name`          VARCHAR(200)            DEFAULT NULL,
  `raw_url`       VARCHAR(300)            DEFAULT NULL,
  `processed_url` VARCHAR(300)            DEFAULT NULL,
  `defect_type`   VARCHAR(50)             DEFAULT NULL,
  `block_x`       DOUBLE(20, 12)          DEFAULT NULL,
  `block_y`       DOUBLE(20, 12)          DEFAULT NULL,
  `work_angle`    DOUBLE(20, 12) NULL     DEFAULT NULL,
  `navi_angle`    DOUBLE(20, 12) NULL     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `nav_axes_point`;
CREATE TABLE `nav_axes_point` (
  `id`      INT(11) NOT NULL AUTO_INCREMENT,
  `x_value` DOUBLE(20, 12)   DEFAULT 0,
  `y_value` DOUBLE(20, 12)   DEFAULT 0,
  `z_value` DOUBLE(20, 12)   DEFAULT 0,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `disease`;
CREATE TABLE `disease` (
  `id`                 INT(11)      NOT NULL AUTO_INCREMENT,
  `name`               VARCHAR(500) NOT NULL,
  `record_id`          INT(11)      NOT NULL,
  `source_type`        VARCHAR(20)           DEFAULT NULL,
  `level`              VARCHAR(20)           DEFAULT NULL,
  `width`              FLOAT                 DEFAULT NULL,
  `length`             FLOAT                 DEFAULT NULL,
  `depth`              FLOAT                 DEFAULT NULL,
  `radius`             FLOAT                 DEFAULT NULL,
  `center_point_id`    INT(11)               DEFAULT NULL,
  `area`               FLOAT                 DEFAULT NULL,

  `bit_defect_type`    VARCHAR(20)           DEFAULT NULL,
  `cement_defect_type` VARCHAR(20)           DEFAULT NULL,
  `block_number`       TINYTEXT              DEFAULT NULL,
  `detail`             LONGTEXT              DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `source_type` (`source_type`),
  KEY `level` (`level`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `disease_outline`;
CREATE TABLE `disease_outline` (
  `disease_id`        INT(11) NOT NULL,
  `location_point_id` INT(11) NOT NULL,
  PRIMARY KEY (`disease_id`, `location_point_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `sub_server_relation`;
CREATE TABLE `sub_server_relation` (
  `id`               INT(11) NOT NULL AUTO_INCREMENT,
  `robot_id`         INT(11) NOT NULL,
  `server_id`        INT(11) NOT NULL,
  `sub_server_id`    INT(11)          DEFAULT NULL,
  `meta_object_type` VARCHAR(10)      DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `server_id` (`server_id`),
  KEY `sub_server_id` (`sub_server_id`),
  KEY `robot_id` (`robot_id`),
  KEY `meta_object_type` (`meta_object_type`)


)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `robot_instance`;
CREATE TABLE `robot_instance` (
  `id`                INT(11)        NOT NULL     AUTO_INCREMENT,
  `name`              VARCHAR(200)                DEFAULT NULL,
  `time_stamp`        LONG                        DEFAULT NULL,
  `work_axes_sync_no` INT(11)                     DEFAULT 10000,
  `record_sync_no`    INT(11)                     DEFAULT 10000,
  `mission_sync_no`   INT(11)                     DEFAULT 10000,
  `location_id`       INT(11)                     DEFAULT NULL,
  `heading`           DOUBLE(20, 12) NULL         DEFAULT 0,
  `working_state`     VARCHAR(200)   NULL         DEFAULT NULL,
  `auth_code`         VARCHAR(200)   NULL         DEFAULT NULL,

  PRIMARY KEY (`id`),
  KEY `name` (`name`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;

# Insert roles
INSERT INTO role (id, name, description) VALUES (10000, '普通用户', NULL);
INSERT INTO role (id, name, description) VALUES (10001, '数据管理员', NULL);
INSERT INTO role (id, name, description) VALUES (10002, '系统管理', NULL);
