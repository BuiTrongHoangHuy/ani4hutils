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
	godotenv.Load()
	var genres []model.Genre
	fileName := fmt.Sprintf("../data/genre.json")
	plan, _ := os.ReadFile(fileName)
	err := json.Unmarshal(plan, &genres)
	db, err := gorm.Open(mysql.Open(os.Getenv("DATABASE_URL")), &gorm.Config{})

	if err != nil {
		log.Fatal(err)
	}
	if err = db.Create(genres).Error; err != nil {
		log.Fatalln(err)
	}
}
