// auth
resource "aws_apigatewayv2_route" "auth" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /auth"
  target = "integrations/${aws_apigatewayv2_integration.auth.id}"
}

resource "aws_apigatewayv2_route" "auth_p" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /auth/{proxy+}"
  target = "integrations/${aws_apigatewayv2_integration.auth.id}"
}



// user
resource "aws_apigatewayv2_route" "user" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /user"
  target = "integrations/${aws_apigatewayv2_integration.auth.id}"
}

resource "aws_apigatewayv2_route" "user_p" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /user/{proxy+}"
  target = "integrations/${aws_apigatewayv2_integration.auth.id}"
}

// film
resource "aws_apigatewayv2_route" "film" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /film"
  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}

resource "aws_apigatewayv2_route" "film_p" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /film/{proxy+}"
  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}


// genre
resource "aws_apigatewayv2_route" "genre" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /genre"

  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}

resource "aws_apigatewayv2_route" "genre_p" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /genre/{proxy+}"

  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}