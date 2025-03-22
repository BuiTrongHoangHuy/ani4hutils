package main

import (
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
	"strings"
)

func cleanAgeRatingData(film *model.Film) *model.AgeRating {
	str := film.AgeRating
	if str == "" || str == "None" {
		return nil
	}
	var rating model.AgeRating

	if s, ok := ageRatingData[str]; ok {
		rating = s
	} else {
		names := strings.Split(str, " - ")
		rating = model.AgeRating{
			Id:          ageRatingIdCount,
			LongName:    str,
			ShortName:   names[0],
			Image:       nil,
			Description: "",
		}
		ageRatingData[str] = rating
		ageRatingIdCount++
	}
	return &rating
}
