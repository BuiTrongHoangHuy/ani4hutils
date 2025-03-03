package main

import (
	"fmt"
	"strconv"
	"strings"

	"github.com/PuerkitoBio/goquery"
	"github.com/gocolly/colly/v2"
)

func main() {
	// variable c is a collector
	var film Film
	title := ""
	c := colly.NewCollector()
	d := colly.NewCollector()
	// get main image
	c.OnHTML(`#content > table > tbody > tr > td.borderClass > div > div:nth-child(1) > a > img`, func(e *colly.HTMLElement) {
		fmt.Println("main_picture: " + e.Attr("data-src"))
		film.Images = append(film.Images, e.Attr("data-src"))
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
	// get mean
	c.OnHTML(`div.score-label`, func(e *colly.HTMLElement) {
		fmt.Println("mean: " + e.Text)
		film.Mean, _ = strconv.ParseFloat(e.Text, 64)
	})
	// get rank
	c.OnHTML(`span.numbers.ranked > strong`, func(e *colly.HTMLElement) {
		fmt.Println("rank: " + e.Text)
	})
	// get popularity
	c.OnHTML(`span.numbers.popularity > strong`, func(e *colly.HTMLElement) {
		fmt.Println("popularity: " + e.Text)
	})
	// get members or num_list_users
	c.OnHTML(`span.numbers.members > strong`, func(e *colly.HTMLElement) {
		fmt.Println("num_list_users: " + e.Text)
	})
	// get num_scoring_users
	c.OnHTML(`div.fl-l.score`, func(e *colly.HTMLElement) {
		fmt.Println("num_scoring_users: " + e.Attr("data-user"))
	})
	// get list information
	c.OnHTML(`div.leftside > div.spaceit_pad`, func(e *colly.HTMLElement) {
		spanText := e.ChildText("span:first-child")
		switch spanText {
		case "Synonyms:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			fmt.Println("Synonyms: " + remainText)
		case "Japanese:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			fmt.Println("Japanese: " + remainText)
		case "Type:":
			fmt.Println("type: " + e.ChildText("a"))
		case "Episodes:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			fmt.Println("Episodes: " + remainText)
		case "Status:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			fmt.Println("Status: " + remainText)
		case "Aired:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			fmt.Println("Aired: " + remainText)
		case "Premiered:":
			fmt.Println("premiered: " + e.ChildText("a"))
		case "Broadcast:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			fmt.Println("Broadcast: " + remainText)
		case "Producers:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			parts := strings.Split(remainText, ",")
			for i := range parts {
				parts[i] = strings.TrimSpace(parts[i])
			}
			remainText = strings.Join(parts, ", ")
			fmt.Println("Producers: " + remainText)
		case "Licensors:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			parts := strings.Split(remainText, ",")
			for i := range parts {
				parts[i] = strings.TrimSpace(parts[i])
			}
			remainText = strings.Join(parts, ", ")
			fmt.Println("Licensors: " + remainText)
		case "Studios:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			parts := strings.Split(remainText, ",")
			for i := range parts {
				parts[i] = strings.TrimSpace(parts[i])
			}
			remainText = strings.Join(parts, ", ")
			fmt.Println("Studios: " + remainText)
		case "Source:":
			fmt.Println("source: " + e.ChildText("a"))
		case "Genres:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			parts := strings.Split(remainText, ",")
			for i := range parts {
				parts[i] = strings.TrimSpace(parts[i])
			}
			remainText = strings.Join(parts, ", ")
			fmt.Println("Genres: " + remainText)
		case "Duration:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			fmt.Println("Duration: " + remainText)
		case "Rating:":
			remainText := strings.TrimSpace(strings.Replace(e.Text, spanText, "", 1))
			fmt.Println("Rating: " + remainText)
		}
	})

	c.Visit("https://myanimelist.net/anime/58567/")

	// get characters & staff
	d.OnHTML(`div.anime-character-container.js-anime-character-container > table`,
		func(e *colly.HTMLElement) {
			image := e.DOM.Find("div.picSurround > a > img").AttrOr("data-src", "")
			name := e.DOM.Find("h3.h3_character_name").Text()
			role := e.DOM.Find("tbody > tr > td:nth-child(2) > div:nth-child(4)").Text()
			role = strings.TrimSpace(role)
			fmt.Println("name: " + name + " role: " + role + " image: " + image)
			// get voice actor
			e.DOM.Find("tr.js-anime-character-va-lang").Each(func(i int, s *goquery.Selection) {
				image_voiceActor := s.Find("div.picSurround > a > img").AttrOr("data-src", "")
				name_voiceActor := s.Find("div.spaceit_pad > a").Text()
				language := s.Find("div.spaceit_pad.js-anime-character-language").Text()
				language = strings.TrimSpace(language)
				fmt.Println("voice_actor: " + name_voiceActor + "language: " + language + " image: " + image_voiceActor)

			})
		})
	// convert title
	//re := regexp.MustCompile(`[^a-zA-Z0-9\s]`)
	//cleaned := re.ReplaceAllString(title, "")
	//// Thay khoảng trắng giữa các từ thành dấu "_"
	//result := strings.ReplaceAll(cleaned, " ", "_")
	d.Visit("https://myanimelist.net/anime/58567/a/characters")
}
