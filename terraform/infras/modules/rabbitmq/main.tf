terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
}

resource "random_password" "password" {
  length           = 16
  special          = true
  override_special = "!#$%&*()-_=+[]{}<>?"
}

resource "aws_mq_broker" "broker" {
  broker_name        = "${var.project}-broker"
  apply_immediately = true
  engine_type        = "RabbitMQ"
  engine_version     = "3.13"
  host_instance_type = "mq.t3.micro"
  user {
    username = "admin"
    password = random_password.password.result
  }
  deployment_mode = "SINGLE_INSTANCE"
  publicly_accessible = false
  storage_type = "ebs"
  subnet_ids = [var.vpc.private_subnets[0]]
  security_groups = [var.sg.mq]
  auto_minor_version_upgrade = true
}