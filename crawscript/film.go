package main

import "time"

type Film struct {
	Id                     int         `json:"id"`
	Title                  string      `json:"title"`
	StartDate              *time.Time  `json:"startDate"`
	EndDate                *time.Time  `json:"endDate"`
	Synopsis               string      `json:"synopsis"`
	Background             string      `json:"background"`
	Mean                   float64     `json:"mean"`
	Rank                   int         `json:"rank"`
	Popularity             int         `json:"popularity"`
	NumListUsers           int         `json:"numListUsers"`
	NumScoringUsers        int         `json:"numScoringUsers"`
	MediaType              int         `json:"mediaType"`
	State                  string      `json:"state"`
	MaxEpisodes            int         `json:"maxEpisodes"`
	NumEpisodes            int         `json:"numEpisodes"`
	Completed              bool        `json:"completed"`
	AverageEpisodeDuration int         `json:"averageEpisodeDuration"`
	Source                 string      `json:"source"`
	AgeRating              string      `json:"ageRating"`
	SerialId               int         `json:"serialId"`
	Studio                 Studio      `json:"studio"`
	Images                 []string    `json:"images"`
	Characters             []Character `json:"characters"`
}

//`id`                        int NOT NULL AUTO_INCREMENT,
//`title`                     varchar(255) NOT NULL,
//`start_date`                datetime DEFAULT CURRENT_TIMESTAMP,
//`end_date`                  datetime DEFAULT NULL,
//`synopsis`                  TEXT,
//`background`                TEXT,
//`mean`                      decimal(5,2) DEFAULT 0.00,
//`rank`                      int,
//`popularity`                int DEFAULT 0,
//`num_list_users`            int DEFAULT 0,
//`num_scoring_users`         int DEFAULT 0,
//`media_type`                varchar(50),
//`state`                     enum('upcoming','now_streaming', 'released', 'discontinued'),
//`max_episodes`              int,
//`num_episodes`              int,
//`completed`                 BOOLEAN NOT NULL DEFAULT FALSE,
//`average_episode_duration`  int,
//`source`                    varchar(50),
//`age_rating`                int,
//`images`                    JSON,
//`status`                    int DEFAULT 1,
//`created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
//`updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//
//`series_id`                 int,
