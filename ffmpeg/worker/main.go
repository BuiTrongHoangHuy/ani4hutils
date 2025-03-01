package main

import (
	"bytes"
	"context"
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

func handleRequest(ctx context.Context, event events.S3Event) error {
	for _, record := range event.Records {
		sourceBucket := record.S3.Bucket.Name
		objectKey := record.S3.Object.Key

		tmpInputFile := fmt.Sprintf("/tmp/%s", filepath.Base(objectKey))
		tmpOutputDir := "/tmp/hls_output"
		// 1. Download file from s3
		getObjectParam := s3.GetObjectInput{
			Bucket: aws.String(sourceBucket),
			Key:    aws.String(objectKey),
		}

		output, err := s3Client.GetObject(ctx, &getObjectParam)
		if err != nil {
			return fmt.Errorf("failed to get object: %w", err)
		}
		defer output.Body.Close()

		file, err := os.Create(tmpInputFile)
		if err != nil {
			return fmt.Errorf("failed to create temp file: %w", err)
		}
		defer file.Close()

		_, err = io.Copy(file, output.Body)
		if err != nil {
			return fmt.Errorf("failed to save file: %w", err)
		}

		// 2. Run ffmpeg
		err = os.MkdirAll(tmpOutputDir, os.ModePerm)
		if err != nil {
			return fmt.Errorf("failed to create output dir: %w", err)
		}

		args := []string{
			"-i", tmpInputFile,
			"-crf", "32",
			"-preset", "ultrafast",
			"-threads", "0",
			"-tune", "fastdecode",
			"-tune", "zerolatency",
			"-x264-params", "scenecut=0",
			"-map", "0:v:0", "-map", "0:a:0",
			"-filter:v:0", "scale=-2:360",
			"-b:v:0", "800k",
			"-maxrate:v:0", "900k",
			"-bufsize:v:0", "1200k",
			"-map", "0:v:0", "-map", "0:a:0",
			"-filter:v:1", "scale=-2:720",
			"-b:v:1", "2800k",
			"-maxrate:v:1", "3000k",
			"-bufsize:v:1", "4200k",
			"-c:v", "libx264",
			"-c:a", "aac",
			"-ar", "48000",
			"-b:a", "128k",
			"-hls_time", "10",
			"-hls_list_size", "0",
			"-f", "hls",
			"-master_pl_name", "master.m3u8",
			"-hls_segment_filename", tmpOutputDir + "/%v_%03d.m4s",
			"-hls_segment_type", "fmp4",
			"-var_stream_map", "v:0,a:0,name:index:360p v:1,a:1,name:index:720p",
			tmpOutputDir + "/index-%v.m3u8",
		}

		cmd := exec.CommandContext(ctx, "ffmpeg", args...)
		cmd.Stdout = os.Stdout
		cmd.Stderr = os.Stderr

		err = cmd.Run()
		if err != nil {
			return fmt.Errorf("failed to run ffmpeg: %w", err)
		}

		outputBucket := "ani4h-film-storage"
		files, err := os.ReadDir(tmpOutputDir)
		if err != nil {
			return fmt.Errorf("failed to read output dir: %w", err)
		}

		for _, file := range files {

			// ignore to put master.m3u8 file
			if file.Name() == "master.m3u8" {
				continue
			}

			filePath := fmt.Sprintf("%s/%s", tmpOutputDir, file.Name())
			fileData, err := os.ReadFile(filePath)
			if err != nil {
				return fmt.Errorf("failed to read output file: %w", err)
			}

			_, err = s3Client.PutObject(ctx, &s3.PutObjectInput{
				Bucket: aws.String(outputBucket),
				Key:    aws.String("hls_output/" + file.Name()),
				Body:   bytes.NewReader(fileData),
			})
			if err != nil {
				return fmt.Errorf("failed to upload file: %w", err)
			}
		}
	}

	return nil
}
