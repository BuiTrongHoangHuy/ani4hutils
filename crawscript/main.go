package main

import (
	"encoding/json"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"

	"github.com/PuerkitoBio/goquery"
	"github.com/gocolly/colly/v2"
)

type Film struct {
	Id                         int               `json:"id"`
	Title                      string            `json:"title"`
	AlternativeTitles          AlternativeTitles `json:"alternativeTitles"`
	MainImage                  string            `json:"mainImage"`
	Images                     []string          `json:"images"`
	Synopsis                   string            `json:"synopsis"`
	StartDate                  string            `json:"startDate"`
	Aired                      string            `json:"aired"`
	EndDate                    *string           `json:"endDate"` // Sử dụng pointer vì có thể null
	MaxEpisodes                int               `json:"maxEpisodes"`
	NumEpisodes                int               `json:"numEpisodes"`
	Genres                     []string          `json:"genres"`
	AgeRating                  string            `json:"ageRating"`
	Background                 string            `json:"background"`
	State                      string            `json:"state"`
	AverageEpisodeDuration     int               `json:"averageEpisodeDuration"`
	TextAverageEpisodeDuration string            `json:"textAverageEpisodeDuration"`
	Studios                    []string          `json:"studios"`
	Producers                  []string          `json:"producers"`
	Season                     string            `json:"season"`
	Broadcast                  Broadcast         `json:"broadcast"`
	Characters                 []Character       `json:"characters"`
}

type AlternativeTitles struct {
	Synonyms []string `json:"synonyms"`
	EnName   *string  `json:"enName"` // Sử dụng pointer vì có thể null
	JpName   string   `json:"jpName"`
}

type Broadcast struct {
	StartTime string `json:"startTime"`
	DayOfWeek string `json:"dayOfWeek"`
	TimeZone  string `json:"timeZone"`
}

type Character struct {
	Name        string       `json:"name"`
	Role        string       `json:"role"`
	Image       string       `json:"image"`
	VoiceActors []VoiceActor `json:"voiceActors"`
}

type VoiceActor struct {
	Name     string `json:"name"`
	Language string `json:"language"`
	Image    string `json:"image"`
}

func main() {
	id := 58567
	var film Film
	title := ""
	film.Id = id
	filmLink := fmt.Sprintf("https://myanimelist.net/anime/%d/", id)
	characterLink := fmt.Sprintf("https://myanimelist.net/anime/%d/a/characters", id)
	c := colly.NewCollector()
	// get main image
	c.OnHTML(`#content > table > tbody > tr > td.borderClass > div > div:nth-child(1) > a > img`, func(e *colly.HTMLElement) {
		fmt.Println("main_picture: " + e.Attr("data-src"))
	})
	// get title
	c.OnHTML(`h1.title-name.h1_bold_none > strong`, func(e *colly.HTMLElement) {
		title = e.Text
		film.Title = e.Text
		fmt.Println("title: " + title)
	})
	// get synopsis
	c.OnHTML(`p[itemprop~="description"]`, func(e *colly.HTMLElement) {
		film.Synopsis = e.Text
		fmt.Println("synopsis: " + e.Text)
	})
	// get background
	// get list information
	c.OnHTML(`div.leftside > div.spaceit_pad`, func(e *colly.HTMLElement) {
		spanText := e.ChildText("span:first-child")
		switch spanText {
		case "Synonyms:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			fmt.Println("Synonyms: " + remainText)
			film.AlternativeTitles.Synonyms = append(film.AlternativeTitles.Synonyms, remainText)
		case "Japanese:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			film.AlternativeTitles.JpName = remainText
			fmt.Println("Japanese: " + remainText)
		case "Type:":
			fmt.Println("type: " + e.ChildText("a"))
		case "Episodes:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			fmt.Println("Episodes: " + remainText)
			if v, ok := strconv.ParseInt(remainText, 10, 32); ok != nil {
				film.MaxEpisodes = int(v)
			}
		case "Status:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			film.State = remainText
			fmt.Println("Status: " + remainText)
		case "Aired:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			film.Aired = remainText
			fmt.Println("Aired: " + remainText)
		case "Premiered:":
			fmt.Println("premiered: " + e.ChildText("a"))
			film.Season = e.ChildText("a")
		case "Broadcast:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			fmt.Println("Broadcast: " + remainText)
			film.Broadcast.DayOfWeek = remainText
		case "Producers:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			parts := strings.Split(remainText, ",")
			for i := range parts {
				parts[i] = strings.TrimSpace(parts[i])
			}
			film.Producers = parts
			remainText = strings.Join(parts, ", ")
			fmt.Println("Producers: " + remainText)
		case "Studios:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			parts := strings.Split(remainText, ",")
			for i := range parts {
				parts[i] = strings.TrimSpace(parts[i])
			}
			film.Studios = parts
			remainText = strings.Join(parts, ", ")
			fmt.Println("Studios: " + remainText)
		case "Genres:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			parts := strings.Split(remainText, ",")
			for i := range parts {
				parts[i] = strings.TrimSpace(parts[i])
			}
			film.Genres = parts
			remainText = strings.Join(parts, ", ")
			fmt.Println("Genres: " + remainText)
		case "Duration:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			film.TextAverageEpisodeDuration = remainText
			fmt.Println("Duration: " + remainText)
		case "Rating:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			fmt.Println("Rating: " + remainText)
			film.AgeRating = remainText
		}
	})
	err := c.Visit(filmLink)
	if err != nil {
		log.Print(err)
		return
	}
	// get characters & staff
	c.OnHTML(`div.anime-character-container.js-anime-character-container > table`,
		func(e *colly.HTMLElement) {
			image := e.DOM.Find("div.picSurround > a > img").AttrOr("data-src", "")
			name := e.DOM.Find("h3.h3_character_name").Text()
			role := e.DOM.Find("tbody > tr > td:nth-child(2) > div:nth-child(4)").Text()
			role = strings.TrimSpace(role)
			fmt.Println("name: " + name + " role: " + role + " image: " + image)
			character := Character{
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
				voiceActor := VoiceActor{
					Name:     nameVoiceActor,
					Language: language,
					Image:    imageVoiceActor,
				}
				character.VoiceActors = append(character.VoiceActors, voiceActor)
				fmt.Println("voice_actor: " + nameVoiceActor + "language: " + language + " image: " + imageVoiceActor)
			})
			film.Characters = append(film.Characters, character)
		})
	c.Visit(characterLink)
	saveToJSONFilePretty("1.json", film)
}

func saveToJSONFilePretty(filename string, data interface{}) error {
	// Chuyển struct thành JSON với indent
	jsonData, err := json.MarshalIndent(data, "", "  ")
	if err != nil {
		return fmt.Errorf("error marshaling to JSON: %v", err)
	}

	// Ghi vào file
	err = os.WriteFile(filename, jsonData, 0644)
	if err != nil {
		return fmt.Errorf("error writing to file: %v", err)
	}

	fmt.Printf("Đã lưu JSON vào %s với định dạng đẹp\n", filename)
	return nil
}
