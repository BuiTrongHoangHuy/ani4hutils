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
	startId := 55000
	n := 46
	var films []model.Film
	for i := 1; i <= n; i++ {
		fileName := fmt.Sprintf("../data/%d-%d.json", startId, startId+99)
		plan, _ := os.ReadFile(fileName)
		var data []model.Film
		err := json.Unmarshal(plan, &data)
		if err != nil {
			log.Print(err)
		} else {
			films = append(films, data...)
		}
		log.Print(fileName)
		startId += 100
	}
	log.Println("total: ", len(films))
	_, err := gorm.Open(mysql.Open(os.Getenv("DATABASE_URL")))
	if err != nil {
		log.Fatalln(err)
	} else {
		log.Print("Connect database success")
	}
}
