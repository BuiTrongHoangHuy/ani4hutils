package main

import (
	"context"
	"encoding/json"
	"fmt"
	"github.com/aws/aws-lambda-go/lambda"
)

func main() {
	lambda.Start(handleRequest)
}
func handleRequest(ctx context.Context, event json.RawMessage) error {
	fmt.Println(string(event))
	return nil
}
