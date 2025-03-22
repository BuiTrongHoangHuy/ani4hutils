package main

import (
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
)

func cleanCharacterData(film *model.Film) []model.Character {
	var characters []model.Character
	for _, character := range film.Characters {
		if _, ok := characterData[character.Name+character.Image]; ok {
			character = characterData[character.Name+character.Image]
		} else {
			character.Id = characterIdCount
			characterData[character.Name+character.Image] = character
			characterIdCount++
		}
		for i, actor := range character.VoiceActors {
			if _, ok := actorData[actor.Name+actor.Image]; ok {
				character.VoiceActors[i] = actorData[actor.Name+actor.Image]
				continue
			} else {
				actor.Id = actorIdCount
				actorData[actor.Name+actor.Image] = actor
				character.VoiceActors[i] = actor
				actorIdCount++
			}
		}
		characters = append(characters, character)
	}
	return characters
}
