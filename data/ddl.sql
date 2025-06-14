CREATE DATABASE ani4h;
USE ani4h;

-- Create tables
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id`            int                            NOT NULL AUTO_INCREMENT,
    `phone_number`  VARCHAR(255) DEFAULT NULL,
    `address`       VARCHAR(255) DEFAULT NULL ,
    `first_name`    VARCHAR(255),
    `last_name`     VARCHAR(255),
    `display_name`  VARCHAR(255),
    `date_of_birth` date,
    `gender`        enum ('male','female','other') NOT NULL DEFAULT 'other',
    `role`          ENUM ('admin','user'),
    `avatar`        JSON DEFAULT NULL,
    `status`        INT                                     DEFAULT 1,
    `created_at`    TIMESTAMP                               DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    TIMESTAMP                               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `status` (`status`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE `user_permission` (
    `id` int NOT NULL  AUTO_INCREMENT,
    `user_id` int NOT NULL,
    `role`          ENUM ('admin','user'),
    `status`     INT                                DEFAULT 1,
    `created_at` datetime                           DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime                           DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_id` (`user_id`)  USING BTREE
)ENGINE = InnoDB
 DEFAULT CHARSET = utf8mb4
 COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `auths`;
CREATE TABLE `auths`
(
    `id`         int                                NOT NULL AUTO_INCREMENT,
    `user_id`    int                                NOT NULL,
    `email`      varchar(255) CHARACTER SET utf8mb4 NOT NULL,
    `salt`       varchar(50) CHARACTER SET utf8mb4  DEFAULT NULL,
    `password`   varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
    `status`     INT                                DEFAULT 1,
    `created_at` datetime                           DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime                           DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`) USING BTREE,
    KEY `user_id` (`user_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
DROP TABLE IF EXISTS `external_auth_providers`;
CREATE TABLE `external_auth_providers`
(
    `id`         int NOT NULL AUTO_INCREMENT,
    `name`       varchar(50),
    `endpoint`   varchar(50) DEFAULT NULL,
    `status`     INT         DEFAULT 1,
    `created_at` datetime    DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `status` (`status`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `external_auths`;
CREATE TABLE `external_auths`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `user_id`                   int NOT NULL,
    `external_auth_provider_id` int NOT NULL,
    `auth_token`                VARCHAR(255),
    `status`                    INT      DEFAULT 1,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`) USING BTREE,
    KEY `external_auth_provider_id` (`external_auth_provider_id`) USING BTREE,
    KEY `status` (`status`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `favorites`;
CREATE TABLE `favorites`
(
    `id`         int NOT NULL AUTO_INCREMENT,
    `user_id`    int NOT NULL,
    `film_id`   int NOT NULL,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`) USING BTREE,
    KEY `anime_id` (`film_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `watch_history`;
CREATE TABLE `watch_history`
(
    `id`         int NOT NULL AUTO_INCREMENT,
    `user_id`    int NOT NULL,
    `episode_id`   int NOT NULL,
    `watched_duration` int NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`) USING BTREE,
    KEY `anime_id` (`episode_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `subscriptions`;
CREATE TABLE `subscriptions`
(
    `id`            int NOT NULL    AUTO_INCREMENT,
    `name`          varchar(50)     DEFAULT NULL,
    `price`         decimal(10,2)   DEFAULT NULL,
    `quality`       ENUM ('Fair','Good','Excellent') DEFAULT 'Fair',
    `resolution`    ENUM('480p','720p','1080p','2K + HDR', '4K + HDR') DEFAULT '480p',
    `max_simultaneous_streams` int DEFAULT 1,
    `status`        INT         DEFAULT 1,
    `created_at`    datetime    DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `user_subscriptions`;
CREATE TABLE `user_subscriptions`
(
    `id`                int NOT NULL AUTO_INCREMENT,
    `user_id`           int NOT NULL,
    `subscription_id`   int NOT NULL,
    `status`            INT         DEFAULT 1,
    `created_at`        datetime    DEFAULT CURRENT_TIMESTAMP,
    `updated_at`        datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`) USING BTREE,
    KEY `subscription_id` (`subscription_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `user_ratings`;
CREATE TABLE `user_ratings`
(
    `id`         int NOT NULL AUTO_INCREMENT,
    `user_id`    int NOT NULL,
    `film_id`    int NOT NULL,
    `rating`     INT NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`) USING BTREE,
    KEY `anime_id` (`film_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;





DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`
(
    `id`         int NOT NULL AUTO_INCREMENT,
    `user_id`    int NOT NULL,
    `film_id`    int NOT NULL,
    `episode_id` int DEFAULT NULL,
    `content`    text NOT NULL,
    `status`     INT DEFAULT 1,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`) USING BTREE,
    KEY `film_id` (`film_id`) USING BTREE,
    KEY `episode_id` (`episode_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

