package main

import (
	"context"
	"encoding/json"
	"fmt"
	"github.com/aws/aws-lambda-go/events"
	"github.com/aws/aws-lambda-go/lambda"
	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/config"
	ld "github.com/aws/aws-sdk-go-v2/service/lambda"
	"github.com/aws/aws-sdk-go-v2/service/lambda/types"
	"github.com/aws/aws-sdk-go-v2/service/s3"
	"github.com/caovanhoang63/ani4hutils/ffmpeg/common"
	"io"
	"log"
	"os"
	"os/exec"
	"path/filepath"
)

var (
	s3Client *s3.Client
	ldClient *FunctionWrapper
)

type FunctionWrapper struct {
	LambdaClient *ld.Client
}

func (wrapper FunctionWrapper) Invoke(ctx context.Context, functionName string, parameters any, getLog bool) *ld.InvokeOutput {
	logType := types.LogTypeNone
	if getLog {
		logType = types.LogTypeTail
	}
	payload, err := json.Marshal(parameters)
	if err != nil {
		log.Panicf("Couldn't marshal parameters to JSON. Here's why %v\n", err)
	}
	invokeOutput, err := wrapper.LambdaClient.Invoke(ctx, &ld.InvokeInput{
		FunctionName:   aws.String(functionName),
		LogType:        logType,
		Payload:        payload,
		InvocationType: types.InvocationTypeEvent,
	})
	if err != nil {
		log.Panicf("Couldn't invoke function %v. Here's why: %v\n", functionName, err)
	}
	return invokeOutput
}

func init() {
	cfg, err := config.LoadDefaultConfig(context.TODO())
	if err != nil {
		log.Fatalf("unable to load SDK config, %v", err)
	}
	ldClient = &FunctionWrapper{
		LambdaClient: ld.NewFromConfig(cfg),
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
		getObjectParam := s3.GetObjectInput{
			Bucket: aws.String(sourceBucket),
			Key:    aws.String(objectKey),
		}

		output, err := s3Client.GetObject(ctx, &getObjectParam)
		if err != nil {
			return "", fmt.Errorf("failed to get object: %w", err)
		}

		file, err := os.Create(tmpInputFile)
		if err != nil {
			return "", fmt.Errorf("failed to create temp file: %w", err)
		}

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

		if err = json.Unmarshal(cmdOutput, &probeData); err != nil {
			return "", err
		}
		if len(probeData.Streams) == 0 {
			return "", fmt.Errorf("no video stream found")
		}
		res := fmt.Sprintf("%dx%d", probeData.Streams[0].Width, probeData.Streams[0].Height)
		if err = output.Body.Close(); err != nil {
			return "", err
		}
		if err = file.Close(); err != nil {
			return "", err
		}
		for k, v := range common.ResolutionMap {
			if probeData.Streams[0].Height >= k {
				_ = ldClient.Invoke(ctx, "test-worker", common.Event{
					BucketName: record.S3.Bucket.Name,
					ObjectKey:  record.S3.Object.Key,
					Width:      v.Width,
					Height:     v.Height,
				}, true)

			}
		}
		return res, nil
	}
	return "", nil
}
