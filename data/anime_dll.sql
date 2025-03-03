DROP TABLE IF EXISTS `series`;
CREATE TABLE `series`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `name`                      varchar(50) NOT NULL,
    `images`                    JSON,
    `status`                    int DEFAULT 1,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `status` (`status`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `producers`;
CREATE TABLE `producers`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `name`                      varchar(50),
    `image`                     JSON,
    `status`                    int DEFAULT 1,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `status` (`status`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `film`;
CREATE TABLE `film`
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
    `state`                     enum('upcoming','now_streaming', 'released', 'discontinued'),
    `max_episodes`              int,
    `num_episodes`              int,
    `completed`                 BOOLEAN NOT NULL DEFAULT FALSE,
    `average_episode_duration`  float,
    `source`                    varchar(50),
    `age_rating_id`             int,
    `images`                    JSON,
    `status`                    int DEFAULT 1,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `series_id`                 int,
    PRIMARY KEY (`id`),
    KEY `state` (`state`) USING BTREE,
    KEY `status` (`status`) USING BTREE,
    KEY `series_id` (`series_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `age_ratings`;
CREATE TABLE `age_ratings`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `name` varchar(50),
    `description` TEXT,
    `status`                    int DEFAULT 1,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `status` (`status`) USING BTREE
)ENGINE = InnoDB
 DEFAULT CHARSET = utf8mb4
 COLLATE = utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `alternative_titles`;
CREATE TABLE `alternative_titles`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `synonyms`                  varchar(250),
    `en_name`                   varchar(250),
    `ja_name`                   varchar(250),
    `status`                    int DEFAULT 1,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    `film_id`                   int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `film_id` (`film_id`) USING BTREE,
    KEY `status` (`status`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `genre`;
CREATE TABLE `genre`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `name`                      varchar(50) NOT NULL,
    `status`                    int DEFAULT 1,
    `description`               TEXT,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `status` (`status`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `film_genre`;
CREATE TABLE `film_genre`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `film_id`                   int NOT NULL,
    `genre_id`                  int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `film_id` (`film_id`) USING BTREE,
    KEY `genre_id` (`genre_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `season`;
CREATE TABLE `season`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `year`                      int,
    `season`                    enum('spring', 'summer', 'fall', 'winter'),
    `status`                    int DEFAULT 1,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    `film_id`                   int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `film_id` (`film_id`) USING BTREE,
    KEY `season` (`season`) USING BTREE,
    KEY `status` (`status`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `broadcast`;
CREATE TABLE `broadcast`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `start_time`                time,
    `date_of_week`              enum('sunday', 'monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday'),
    `status`                    int DEFAULT 1,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    `film_id`                   int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `film_id` (`film_id`) USING BTREE,
    KEY `date_of_week` (`date_of_week`) USING BTREE,
    KEY `status` (`status`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `studio`;
CREATE TABLE `studio`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `name`                      varchar(50),
    `image`                     JSON,
    `status`                    int DEFAULT 1,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `status` (`status`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `film_studio`;
CREATE TABLE `film_studio`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `film_id`                   int NOT NULL ,
    `studio_id`                 int NOT NULL ,
    PRIMARY KEY (`id`),
    KEY `film_id` (`film_id`) USING BTREE,
    KEY `studio_id` (`studio_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `statistic`;
CREATE TABLE `statistic`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `state`                     enum('watching', 'completed', 'rating'),
    `count_value`               int DEFAULT 0,
    `status`                    int DEFAULT 1,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    `film_id`                   int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `film_id` (`film_id`) USING BTREE,
    KEY `state` (`state`) USING BTREE ,
    KEY `status` (`status`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `film_character`;
CREATE TABLE `film_character`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `image`                     JSON,
    `name`                      varchar(50) NOT NULL,
    `role`                      enum('main', 'supporting'),
    `status`                    int DEFAULT 1,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    `film_id`                  int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `film_id` (`film_id`) USING BTREE,
    KEY `role` (`role`) USING BTREE ,
    KEY `status` (`status`) USING BTREE
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
    `status`                    int DEFAULT 1,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `status` (`status`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `film_character_voice_actor`;
CREATE TABLE `film_character_voice_actor`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `film_character_id`         int NOT NULL,
    `voice_actor_id`            int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `film_character_id` (`film_character_id`) USING BTREE,
    KEY `voice_actor_id` (`voice_actor_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `episode`;
CREATE TABLE `episode`
(
    `id`                        int NOT NULL AUTO_INCREMENT,
    `title`                     varchar(50) NOT NULL,
    `episode_number`            int NOT NULL,
    `synopsis`                  TEXT,
    `duration`                  int,
    `thumbnail`                 JSON,
    `video_url`                 TEXT,
    `view_count`                int DEFAULT 0,
    `air_date`                  datetime,
    `state`                     enum('released', 'upcoming'),
    `status`                    int DEFAULT 1,
    `created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    `film_id`                   int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `film_id` (`film_id`) USING BTREE,
    KEY `state` (`state`) USING BTREE,
    KEY `status` (`status`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
