package main

import (
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
	"strings"
)

func cleanUpBoardCastData(film *model.Film) *model.Broadcast {
	if film.Broadcast.DayOfWeek == "" || film.Broadcast.DayOfWeek == "Unknown" {
		return nil
	}
	return &model.Broadcast{
		FilmId:    film.Id,
		StartTime: film.Broadcast.StartTime,
		DayOfWeek: strings.ToLower(normalizeDayOfWeek(film.Broadcast.DayOfWeek)),
		TimeZone:  film.Broadcast.TimeZone,
	}
}

func normalizeDayOfWeek(day string) string {
	day = strings.ToLower(day)
	if strings.HasSuffix(day, "s") {
		return day[:len(day)-1]
	}
	return day
}
