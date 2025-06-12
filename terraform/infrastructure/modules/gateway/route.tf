// auth
resource "aws_apigatewayv2_route" "auth" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "POST /auth"
  target = "integrations/${aws_apigatewayv2_integration.auth.id}"
}

resource "aws_apigatewayv2_route" "auth_p" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "POST /auth/{proxy+}"
  target = "integrations/${aws_apigatewayv2_integration.auth.id}"
}

// user
resource "aws_apigatewayv2_route" "user" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /user"
  target = "integrations/${aws_apigatewayv2_integration.auth.id}"
  authorizer_id = aws_apigatewayv2_authorizer.authorizer.id
  authorization_scopes = ["user","admin"]
  authorization_type = "JWT"
}

resource "aws_apigatewayv2_route" "user_p" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /user/{proxy+}"
  target = "integrations/${aws_apigatewayv2_integration.auth.id}"
  authorizer_id = aws_apigatewayv2_authorizer.authorizer.id
  authorization_type = "JWT"
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


resource "aws_apigatewayv2_route" "upload" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /upload/{proxy+}"
  target    = "integrations/${aws_apigatewayv2_integration.auth.id}"
  authorizer_id = aws_apigatewayv2_authorizer.authorizer.id
  authorization_scopes = ["admin"]
  authorization_type = "JWT"
}


// search
resource "aws_apigatewayv2_route" "search" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /search"

  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}

resource "aws_apigatewayv2_route" "search_p" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /search/{proxy+}"
  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
  authorizer_id = aws_apigatewayv2_authorizer.authorizer.id
  authorization_scopes = ["admin","user"]
  authorization_type = "JWT"
}

resource "aws_apigatewayv2_route" "search" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /search"
  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
  authorizer_id = aws_apigatewayv2_authorizer.authorizer.id
  authorization_scopes = ["admin","user"]
  authorization_type = "JWT"
}

resource "aws_apigatewayv2_route" "favorite_p" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /favorite/{proxy+}"
  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
  authorizer_id = aws_apigatewayv2_authorizer.authorizer.id
  authorization_scopes = ["admin","user"]
  authorization_type = "JWT"
}

resource "aws_apigatewayv2_route" "favorite" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /favorite"
  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
  authorizer_id = aws_apigatewayv2_authorizer.authorizer.id
  authorization_scopes = ["admin","user"]
  authorization_type = "JWT"
}


resource "aws_apigatewayv2_route" "episode_create" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "POST /episode"
  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
  authorizer_id = aws_apigatewayv2_authorizer.authorizer.id
  authorization_scopes = ["admin"]
  authorization_type = "JWT"
}


resource "aws_apigatewayv2_route" "episode" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "GET /episode"
  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
  authorizer_id = aws_apigatewayv2_authorizer.authorizer.id
  authorization_scopes = ["user","admin"]
  authorization_type = "JWT"
}

resource "aws_apigatewayv2_route" "episode_p" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "GET /episode/{proxy+}"
  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
  authorizer_id = aws_apigatewayv2_authorizer.authorizer.id
  authorization_scopes = ["user","admin"]
  authorization_type = "JWT"
}


resource "aws_apigatewayv2_route" "subscription" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /subscription"
  target    = "integrations/${aws_apigatewayv2_integration.auth.id}"
}

resource "aws_apigatewayv2_route" "subscription_p" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /subscription/{proxy+}"
  target    = "integrations/${aws_apigatewayv2_integration.auth.id}"
}

// genre
resource "aws_apigatewayv2_route" "age_rating" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /age-rating"

  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}

resource "aws_apigatewayv2_route" "age_rating_p" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /age-rating/{proxy+}"
  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}
// genre
resource "aws_apigatewayv2_route" "favorite" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /favorite"

  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}

resource "aws_apigatewayv2_route" "favorite_p" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /favorite/{proxy+}"
  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}


// genre
resource "aws_apigatewayv2_route" "wh" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /watch-history"

  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}

resource "aws_apigatewayv2_route" "wh_p" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /watch-history/{proxy+}"
  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}
// genre
resource "aws_apigatewayv2_route" "ep" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /episode"

  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}

resource "aws_apigatewayv2_route" "ep_p" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /episode/{proxy+}"
  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}

// genre
resource "aws_apigatewayv2_route" "rating" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /rating"

  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}

resource "aws_apigatewayv2_route" "rating_p" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /rating/{proxy+}"
  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}

// genre
resource "aws_apigatewayv2_route" "comments" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /comments"

  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}

resource "aws_apigatewayv2_route" "comments_p" {
  api_id    = aws_apigatewayv2_api.rest_gateway.id
  route_key = "ANY /comments/{proxy+}"
  target    = "integrations/${aws_apigatewayv2_integration.film.id}"
}