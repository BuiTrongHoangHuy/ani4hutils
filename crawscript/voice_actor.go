package main

type VoiceActor struct {
	Id       int    `json:"id"`
	Image    string `json:"image"`
	Name     string `json:"name"`
	Language string `json:"language"`
}

//`id`                        int NOT NULL AUTO_INCREMENT,
//`image`                     JSON,
//`name`                      varchar(50) NOT NULL ,
//`language`                  varchar(50),
