package main

import (
	"fmt"
	"github.com/PuerkitoBio/goquery"
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
	"github.com/gocolly/colly/v2"
	"log"
	"strconv"
	"strings"
	"time"
)

func main() {
	var films []model.Film
	startId := 51000
	n := 3000
	count := 0
	for id := startId; id < startId+n; id++ {
		fmt.Printf("id: %d ", id)
		var film model.Film
		film.Id = id
		filmLink := fmt.Sprintf("https://myanimelist.net/anime/%d/", id)
		characterLink := fmt.Sprintf("https://myanimelist.net/anime/%d/a/characters", id)
		c := colly.NewCollector()
		// get main image
		c.OnHTML(`#content > table > tbody > tr > td.borderClass > div > div:nth-child(1) > a > img`, func(e *colly.HTMLElement) {
			image := e.Attr("data-src")
			film.Images = append(film.Images, image)
		})
		// get title
		c.OnHTML(`h1.title-name.h1_bold_none > strong`, func(e *colly.HTMLElement) {
			film.Title = e.Text
		})
		// get synopsis
		c.OnHTML(`p[itemprop~="description"]`, func(e *colly.HTMLElement) {
			film.Synopsis = e.Text
		})
		// get background
		// get list information
		c.OnHTML(`div.leftside > div.spaceit_pad`, func(e *colly.HTMLElement) {
			spanText := e.ChildText("span:first-child")
			switch spanText {
			case "Synonyms:":
				remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
				film.AlternativeTitlesRaw.Synonyms = append(film.AlternativeTitlesRaw.Synonyms, remainText)
			case "Japanese:":
				remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
				film.AlternativeTitlesRaw.JpName = remainText
			case "Type:":
			case "Episodes:":
				remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
				if v, ok := strconv.ParseInt(remainText, 10, 32); ok == nil {
					film.MaxEpisodes = int(v)
				}
			case "Status:":
				remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
				film.State = remainText
			case "Aired:":
				remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
				film.Aired = remainText
			case "Premiered:":
				film.Season = e.ChildText("a")
			case "Broadcast:":
				remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
				if remainText != "Unknown" {
					// Sundays at 17:00 (JST)
					broadcast := strings.Split(remainText, " ")
					if len(broadcast) == 4 {
						film.Broadcast.DayOfWeek = broadcast[0]
						film.Broadcast.StartTime = broadcast[2]
						film.Broadcast.TimeZone = broadcast[3][1 : len(broadcast[3])-1]
					}
				} else {
					film.Broadcast.DayOfWeek = remainText
				}
			case "Producers:":
				remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
				parts := strings.Split(remainText, ",")
				for i := range parts {
					parts[i] = strings.TrimSpace(parts[i])
				}
				film.Producers = parts
				remainText = strings.Join(parts, ", ")
			case "Studios:":
				remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
				parts := strings.Split(remainText, ",")
				for i := range parts {
					parts[i] = strings.TrimSpace(parts[i])
				}
				film.Studios = parts
				remainText = strings.Join(parts, ", ")
			case "Genres:":
				remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
				parts := strings.Split(remainText, ",")
				for i := range parts {
					parts[i] = strings.TrimSpace(parts[i])
				}
				film.Genres = parts
				remainText = strings.Join(parts, ", ")
			case "Duration:":
				remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
				film.TextAverageEpisodeDuration = remainText
			case "Rating:":
				remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
				film.AgeRating = remainText
			}
		})
		if err := c.Visit(filmLink); err != nil {
			fmt.Print(" Film Not Found  \n")
			time.Sleep(5 * time.Second)
			count++
			if count == 100 {
				count = 0
				if err = model.SaveToJSONFilePretty(fmt.Sprintf("%d-%d.json", id-99, id), films); err != nil {
					return
				}
				films = []model.Film{}
			}
			continue
		}
		// get characters & staff
		c.OnHTML(`div.anime-character-container.js-anime-character-container > table`,
			func(e *colly.HTMLElement) {
				image := e.DOM.Find("div.picSurround > a > img").AttrOr("data-src", "")
				name := e.DOM.Find("h3.h3_character_name").Text()
				role := e.DOM.Find("tbody > tr > td:nth-child(2) > div:nth-child(4)").Text()
				role = strings.TrimSpace(role)
				character := model.Character{
					Name:  name,
					Image: image,
					Role:  role,
				}
				// get voice actor
				e.DOM.Find("tr.js-anime-character-va-lang").Each(func(i int, s *goquery.Selection) {
					imageVoiceActor := s.Find("div.picSurround > a > img").AttrOr("data-src", "")
					nameVoiceActor := s.Find("div.spaceit_pad > a").Text()
					language := s.Find("div.spaceit_pad.js-anime-character-language").Text()
					language = strings.TrimSpace(language)
					voiceActor := model.VoiceActor{
						Name:     nameVoiceActor,
						Language: language,
						Image:    imageVoiceActor,
					}
					character.VoiceActors = append(character.VoiceActors, voiceActor)
				})
				film.Characters = append(film.Characters, character)
			})
		if err := c.Visit(characterLink); err != nil {
			fmt.Print("Character Not Found ")
			time.Sleep(5 * time.Second)
			count++
			if count == 100 {
				count = 0
				if err = model.SaveToJSONFilePretty(fmt.Sprintf("%d-%d.json", id-99, id), films); err != nil {
					continue
				}
				films = []model.Film{}
			}
			continue
		} else {
			time.Sleep(10 * time.Second)
		}
		fmt.Print("Done \n")
		if film.Title != "" {
			films = append(films, film)
		}
		count++
		if count == 100 {
			count = 0
			if err := model.SaveToJSONFilePretty(fmt.Sprintf("%d-%d.json", id-99, id), films); err != nil {
				log.Println("Can't save file with error", err)
			}
			films = []model.Film{}
		}
	}
	time.Sleep(10 * time.Second)
}
