package main

import (
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
)

func cleanProducerData(film *model.Film) []model.Producer {
	var producers []model.Producer
	for _, producerStr := range film.Producers {
		if producerStr == "None found" || producerStr == "add some" {
			continue
		}
		var producer model.Producer
		if s, ok := producerData[producerStr]; ok {
			producer = s
		} else {
			producer = model.Producer{
				Id:          producerIdCount,
				Name:        producerStr,
				Image:       nil,
				Description: "",
			}
			producerData[producerStr] = producer
			producerIdCount++
		}
		producers = append(producers, producer)
	}
	return producers
}
