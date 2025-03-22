package main

import (
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
	"math/rand"
	"time"
)

var ep = []int{1, 2, 3, 6, 12, 13, 16, 24, 25, 100}

func randomEP() int {
	rand.New(rand.NewSource(time.Now().UnixNano())) // Khởi tạo seed ngẫu nhiên
	p := rand.Float64()                             // Random số từ 0.0 đến 1.0

	if p < 0.5 {
		return 12
	} else if p < 0.6 { // 10% nhỏ hơn 12
		choices := []int{1, 2, 3, 6}
		return choices[rand.Intn(len(choices))]
	} else if p < 0.9 { // 30% nhỏ hơn 25
		choices := []int{13, 16, 24, 25}
		return choices[rand.Intn(len(choices))]
	} else { // 10% còn lại >= 100
		choices := []int{100, 124, 96}
		return choices[rand.Intn(len(choices))]
	}
}

func randomNumber(a int) int {
	rand.New(rand.NewSource(time.Now().UnixNano())) // Khởi tạo seed ngẫu nhiên
	return rand.Intn(a + 1)                         // Random từ 0 đến a
}

func cleanEpisodeData(film *model.Film) (int, int) {
	if film.MaxEpisodes != 0 {
		switch film.State {
		case StateUpcoming:
			return film.MaxEpisodes, 0
		case StateFinished:
			return film.MaxEpisodes, film.MaxEpisodes
		case StateOnAir:
			return film.MaxEpisodes, randomNumber(film.MaxEpisodes)
		}
	}
	maxEp := randomEP()
	switch film.State {
	case StateUpcoming:
		return maxEp, 0
	case StateFinished:
		return maxEp, maxEp
	case StateOnAir:
		return maxEp, randomNumber(film.MaxEpisodes)
	}
	return maxEp, 0
}
