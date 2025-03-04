terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
    }
  }
}

resource "random_password" "password" {
  length = 16
  special = false
}


resource "aws_opensearch_domain" "opensearch" {
  domain_name = "${var.project}-opensearch"
  engine_version = "Elasticsearch_7.10"

  cluster_config {
    instance_type = "t3.small.search"
    multi_az_with_standby_enabled = false
    dedicated_master_count = 0
    instance_count = 1
  }
  advanced_security_options {
    enabled                        = false
    anonymous_auth_enabled         = true
    internal_user_database_enabled = true
    master_user_options {
      master_user_name     = "admin"
      master_user_password = random_password.password.result
    }
  }

  vpc_options {
    subnet_ids = [var.vpc.private_subnets[0]]
    security_group_ids = [var.sg.opensearch]
  }
  ebs_options {
    ebs_enabled = true
    volume_size = 10
  }
}