terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
}
data "aws_service_discovery_service" "auth" {
  name = "backend"
  namespace_id = var.servicediscovery
}

#
# resource "aws_apigatewayv2_domain_name" "domain" {
#   domain_name = "api.ani4h.site"
#   depends_on = [aws_acm_certificate.cert]
#   domain_name_configuration {
#     certificate_arn = aws_acm_certificate.cert.arn
#     endpoint_type   = "REGIONAL"
#     security_policy = "TLS_1_2"
#   }
# }


resource "aws_apigatewayv2_vpc_link" "vpc_link" {
  name = "${var.project}-vpc-link"
  security_group_ids = [var.sg.vm]
  subnet_ids = [var.vpc.public_subnets[0]]
}


resource "aws_apigatewayv2_api" "rest_gateway" {
  name        = "${var.project}-gateway"
  description = "This is my API for demonstration purposes"
  protocol_type = "HTTP"
}


resource "aws_apigatewayv2_stage" "v1" {
  api_id = aws_apigatewayv2_api.rest_gateway.id
  name   = "v1"
  auto_deploy = true
}

resource "aws_apigatewayv2_integration" "example" {
  api_id = aws_apigatewayv2_api.rest_gateway.id
  integration_type = "HTTP_PROXY"
  connection_type = "VPC_LINK"
  connection_id = aws_apigatewayv2_vpc_link.vpc_link.id
  integration_uri = data.aws_service_discovery_service.auth.arn
  integration_method = "ANY"


}
resource "aws_apigatewayv2_route" "example" {
  api_id = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /{proxy+}"

  target = "integrations/${aws_apigatewayv2_integration.example.id}"
}