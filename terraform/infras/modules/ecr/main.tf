terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
}
resource "aws_ecr_repository" "lambda_master" {
  name = "${var.project}-lambda-master"
  image_tag_mutability = "MUTABLE"
  force_delete = true

}

resource "aws_ecr_lifecycle_policy" "lambda_master" {
  policy = <<EOF
  {
      "rules": [
          {
              "rulePriority": 1,
              "description": "Keep 1 latest image",
              "selection": {
                  "tagStatus": "tagged",
                  "tagPrefixList": ["v"],
                  "countType": "imageCountMoreThan",
                  "countNumber": 1
              },
              "action": {
                  "type": "expire"
              }
          }
      ]
  }
  EOF
  repository = aws_ecr_repository.lambda_master.name
}
