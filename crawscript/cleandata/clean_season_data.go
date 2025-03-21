package main

import (
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
	"strconv"
	"strings"
)

func cleanSeasonData(film *model.Film) (int, string) {
	if film.Season == "" {
		return 0, ""
	}
	data := strings.Split(film.Season, " ")

	year, err := strconv.ParseInt(data[1], 0, 32)
	if err != nil {
		return -1, ""
	}
	return int(year), strings.ToLower(data[0])
}
