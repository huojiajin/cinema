CREATE TABLE `cinema_role`
(
    `id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `insert_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    `name`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `cinema_id`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `info`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `list`        int(11)                                                 NOT NULL,
    `has_delete`  bit(1)                                                  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE `cinema_user`
(
    `id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `insert_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    `status`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `role_id`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `name`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `mobile`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `login_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `password`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `cinema_user_index` (`login_name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `cinema_role_resource`
(
    `id`            varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `role_id`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `resource_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `insert_time`   datetime DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY `cinema_role_resource_index` (`role_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `cinema_system_info`
(
    `id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `user_id`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `info`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `insert_time` datetime                                                DEFAULT NULL,
    `eigen_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `cinema`
(
    `id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `insert_time` datetime                                                DEFAULT NULL,
    `update_time` datetime                                                DEFAULT NULL,
    `parent_id`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `name`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `code`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `type`        varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `pos_type`    varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `customer_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `info`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `list`        int(11)                                                 NOT NULL,
    `has_delete`  bit(1)                                                  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY `cinema_index` (`parent_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `cinema_customer`
(
    `id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `insert_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    `name`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;




CREATE TABLE `cinema_advice_material`
(
    `id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `insert_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    `name`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `material_type`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `upload`       bit(1) NOT NULL,
    `thumbnail`   longtext ,
    `file_path`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci ,
    `remark`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci ,
    `operator_id`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `has_delete`  bit(1)                                                  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `cinema_advice_schedule`
(
    `id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `insert_time` datetime DEFAULT NULL,
    `update_time` datetime DEFAULT NULL,
    `name`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `position_type`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `material_type`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `start_time` DATE DEFAULT NULL,
    `end_time` DATE DEFAULT NULL,
    `operator_id`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `operator_name`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `has_delete`  bit(1)                                                  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `cinema_advice_schedule_cinema`
(
    `id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `insert_time` datetime DEFAULT NULL,
    `schedule_id`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `cinema_id`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `cinema_name`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY `cinema_advice_schedule_cinema_index` (`schedule_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `cinema_advice_schedule_material`
(
    `id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `insert_time` datetime DEFAULT NULL,
    `schedule_id`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `material_id`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `material_name`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `list`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY `cinema_advice_schedule_material_index` (`schedule_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;