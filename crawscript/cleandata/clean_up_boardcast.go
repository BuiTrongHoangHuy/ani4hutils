package main

import "github.com/caovanhoang63/ani4hutils/crawscript/model"

func cleanUpBoardCastData(film *model.Film) *model.Broadcast {
	if film.Broadcast.DayOfWeek == "" || film.Broadcast.DayOfWeek == "Unknown" {
		return nil
	}
	return &model.Broadcast{
		StartTime: film.Broadcast.StartTime,
		DayOfWeek: film.Broadcast.DayOfWeek,
		TimeZone:  film.Broadcast.TimeZone,
	}
}
