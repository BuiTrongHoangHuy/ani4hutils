package main

import (
	"github.com/caovanhoang63/ani4hutils/crawscript/model"
	"image"
	"io"
	"log"
)

func cleanImageData(film *model.Film) *model.Images {
	var images model.Images
	imageData := map[string]bool{}
	for _, imageStr := range film.Images {
		if _, ok := imageData[imageStr]; ok {
			continue
		}
		img := model.Image{
			Id:        0,
			Url:       imageStr,
			Width:     0,
			Height:    0,
			CloudName: "cdn.myanimelist.net",
			Extension: "jpg",
		}
		imageData[imageStr] = true
		images = append(images, img)
	}
	return &images
}

// getImageDimension returns the width and height of an image file
func getImageDimension(reader io.Reader) (int, int, error) {
	img, _, err := image.DecodeConfig(reader)

	if err != nil {
		log.Println("err", err)
		return 0, 0, err
	}

	return img.Width, img.Height, err
}
