package model

import "time"

type FilmInsertModel struct {
	Id                     int                `json:"id" gorm:"column:id"`
	Title                  string             `json:"title" gorm:"column:title"`
	Image                  *Images            `json:"images" gorm:"column:images"`
	Synopsis               string             `json:"synopsis" gorm:"column:synopsis"`
	StartDate              *time.Time         `json:"startDate" gorm:"column:start_date"`
	EndDate                *time.Time         `json:"endDate" gorm:"column:end_date"`
	MaxEpisodes            int                `json:"maxEpisodes" gorm:"column:max_episodes"`
	NumEpisodes            int                `json:"numEpisodes" gorm:"column:num_episodes"`
	Year                   int                `json:"year" gorm:"column:year"`
	Season                 string             `json:"season" gorm:"column:season"`
	State                  string             `json:"state" gorm:"column:state"`
	AverageEpisodeDuration int                `json:"averageEpisodeDuration" gorm:"column:average_episode_duration"`
	AgeRatingId            int                `json:"ageRatingId" gorm:"colum:age_rating_id"`
	GenreObjects           []Genre            `json:"genres" gorm:"-"`
	AgeRatingObject        *AgeRating         `json:"ageRating" gorm:"-"`
	AlternativeTitles      *AlternativeTitles `json:"alternativeTitles" gorm:"-"`
	StudioObjects          []Studio           `json:"studios" gorm:"-"`
	ProducerObjets         []Producer         `json:"producers" gorm:"-"`
	Broadcast              Broadcast          `json:"broadcast" gorm:"-"`
	Characters             []Character        `json:"characters" gorm:"-"`
}
type Film struct {
	Id                         int                  `json:"id" gorm:"column:id"`
	Title                      string               `json:"title" gorm:"column:title"`
	AlternativeTitlesRaw       AlternativeTitlesRaw `json:"alternativeTitles" gorm:"-"`
	AlternativeTitles          *AlternativeTitles   `json:"alternativeTitleObject"`
	Images                     []string             `json:"images"`
	ImageObject                *Images              `json:"imageObjects" gorm:"column:images"`
	Synopsis                   string               `json:"synopsis"`
	StartDate                  *time.Time           `json:"startDate"`
	EndDate                    *time.Time           `json:"endDate"`
	Aired                      string               `json:"aired"`
	MaxEpisodes                int                  `json:"maxEpisodes"`
	NumEpisodes                int                  `json:"numEpisodes"`
	Year                       int                  `json:"year"`
	Season                     string               `json:"season"`
	Genres                     []string             `json:"genres"`
	GenreObjects               []Genre              `json:"genreObjects"`
	AgeRating                  string               `json:"ageRating"`
	AgeRatingObject            *AgeRating           `json:"ageRatingObject"`
	Background                 string               `json:"background"`
	State                      string               `json:"state"`
	AverageEpisodeDuration     int                  `json:"averageEpisodeDuration"`
	TextAverageEpisodeDuration string               `json:"textAverageEpisodeDuration"`
	Studios                    []string             `json:"studios" gorm:"-"`
	StudioObjects              []Studio             `json:"studioObjects" gorm:""`
	Producers                  []string             `json:"producers" gorm:"-"`
	ProducerObjets             []Producer           `json:"producerObjets" gorm:""`
	Broadcast                  Broadcast            `json:"broadcast"`
	Characters                 []Character          `json:"characters"`
}

func (f *Film) TableName() string {
	return "films"
}

type AlternativeTitlesRaw struct {
	Synonyms []string `json:"synonyms"`
	EnName   *string  `json:"enName"`
	JpName   string   `json:"jpName"`
}

func (a *AlternativeTitles) TableName() string {
	return "alternative_titles"
}

type AlternativeTitles struct {
	FilmId   int     `json:"filmId"`
	Synonyms *string `json:"synonyms"`
	EnName   *string `json:"enName"`
	JpName   *string `json:"jpName"`
}

type Broadcast struct {
	FilmId    int    `json:"filmId"`
	StartTime string `json:"startTime"`
	DayOfWeek string `json:"dayOfWeek"`
	TimeZone  string `json:"timeZone"`
}

func (f *Broadcast) TableName() string {
	return "broadcasts"
}

type Character struct {
	Id          int          `json:"id"`
	Name        string       `json:"name"`
	Role        string       `json:"role"`
	Image       string       `json:"image"`
	VoiceActors []VoiceActor `json:"voiceActors"`
}

func (c *Character) TableName() string {
	return "film_characters"
}

type VoiceActor struct {
	Id       int    `json:"id"`
	Name     string `json:"name"`
	Language string `json:"language"`
	Image    string `json:"image"`
}

func (v *VoiceActor) TableName() string {
	return "actors"
}

type Studio struct {
	Id          int    `json:"id"`
	Name        string `json:"name"`
	Image       *Image `json:"image"`
	Description string `json:"description"`
}

func (*Studio) TableName() string {
	return "studios"
}

type Producer struct {
	Id          int    `json:"id"`
	Name        string `json:"name"`
	Image       *Image `json:"image"`
	Description string `json:"description"`
}

func (*Producer) TableName() string {
	return "producer"
}

type Genre struct {
	Id          int    `json:"id"`
	Name        string `json:"name"`
	Image       *Image `json:"image"`
	Description string `json:"description"`
}

func (*Genre) TableName() string {
	return "genres"
}

type AgeRating struct {
	Id          int    `json:"id"`
	LongName    string `json:"longName"`
	ShortName   string `json:"ShortName"`
	Image       *Image `json:"image"`
	Description string `json:"description"`
}

func (a *AgeRating) TableName() string {
	return "age_ratings"
}
