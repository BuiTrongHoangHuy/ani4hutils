terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
}


resource "aws_lambda_function" "ffmpeg_master" {
  function_name = "${var.project}-ffmpeg-master"
  role          = ""
  ephemeral_storage {
    size = 3008
  }
  timeout = 15
}