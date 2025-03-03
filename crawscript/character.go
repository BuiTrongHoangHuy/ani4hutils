package main

type Character struct {
	Id          int          `json:"id"`
	Image       string       `json:"image"`
	Name        string       `json:"name"`
	Role        string       `json:"role"`
	VoiceActors []VoiceActor `json:"voiceActors"`
}

//`id`                        int NOT NULL AUTO_INCREMENT,
//`image`                     JSON,
//`name`                      varchar(50) NOT NULL,
//`role`                      enum('main', 'supporting'),
//`status`                    int DEFAULT 1,
//`created_at`                datetime DEFAULT CURRENT_TIMESTAMP,
//`updated_at`                datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//
//`film_id`                  int NOT NULL,
