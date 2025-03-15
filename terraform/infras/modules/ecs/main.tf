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

resource "aws_ecs_service" "auth" {
  name = "service"
  task_definition = aws_ecs_task_definition.auth.arn
  cluster = aws_ecs_cluster.ecs.id
  desired_count = 1
  launch_type = "EC2"
}

resource "aws_ecs_task_definition" "auth" {
  family                = "auth-api"
  requires_compatibilities = ["EC2"]
  network_mode = "host"
  volume {
    name = "config"
  }
  container_definitions = jsonencode([
    {
      name = "sidecar"
      image = "686255971544.dkr.ecr.ap-southeast-1.amazonaws.com/sidecar:latest"
      essential = false
      memory = 100
      mountPoints = [
        {
          sourceVolume  = "config"
          containerPath = "/config"
        }
      ],
    },
    {
      name      = "first"
      image     = "public.ecr.aws/v2r1j0e6/ani4h-api"
      memory    = 512
      essential = true
      portMappings = [
        {
          containerPort = 4000
          hostPort      = 4000
        }
      ],
      environment = [
        { name = "SPRING_CONFIG_LOCATION", value = "/config/" },
      ],
      mountPoints = [
        {
          sourceVolume  = "config"
          containerPath = "/config"
        }
      ],
      dependsOn = [
        {
          containerName = "sidecar"
          condition     = "COMPLETE"
        }
      ]
    },
  ])
}

