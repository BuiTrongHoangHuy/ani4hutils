terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
}
resource "aws_api_gateway_rest_api" "rest_gateway" {
  name        = "${var.project}-gateway"
  description = "This is my API for demonstration purposes"
}

resource "aws_api_gateway_resource" "auth" {
  path_part   = "auth"
  parent_id   = aws_api_gateway_rest_api.rest_gateway.root_resource_id
  rest_api_id = aws_api_gateway_rest_api.rest_gateway.id
}

