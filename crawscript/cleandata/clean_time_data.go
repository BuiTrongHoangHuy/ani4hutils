package main

import (
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
	"strings"
	"time"
	"unicode"
)

const layout12 = "Jan 2, 2006"
const layout8 = "Jan 2006"
const layout4 = "2006"

var dateMap = map[int]int{}
var dateMapHandle = map[int]bool{}

func cleanTimeData(film *model.Film) (*time.Time, *time.Time) {

	aired := film.Aired
	if aired == "Not available" || aired == "" {
		return nil, nil
	}
	dateMap[len(aired)]++

	switch len(aired) {
	case 26:
		str := strings.Split(aired, " to ")
		start, _ := time.Parse(layout12, str[0])
		end, _ := time.Parse(layout12, str[1])
		return &start, &end
	case 27:
		str := strings.Split(aired, " to ")
		start, _ := time.Parse(layout12, str[0])
		end, _ := time.Parse(layout12, str[1])
		return &start, &end
	case 28:
		str := strings.Split(aired, " to ")
		start, _ := time.Parse(layout12, str[0])
		end, _ := time.Parse(layout12, str[1])
		return &start, &end
	case 20:
		str := strings.Split(aired, " to ")
		if !unicode.IsDigit(rune(aired[0])) {
			start, err1 := time.Parse(layout8, str[0])
			end, err2 := time.Parse(layout8, str[1])
			if err1 == nil && err2 == nil {
				return &start, &end
			}
		}
		start, err1 := time.Parse(layout12, str[0])
		end, err2 := time.Parse(layout4, str[1])
		if err1 == nil && err2 == nil {
			return &start, &end
		} else {
			start, err1 = time.Parse(layout4, str[0])
			end, err2 = time.Parse(layout12, str[1])
			return &start, &end
		}
	case 19:
		str := strings.Split(aired, " to ")
		start, err1 := time.Parse(layout12, str[0])
		end, err2 := time.Parse(layout4, str[1])
		if err1 == nil && err2 == nil {
			return &start, &end
		} else {
			start, err1 = time.Parse(layout4, str[0])
			end, err2 = time.Parse(layout12, str[1])
			return &start, &end
		}
	case 13:
		str := strings.Split(aired, " to ")
		start, _ := time.Parse(layout8, str[0])
		return &start, nil
	case 12:
		start, err := time.Parse(layout12, aired)
		if err != nil {
			str := strings.Split(aired, " to ")
			start, _ = time.Parse(layout4, str[0])
			end, _ := time.Parse(layout4, str[1])
			return &start, &end
		}
		return &start, nil

	case 11:
		start, _ := time.Parse(layout12, aired)
		return &start, nil
	case 17:
		str := strings.Split(aired, " to ")
		start, _ := time.Parse(layout12, str[0])
		return &start, nil
	case 16:
		str := strings.Split(aired, " to ")
		start, err := time.Parse(layout12, str[0])
		if err != nil {
			str = strings.Split(aired, " to ")
			start, _ = time.Parse(layout8, str[0])
			end, _ := time.Parse(layout4, str[1])
			return &start, &end
		}
		return &start, nil
	case 9:
		str := strings.Split(aired, " to ")
		start, _ := time.Parse(layout4, str[0])
		return &start, nil
	case 8:
		start, _ := time.Parse(layout8, aired)
		return &start, nil

	case 4:
		start, _ := time.Parse(layout4, aired)
		return &start, nil
	}
	dateMapHandle[len(aired)] = true

	return nil, nil
}
