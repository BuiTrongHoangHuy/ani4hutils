terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
}
resource "aws_apigatewayv2_api" "rest_gateway" {
  name        = "${var.project}-gateway"
  description = "This is my API for demonstration purposes"
  protocol_type = "HTTP"
}

