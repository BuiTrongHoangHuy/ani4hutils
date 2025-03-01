package main

import (
	"context"
	"encoding/json"
	"fmt"
	"github.com/aws/aws-lambda-go/events"
	"github.com/aws/aws-lambda-go/lambda"
	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/config"
	"github.com/aws/aws-sdk-go-v2/service/s3"
	"io"
	"log"
	"os"
	"os/exec"
	"path/filepath"
)

var (
	s3Client *s3.Client
)

func init() {
	cfg, err := config.LoadDefaultConfig(context.TODO())
	if err != nil {
		log.Fatalf("unable to load SDK config, %v", err)
	}

	s3Client = s3.NewFromConfig(cfg)
}

func main() {
	lambda.Start(handleRequest)
}

func handleRequest(ctx context.Context, event events.S3Event) (string, error) {
	for _, record := range event.Records {
		sourceBucket := record.S3.Bucket.Name
		objectKey := record.S3.Object.Key

		tmpInputFile := fmt.Sprintf("/tmp/%s", filepath.Base(objectKey))
		// 1. Download file from s3
		getObjectParam := s3.GetObjectInput{
			Bucket: aws.String(sourceBucket),
			Key:    aws.String(objectKey),
		}

		output, err := s3Client.GetObject(ctx, &getObjectParam)
		if err != nil {
			return "", fmt.Errorf("failed to get object: %w", err)
		}
		defer output.Body.Close()

		file, err := os.Create(tmpInputFile)
		if err != nil {
			return "", fmt.Errorf("failed to create temp file: %w", err)
		}
		defer file.Close()

		_, err = io.Copy(file, output.Body)
		if err != nil {
			return "", fmt.Errorf("failed to save file: %w", err)
		}

		cmd := exec.Command("ffprobe", "-v", "error", "-select_streams", "v:0", "-show_entries", "stream=width,height", "-of", "json", tmpInputFile)

		cmdOutput, err := cmd.Output()
		if err != nil {
			return "", err
		}

		var probeData struct {
			Streams []struct {
				Width  int `json:"width"`
				Height int `json:"height"`
			} `json:"streams"`
		}
		json.Unmarshal(cmdOutput, &probeData)
		if len(probeData.Streams) == 0 {
			return "", fmt.Errorf("no video stream found")
		}
		res := fmt.Sprintf("%dx%d", probeData.Streams[0].Width, probeData.Streams[0].Height)
		return res, nil
	}
	return "", nil
}
