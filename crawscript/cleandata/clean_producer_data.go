package main

import (
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
	"log"
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
			log.Println(producerStr)
		} else {
			producer = model.Producer{
				Id:          studioIdCount,
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
