package main

import (
	"context"
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

func handleRequest(ctx context.Context, event events.S3Event) error {
	for _, record := range event.Records {
		fmt.Println(record.S3.Bucket.Name, record.S3.Object.Key)

		sourceBucket := record.S3.Bucket.Name
		objectKey := record.S3.Object.Key
		param := &s3.CopyObjectInput{
			Bucket:     aws.String("ani4h-film-storage"),
			CopySource: aws.String(fmt.Sprintf("%v/%v", sourceBucket, objectKey)),
			Key:        &record.S3.Object.Key,
		}
		_, err := s3Client.CopyObject(ctx, param)

		if err != nil {
			return err
		}
	}

	return nil
}
