terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
    }
  }
}
data "aws_service_discovery_service" "auth" {
  name         = "auth"
  namespace_id = var.servicediscovery
}

data "aws_service_discovery_service" "film" {
  name         = "film"
  namespace_id = var.servicediscovery
}

resource "aws_apigatewayv2_vpc_link" "vpc_link" {
  name = "${var.project}-vpc-link"
  security_group_ids = [var.sg.vm]
  subnet_ids = [var.vpc.public_subnets[0]]
}


resource "aws_apigatewayv2_api" "rest_gateway" {
  name          = "${var.project}-gateway"
  description   = "This is my API for demonstration purposes"
  protocol_type = "HTTP"
  cors_configuration {
    allow_headers = ["*"]
    allow_origins = ["*",]
  }
}

resource "aws_cloudwatch_log_group" "log_group_v1" {

}

resource "aws_apigatewayv2_stage" "v1" {
  api_id      = aws_apigatewayv2_api.rest_gateway.id
  name        = "v1"
  auto_deploy = true
  access_log_settings {
    destination_arn = aws_cloudwatch_log_group.log_group_v1.arn
    format = jsonencode(
      {
        errMsg                  = "$context.error.message"
        errType                 = "$context.error.responseType"
        httpMethod              = "$context.httpMethod"
        intError                = "$context.integration.error"
        intIntStatus            = "$context.integration.integrationStatus"
        intLat                  = "$context.integration.latency"
        intReqID                = "$context.integration.requestId"
        intStatus               = "$context.integration.status"
        integrationErrorMessage = "$context.integrationErrorMessage"
        protocol                = "$context.protocol"
        requestId               = "$context.requestId"
        requestTime             = "$context.requestTime"
        resourcePath            = "$context.resourcePath"
        responseLength          = "$context.responseLength"
        routeKey                = "$context.routeKey"
        sourceIp                = "$context.identity.sourceIp"
        status                  = "$context.status"
      }
    )
  }
}

resource "aws_apigatewayv2_integration" "auth" {
  api_id             = aws_apigatewayv2_api.rest_gateway.id
  integration_type   = "HTTP_PROXY"
  connection_type    = "VPC_LINK"
  connection_id      = aws_apigatewayv2_vpc_link.vpc_link.id
  integration_uri    = data.aws_service_discovery_service.auth.arn
  integration_method = "ANY"
}
resource "aws_apigatewayv2_integration" "film" {
  api_id             = aws_apigatewayv2_api.rest_gateway.id
  integration_type   = "HTTP_PROXY"
  connection_type    = "VPC_LINK"
  connection_id      = aws_apigatewayv2_vpc_link.vpc_link.id
  integration_uri    = data.aws_service_discovery_service.film.arn
  integration_method = "ANY"
}

