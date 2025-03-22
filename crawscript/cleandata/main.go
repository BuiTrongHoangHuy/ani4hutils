package main

import (
	"encoding/json"
	"fmt"
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
	"log"
	"os"
	"sort"
)

var studioData = map[string]model.Studio{}
var studioIdCount int = 1

var producerData = map[string]model.Producer{}
var producerIdCount int = 1

var genreData = map[string]model.Genre{}
var genreIdCount int = 1

var ageRatingData = map[string]model.AgeRating{}
var ageRatingIdCount int = 1

var actorData = map[string]model.VoiceActor{}
var actorIdCount int = 1

var characterData = map[string]model.Character{}
var characterIdCount int = 1

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

		character := cleanCharacterData(&films[i])
		films[i].Characters = character

	}
	log.Print(fileName)
	log.Println("total: ", len(films))
	log.Println(characterIdCount)
	model.SaveToJSONFilePretty("../data/cleaned.json", films)
	storeMetaData()
}

func storeMetaData() {
	var studioArray []model.Studio
	for _, v := range studioData {
		studioArray = append(studioArray, v)
	}
	sort.Slice(studioArray, func(i, j int) bool {
		return studioArray[i].Id < studioArray[j].Id
	})
	model.SaveToJSONFilePretty("../data/studio.json", studioArray)

	var producerArray []model.Producer
	for _, v := range producerData {
		producerArray = append(producerArray, v)
	}
	sort.Slice(producerArray, func(i, j int) bool {
		return producerArray[i].Id < producerArray[j].Id
	})
	model.SaveToJSONFilePretty("../data/producer.json", producerArray)

	var genreArray []model.Genre
	for _, v := range genreData {
		genreArray = append(genreArray, v)
	}
	sort.Slice(genreArray, func(i, j int) bool {
		return genreArray[i].Id < genreArray[j].Id
	})
	model.SaveToJSONFilePretty("../data/genre.json", genreArray)

	var ageRatingArray []model.AgeRating
	for _, v := range ageRatingData {
		ageRatingArray = append(ageRatingArray, v)
	}
	sort.Slice(ageRatingArray, func(i, j int) bool {
		return ageRatingArray[i].Id < ageRatingArray[j].Id
	})
	model.SaveToJSONFilePretty("../data/age_rating.json", ageRatingArray)

	var actorArray []model.VoiceActor
	for _, v := range actorData {
		actorArray = append(actorArray, v)
	}
	sort.Slice(actorArray, func(i, j int) bool {
		return actorArray[i].Id < actorArray[j].Id
	})
	model.SaveToJSONFilePretty("../data/voice_actor.json", actorArray)

	var characterArray []model.Character
	for _, v := range characterData {
		characterArray = append(characterArray, v)
	}
	sort.Slice(characterArray, func(i, j int) bool {
		return characterArray[i].Id < characterArray[j].Id
	})
	model.SaveToJSONFilePretty("../data/character.json", characterArray)
}
