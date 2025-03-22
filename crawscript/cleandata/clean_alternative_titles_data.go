package main

import "github.com/caovanhoang63/ani4hutils/crawscript/model"

func cleanAlternativeTitleData(film *model.Film) *model.AlternativeTitles {
	var titles model.AlternativeTitles
	rawTitle := film.AlternativeTitlesRaw
	if rawTitle.JpName == "" && rawTitle.EnName == nil && len(rawTitle.Synonyms) == 0 {
		return nil
	}
	titles.FilmId = film.Id
	if len(rawTitle.Synonyms) > 0 {
		titles.Synonyms = &rawTitle.Synonyms[0]
	}
	titles.EnName = rawTitle.EnName
	if rawTitle.JpName != "" {
		titles.JpName = &rawTitle.JpName
	}

	return &titles
}
