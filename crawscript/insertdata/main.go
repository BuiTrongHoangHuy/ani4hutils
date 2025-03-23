package main

import (
	"encoding/json"
	"fmt"
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
	"github.com/joho/godotenv"
	"gorm.io/driver/mysql"
	_ "gorm.io/driver/mysql"
	"gorm.io/gorm"
	"gorm.io/gorm/clause"
	"log"
	"os"
)

var fileModelMap = map[string]interface{}{
	"character":           []model.Character{},
	"voice_actor":         []model.VoiceActor{},
	"studio":              []model.Studio{},
	"producer":            []model.Producer{},
	"genre":               []model.Genre{},
	"age_rating":          []model.AgeRating{},
	"character_actor":     []model.FilmCharacterActor{},
	"film_film_character": []model.FilmCharacterActor{},
	"film_studio":         []model.FilmStudio{},
	"film_producer":       []model.FilmProducer{},
	"cleaned":             []model.FilmInsertModel{},
	"alter_titles":        []model.AlternativeTitles{},
	"broadcast":           []model.Broadcast{},
}

func main() {
	//files := []string{    "film_producer"}
	//files := []string{"cleaned"}

	godotenv.Load()
	db, err := gorm.Open(mysql.Open(os.Getenv("DATABASE_URL")), &gorm.Config{})
	if err != nil {
		log.Fatal(err)
	}
	fileName := fmt.Sprintf("../data/film_genre.json")
	data, err := os.ReadFile(fileName)
	var modelPtr []model.FilmGenre
	err = json.Unmarshal(data, &modelPtr)
	if err != nil {
		log.Printf("Error reading %s: %v\n", fileName, err)
	}
	if err := db.Clauses(clause.OnConflict{
		DoNothing: true,
	}).Create(modelPtr).Error; err != nil {
		log.Println(err)
	}
	//for _, file := range files {

	//
	//	modelPtr := fileModelMap[file]
	//	err = json.Unmarshal(data, &modelPtr)
	//	if err != nil {
	//		log.Printf("Error unmarshaling %s: %v\n", fileName, err)
	//		continue
	//	}
	//
	//	result := db.Create(modelPtr).Table("films")
	//	if result.Error != nil {
	//		log.Printf("Error inserting %s: %v\n", fileName, result.Error)
	//		continue
	//	}
	//
	//	log.Printf("Successfully inserted data from %s\n", fileName)
	//}
}
