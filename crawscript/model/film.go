package model

type Film struct {
	Id                         int               `json:"id" gorm:"column:id"`
	Title                      string            `json:"title" gorm:"column:title"`
	AlternativeTitles          AlternativeTitles `json:"alternativeTitles"`
	MainImage                  string            `json:"mainImage"`
	Images                     []string          `json:"images"`
	Synopsis                   string            `json:"synopsis"`
	StartDate                  string            `json:"startDate"`
	Aired                      string            `json:"aired"`
	EndDate                    *string           `json:"endDate"`
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

func (f *Film) TableName() string {
	return "film"
}

type AlternativeTitles struct {
	Synonyms []string `json:"synonyms"`
	EnName   *string  `json:"enName"`
	JpName   string   `json:"jpName"`
}

func (f *AlternativeTitles) TableName() string {
	return "alternative_titles"
}

type Broadcast struct {
	StartTime string `json:"startTime"`
	DayOfWeek string `json:"dayOfWeek"`
	TimeZone  string `json:"timeZone"`
}

func (f *Broadcast) TableName() string {
	return "broad_cast"
}

type Character struct {
	Name        string       `json:"name"`
	Role        string       `json:"role"`
	Image       string       `json:"image"`
	VoiceActors []VoiceActor `json:"voiceActors"`
}

func (c *Character) TableName() string {
	return "film_character"
}

type VoiceActor struct {
	Name     string `json:"name"`
	Language string `json:"language"`
	Image    string `json:"image"`
}

func (v *VoiceActor) TableName() string {
	return "voice_actor"
}
