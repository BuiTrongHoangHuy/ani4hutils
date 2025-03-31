
resource "aws_apigatewayv2_authorizer" "authorizer" {
  api_id          = aws_apigatewayv2_api.rest_gateway.id
  authorizer_type = "JWT"
  name            = "s3"
  identity_sources = ["$request.header.Authorization"]
  jwt_configuration {
    issuer = "https://ani4h-auth.s3.ap-southeast-1.amazonaws.com/"
    audience = ["my-audience"]
  }
}