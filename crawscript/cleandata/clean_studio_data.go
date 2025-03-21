package main

import (
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
)

func cleanStudioData(film *model.Film) []model.Studio {
	var studios []model.Studio
	for _, studio := range film.Studios {
		if studio == "None found" || studio == "add some" {
			continue
		}
		var std model.Studio
		if s, ok := studioData[studio]; ok {
			std = s
		} else {
			std = model.Studio{
				Id:          studioIdCount,
				Name:        studio,
				Image:       nil,
				Description: "",
			}
			studioData[studio] = std
			studioIdCount++
		}
		studios = append(studios, std)
	}
	return studios
}
