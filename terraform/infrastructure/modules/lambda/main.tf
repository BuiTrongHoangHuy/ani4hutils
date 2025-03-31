terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
}
resource "aws_iam_policy" "ecr_policy" {
  name        = "lambda_ecr_access"
  description = "Allow Lambda to pull image from ECR"
  policy      = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "ecr:GetDownloadUrlForLayer",
          "ecr:BatchGetImage",
          "ecr:GetAuthorizationToken"
        ]
        Resource = "*"
      }
    ]
  })
}
data "aws_iam_policy_document" "assume_role" {
  statement {
    effect = "Allow"

    principals {
      type        = "Service"
      identifiers = ["lambda.amazonaws.com"]
    }

    actions = ["sts:AssumeRole"]
  }
}


resource "aws_iam_role_policy_attachment" "lambda_ecr_attachment" {
  policy_arn = aws_iam_policy.ecr_policy.arn
  role       = aws_iam_role.iam_for_lambda.name
}
resource "aws_iam_role" "iam_for_lambda" {
  name               = "iam_for_lambda"
  assume_role_policy = data.aws_iam_policy_document.assume_role.json
}

resource "aws_lambda_function" "master_function" {
  function_name = "${var.project}-ffmpeg-master"
  role          = aws_iam_role.iam_for_lambda.arn
  image_uri = "${var.account_id}.dkr.ecr.${var.region}.amazonaws.com/${var.project}-lambda-master:latest"
  ephemeral_storage {
    size = 3004
  }
  package_type = "Image"
  timeout = 15*60
}