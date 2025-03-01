terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
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


#686255971544.dkr.ecr.ap-southeast-1./ani4h-lambda-master:latest