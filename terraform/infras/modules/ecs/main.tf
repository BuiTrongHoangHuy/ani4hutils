terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
}

resource "aws_ecs_cluster" "ecs" {
  name = "${var.project}-cluster"
}