package main

import (
	"encoding/json"
	"fmt"
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
	"log"
	"os"
)

func main() {
	startId := 50100
	n := 24
	var films []model.Film

	for i := 0; i < n; i++ {
		var data []model.Film
		fileName := fmt.Sprintf("../data/%d-%d.json", startId, startId+99)
		plan, _ := os.ReadFile(fileName)
		err := json.Unmarshal(plan, &data)
		if err != nil {
			log.Println(err)
		}
		films = append(films, data...)
		startId += 100
	}
	var data []model.Film
	fileName := fmt.Sprintf("../data/55000-55999.json")
	plan, _ := os.ReadFile(fileName)
	err := json.Unmarshal(plan, &data)
	if err != nil {
		log.Println(err)
	}
	films = append(films, data...)
	fileName = fmt.Sprintf("../data/56000-59599.json")
	plan, _ = os.ReadFile(fileName)
	err = json.Unmarshal(plan, &data)
	if err != nil {
		log.Println(err)
	}
	films = append(films, data...)
	model.SaveToJSONFilePretty("../data/all_data3.json", films)

}
