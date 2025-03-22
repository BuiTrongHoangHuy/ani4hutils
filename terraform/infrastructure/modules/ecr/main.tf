terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
}

module "ecr_repositories" {
  source = "terraform-aws-modules/ecr/aws"
  for_each = toset(var.image_names)
  repository_name = "${var.project}-${each.value}"
  repository_image_tag_mutability = "MUTABLE"
  repository_force_delete = true
  repository_lifecycle_policy = <<EOF
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
}