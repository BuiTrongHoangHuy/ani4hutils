package main

import (
	"encoding/json"
	"fmt"
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
	"image"
	"image/gif"
	"image/jpeg"
	"image/png"
	"log"
	"os"
)

var studioData map[string]model.Studio = map[string]model.Studio{}
var studioIdCount int = 1

var producerData map[string]model.Producer = map[string]model.Producer{}
var producerIdCount int = 1

var genreData map[string]model.Genre = map[string]model.Genre{}
var genreIdCount int = 1

func main() {
	var films []model.Film
	fileName := fmt.Sprintf("../data/all_data.json")
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
		images := cleanImageData(&films[i])
		genres := cleanGenreData(&films[i])
		boardCast := cleanUpBoardCastData(&films[i])
		titles := cleanAlternativeTitleData(&films[i])
		films[i].StudioObjects = studios
		films[i].ProducerObjets = producers
		films[i].ImageObject = images
		films[i].GenreObjects = genres
		films[i].Broadcast = *boardCast
		films[i].AlternativeTitles = titles
	}
	log.Print(fileName)
	log.Println("total: ", len(films))
	for i := range genreData {
		log.Print(i)
	}
	model.SaveToJSONFilePretty("cleaned.json", films)
}

// RegisterImageFormat registers the standard library's image formats.
func RegisterImageFormat() {
	image.RegisterFormat("jpeg", "jpeg", jpeg.Decode, jpeg.DecodeConfig)
	image.RegisterFormat("png", "png", png.Decode, png.DecodeConfig)
	image.RegisterFormat("gif", "gif", gif.Decode, gif.DecodeConfig)
}
