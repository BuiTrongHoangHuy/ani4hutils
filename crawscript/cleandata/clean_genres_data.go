package main

import (
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
)

func cleanGenreData(film *model.Film) []model.Genre {
	var genres []model.Genre
	for _, genreStr := range film.Genres {
		if genreStr == "None found" || genreStr == "add some" {
			continue
		}
		var genre model.Genre
		if s, ok := genreData[genreStr]; ok {
			genre = s
		} else {
			genre = model.Genre{
				Id:          genreIdCount,
				Name:        genreStr[:len(genreStr)/2],
				Image:       nil,
				Description: "",
			}
			genreData[genreStr] = genre
			genreIdCount++
		}
		genres = append(genres, genre)
	}
	return genres
}
