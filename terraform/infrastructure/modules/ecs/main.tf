terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
}

resource "aws_ecs_cluster" "ecs" {
  name = "${var.project}-cluster"
  service_connect_defaults {
    namespace = aws_service_discovery_http_namespace.service_discovery.arn
  }
}
resource "aws_service_discovery_http_namespace" "service_discovery" {
  name        = var.project
}

resource "aws_ecs_service" "auth" {
  name = "service"
  task_definition = aws_ecs_task_definition.auth.arn
  cluster = aws_ecs_cluster.ecs.id
  desired_count = 1
  launch_type = "EC2"
  service_connect_configuration {
    enabled      = true
    namespace = aws_service_discovery_http_namespace.service_discovery.arn
    service {
      port_name = "backend"
      client_alias {
        dns_name = "backend.my-namespace"
        port     = 4000
      }
    }
  }
}



resource "aws_ecs_task_definition" "auth" {
  family                = "auth-api"
  requires_compatibilities = ["EC2"]
  network_mode = "bridge"
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
      memory    = 500
      essential = true
      portMappings = [
        {
          containerPort = 4000
          name: "backend"
        }
      ],
      environment = [
        { name = "SPRING_CONFIG_LOCATION", value = "/config/" },
      ],
      linuxParameters: {
        maxSwap: 300,
        swappiness: 60
      },
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

