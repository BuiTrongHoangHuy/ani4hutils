package main

import (
	"bytes"
	"context"
	"encoding/json"
	"fmt"
	"github.com/aws/aws-lambda-go/lambda"
	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/config"
	"github.com/aws/aws-sdk-go-v2/service/s3"
	"github.com/caovanhoang63/ani4hutils/ffmpeg/common"
	"io"
	"log"
	"os"
	"os/exec"
	"path/filepath"
	"strings"
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

func handleRequest(ctx context.Context, event common.Event) error {
	b, _ := json.Marshal(event)
	log.Println(string(b))
	sourceBucket := event.BucketName
	objectKey := event.ObjectKey
	tmpInputFile := fmt.Sprintf("/tmp/%s", filepath.Base(objectKey))
	tmpOutputDir := "/tmp/hls_output"
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

	if _, err = io.Copy(file, output.Body); err != nil {
		return fmt.Errorf("failed to save file: %w", err)
	}

	if err = os.MkdirAll(tmpOutputDir, os.ModePerm); err != nil {
		return fmt.Errorf("failed to create output dir: %w", err)
	}

	eventHeight := event.Height

	resolutionInfo := common.ResolutionMap[eventHeight]
	b, _ = json.Marshal(resolutionInfo)
	log.Println(string(b))

	//ffmpeg -hwaccel none -i rezero.webm  -crf 32 -preset ultrafast -threads 1 \
	//-tune fastdecode -tune zerolatency \
	//-x264-params "scenecut=0" \
	//-map 0:v:0 -map 0:a:0 -filter:v:0 "scale=-2:360" -b:v:0 800k  -maxrate:v:0 900k  -bufsize:v:0 1200k \
	//-map 0:v:0 -map 0:a:0 -filter:v:1 "scale=-2:480" -b:v:1 1400k -maxrate:v:1 1500k -bufsize:v:1 2100k \
	//-map 0:v:0 -map 0:a:0 -filter:v:2 "scale=-2:720" -b:v:2 2800k -maxrate:v:2 3000k -bufsize:v:2 4200k \
	//-c:v libx264 -c:a aac -ar 48000 -b:a 128k \
	//-hls_time 10 -hls_list_size 0 -f hls \
	//-master_pl_name master.m3u8 \
	//-hls_segment_filename "./hls_output/%v_%03d.m4s" \
	//-hls_segment_type fmp4 \
	//-var_stream_map "v:0,a:0,name:360p v:1,a:1,name:480p v:2,a:2,name:720p" \
	//./hls_output/index-%v.m3u8
	args := []string{
		"-i", tmpInputFile,
		"-crf", "32",
		"-preset", "ultrafast",
		"-threads", "0",
		"-map", "0:v:0", "-map", "0:a:0",
		"-filter:v:0", fmt.Sprintf("scale=-2:%d", eventHeight),
		"-b:v:0", fmt.Sprintf("%dk", resolutionInfo.VBitRate),
		"-maxrate:v:0", fmt.Sprintf("%dk", resolutionInfo.VBitRate+300),
		"-bufsize:v:0", fmt.Sprintf("%dk", resolutionInfo.VBitRate+400),
		"-c:v", "libx264",
		"-c:a", "aac",
		"-ar", "48000",
		"-b:a", fmt.Sprintf("%dk", resolutionInfo.ABitRate),
		"-hls_time", "10",
		"-hls_list_size", "0",
		"-f", "hls",
		"-master_pl_name", "master.m3u8",
		"-hls_segment_filename", tmpOutputDir + "/%v_%03d.m4s",
		"-hls_segment_type", "fmp4",
		"-hls_fmp4_init_filename", fmt.Sprintf("%dp_init.mp4", eventHeight),
		"-var_stream_map", fmt.Sprintf("v:0,a:0,name:%dp", resolutionInfo.Height),
		tmpOutputDir + "/index-%v.m3u8",
	}
	log.Println(strings.Join(args, " "))
	cmd := exec.CommandContext(ctx, "ffmpeg", args...)
	cmd.Stdout = os.Stdout
	cmd.Stderr = os.Stderr

	if err = cmd.Run(); err != nil {
		return fmt.Errorf("failed to run ffmpeg: %w", err)
	}

	outputBucket := "ani4h-film-storage"
	files, err := os.ReadDir(tmpOutputDir)
	if err != nil {
		return fmt.Errorf("failed to read output dir: %w", err)
	}
	for _, f := range files {
		if f.Name() == "master.m3u8" {
			continue
		}
		filePath := fmt.Sprintf("%s/%s", tmpOutputDir, f.Name())
		fileData, err := os.ReadFile(filePath)
		if err != nil {
			return fmt.Errorf("failed to read output file: %w", err)
		}
		_, err = s3Client.PutObject(ctx, &s3.PutObjectInput{
			Bucket: aws.String(outputBucket),
			Key:    aws.String(event.ObjectKey + "/" + f.Name()),
			Body:   bytes.NewReader(fileData),
		})
		if err != nil {
			return fmt.Errorf("failed to upload file: %w", err)
		}
	}
	return nil
}
