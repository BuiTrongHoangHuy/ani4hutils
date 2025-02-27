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
	"log"
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

		output, err := s3Client.HeadObject(ctx, &s3.HeadObjectInput{
			Bucket: aws.String(sourceBucket),
			Key:    aws.String(objectKey),
		})
		if err != nil {
			return "", err
		}

		d, e := json.Marshal(output)
		if e != nil {
			return "", e
		}
		fmt.Println(string(d))
		if resolution, ok := output.Metadata["x-amz-meta-resolution"]; ok {
			fmt.Println(resolution)
			return resolution, nil
		}
		return "", nil
	}
	return "empty", nil
}
