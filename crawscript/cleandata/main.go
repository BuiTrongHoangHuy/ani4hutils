package main

import (
	"encoding/json"
	"fmt"
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
	"log"
	"os"
)

var studioData map[string]model.Studio = map[string]model.Studio{}
var studioIdCount int = 1

var producerData map[string]model.Producer = map[string]model.Producer{}
var producerIdCount int = 1

func main() {
	var films []model.Film
	fileName := fmt.Sprintf("../data/56000-59599.json")
	plan, _ := os.ReadFile(fileName)
	var data []model.Film
	err := json.Unmarshal(plan, &data)
	if err != nil {
		log.Print(err)
	} else {
		films = append(films, data...)
	}

	for i := range films {
		studios := cleanStudioData(&films[i])
		producers := cleanProducerData(&films[i])
		films[i].StudioObjects = studios
		films[i].ProducerObjets = producers

	}
	log.Print(fileName)
	log.Println("total: ", len(films))
	model.SaveToJSONFilePretty("cleaned.json", films)
}
