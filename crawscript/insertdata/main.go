package main

import (
	"encoding/json"
	"fmt"
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
	"github.com/joho/godotenv"
	"gorm.io/driver/mysql"
	_ "gorm.io/driver/mysql"
	"gorm.io/gorm"
	"log"
	"os"
)

func main() {
	var films []model.FilmCharacterActor
	fileName := fmt.Sprintf("../data/character_actor.json")
	plan, _ := os.ReadFile(fileName)
	err := json.Unmarshal(plan, &films)
	if err != nil {
		log.Println(err)
	}
	log.Println(len(films))
	godotenv.Load()
	db, err := gorm.Open(mysql.Open(os.Getenv("DATABASE_URL")), &gorm.Config{})
	if err != nil {
		log.Fatal(err)
	}
	if err = db.Create(films).Error; err != nil {
		log.Println(err)
	}
}
