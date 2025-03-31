terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
    }
  }
}

resource "random_password" "password" {
  length  = 16
  special = false
}


resource "aws_opensearch_domain" "opensearch" {
  domain_name    = "${var.project}-opensearch"
  engine_version = "Elasticsearch_7.10"

  cluster_config {
    instance_type                 = "t3.small.search"
    multi_az_with_standby_enabled = false
    dedicated_master_count        = 0
    instance_count                = 1
  }
  node_to_node_encryption {
    enabled = true
  }
  encrypt_at_rest {
    enabled = true
  }
  advanced_security_options {
    enabled                        = true
    anonymous_auth_enabled         = false
    internal_user_database_enabled = true
    master_user_options {
      master_user_name     = "admin"
      master_user_password = random_password.password.result
    }
  }
  access_policies = <<POLICY
    {
    "Version": "2012-10-17",
    "Statement": [
      {
        "Effect": "Allow",
        "Principal": {
          "AWS": "*"
        },
        "Action": "es:ESHttp*",
        "Resource": "arn:aws:es:${data.aws_region.current.name}:${data.aws_caller_identity.current.account_id}:domain/${var.project}-opensearch/*"
      }
    ]
    }
    POLICY
  vpc_options {
    subnet_ids = [var.vpc.private_subnets[0]]
    security_group_ids = [var.sg.opensearch]
  }
  ebs_options {
    ebs_enabled = true
    volume_size = 10
  }
}

data "aws_region" "current" {}
data "aws_caller_identity" "current" {}