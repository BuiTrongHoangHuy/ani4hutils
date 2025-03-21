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

var studioData = map[string]model.Studio{}
var studioIdCount int = 1

var producerData = map[string]model.Producer{}
var producerIdCount int = 1

var genreData = map[string]model.Genre{}
var genreIdCount int = 1

var ageRatingData = map[string]model.AgeRating{}
var ageRatingIdCount int = 1

var (
	StateOnAir    = "on_air"
	StateFinished = "finished"
	StateUpcoming = "upcoming"
)

var stateMap = map[string]string{
	"Currently Airing": StateOnAir,
	"Finished Airing":  StateFinished,
	"Not yet aired":    StateUpcoming,
}

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
		films[i].StudioObjects = studios

		producers := cleanProducerData(&films[i])
		films[i].ProducerObjets = producers

		images := cleanImageData(&films[i])
		films[i].ImageObject = images

		genres := cleanGenreData(&films[i])
		films[i].GenreObjects = genres

		boardCast := cleanUpBoardCastData(&films[i])
		if boardCast != nil {
			films[i].Broadcast = *boardCast
		}

		titles := cleanAlternativeTitleData(&films[i])
		films[i].AlternativeTitles = titles

		year, season := cleanSeasonData(&films[i])
		films[i].Season = season
		films[i].Year = year

		ageRating := cleanAgeRatingData(&films[i])
		films[i].AgeRatingObject = ageRating

		films[i].State = stateMap[films[i].State]

		start, end := cleanTimeData(&films[i])
		films[i].StartDate = start
		films[i].EndDate = end

	}
	log.Print(fileName)
	log.Println("total: ", len(films))
	for i, r := range dateMap {
		log.Println(i, r, dateMapHandle[i])
	}
	model.SaveToJSONFilePretty("cleaned.json", films)
}

// RegisterImageFormat registers the standard library's image formats.
func RegisterImageFormat() {
	image.RegisterFormat("jpeg", "jpeg", jpeg.Decode, jpeg.DecodeConfig)
	image.RegisterFormat("png", "png", png.Decode, png.DecodeConfig)
	image.RegisterFormat("gif", "gif", gif.Decode, gif.DecodeConfig)
}
