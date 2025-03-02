DROP TABLE IF EXISTS `series`;
CREATE TABLE `series`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `name`                      varchar(50) NOT NULL,
    `images`                    JSON,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `anime`
CREATE TABLE `anime`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `title`                     varchar(255) NOT NULL,
    `start_date`                datetime DEFAULT CURRENT_TIMESTAMP,
    `end_date`                  datetime DEFAULT NULL,
    `synopsis`                  TEXT,
    `background`                TEXT,
    `mean`                      decimal(5,2) DEFAULT 0.00,
    `rank`                      int,
    `popularity`                int DEFAULT 0,
    `num_list_users`            int DEFAULT 0,
    `num_scoring_users`         int DEFAULT 0,
    `media_type`                varchar(50),
    `status`                    varchar(50),
    `max_episodes`              int,
    `num_episodes`              int,
    `completed`                 BOOLEAN NOT NULL DEFAULT FALSE,
    `average_episode_duration`  int,
    `source`                    varchar(50),
    `age_rating`                int,
    `images`                    JSON,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    `series_id`                  int,
    PRIMARY KEY (`id`),
    KEY `series_id` (`series_id`) USING BTREE,
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `alternative_titles`;
CREATE TABLE `alternative_titles`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `synonyms`                  varchar(250),
    `en_name`                   varchar(250),
    `ja_name`                   varchar(250),
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    `anime_id`                  int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `anime_id` (`anime_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `genre`;
CREATE TABLE `genre`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `name`                      varchar(50) NOT NULL,
    `status`                    int DEFAULT 1,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `anime_genre`;
CREATE TABLE `anime_genre`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `anime_id`                  int NOT NULL,
    `genre_id`                  int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `anime_id` (`anime_id`) USING BTREE,
    KEY `genre_id` (`genre_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `season`;
CREATE TABLE `season`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `year`                      int,
    `season`                    enum('Spring', 'Summer', 'Fall', 'Winter'),

    `anime_id`                  int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `anime_id` (`anime_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `broadcast`;
CREATE TABLE `broadcast`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `start_time`                time,
    `date_of_week`              enum('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'),

    `anime_id`                  int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `anime_id` (`anime_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `studio`;
CREATE TABLE `studio`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `name`                      varchar(50),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `anime_studio`;
CREATE TABLE `anime_studio`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `anime_id`                  int NOT NULL ,
    `studio_id`                 int NOT NULL ,
    PRIMARY KEY (`id`),
    KEY `anime_id` (`anime_id`) USING BTREE,
    KEY `studio_id` (`studio_id`) USING BTREE,
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `statistic`;
CREATE TABLE `statistic`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `status`                    varchar(50),
    `count_value`               int DEFAULT 0,

    `anime_id`                  int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `anime_id` (`anime_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `anime_character`;
CREATE TABLE `anime_character`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `image`                     JSON,
    `name`                      varchar(50) NOT NULL,
    `role`                      varchar(50) NOT NULL,

    `anime_id`                  int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `anime_id` (`anime_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `voice_actor`;
CREATE TABLE `voice_actor`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `image`                     JSON,
    `name`                      varchar(50) NOT NULL ,
    `language`                  varchar(50),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `anime_character_voice_actor`;
CREATE TABLE `anime_character_voice_actor`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `anime_character_id`        int NOT NULL,
    `voice_actor_id`            int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `anime_character_id` (`anime_character_id`) USING BTREE,
    KEY `voice_actor_id` (`voice_actor_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

