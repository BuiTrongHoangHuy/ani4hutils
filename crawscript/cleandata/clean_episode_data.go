package main

import (
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
)

func cleanEpisodeData(film *model.Film) (int, int) {
	if film.MaxEpisodes != 0 {
		switch film.State {
		case StateUpcoming:
			return film.MaxEpisodes, 0
		case StateFinished:
			return film.MaxEpisodes, film.MaxEpisodes
		case StateOnAir:
		}
	}
	return 0, 0
}
