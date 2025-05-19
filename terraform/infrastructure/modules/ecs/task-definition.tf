module "ecs_container_definition" {
  for_each      = var.services
  source        = "terraform-aws-modules/ecs/aws//modules/service"
  cluster_arn   = aws_ecs_cluster.ecs.arn
  name          = each.value
  desired_count = 1
  launch_type   = "EC2"
  requires_compatibilities = ["EC2"]
  network_mode  = "bridge"
  cpu = 512
  memory = 600
  service_connect_configuration = {

    enabled   = true
    namespace = aws_service_discovery_http_namespace.service_discovery.arn
    service = {
      port_name = each.value  # e.g., "film"
      client_alias = {
        dns_name = "${each.value}.my-namespace"
        port     = 4000
      }
    }
  }
  volume =[
    {
      name = "config"
    }
  ]
  container_definitions = {

    sidecar = {
      name      = "sidecar"
      image     = "686255971544.dkr.ecr.ap-southeast-1.amazonaws.com/sidecar:latest"
      essential = false
      # memory    = 40
      mount_points = [
        {
          sourceVolume  = "config"
          containerPath = "/config"
        }
      ]
    },
    (each.value) = {
      name      = each.value
      image     = "public.ecr.aws/v2r1j0e6/ani4h-api-${each.value}"
      essential = true
      port_mappings = [
        {
          containerPort = 4000
          name          = each.value
        }
      ]
      environment = [
        { name = "SPRING_CONFIG_LOCATION", value = "/config/" },
        { name = "JAVA_TOOL_OPTIONS", value = "-Djava.io.tmpdir=/config" }
      ]

      mount_points = [
        {
          sourceVolume  = "config"
          containerPath = "/config"
        }
      ]

      dependsOn = [
        {
          containerName = "sidecar"
          condition     = "COMPLETE"
        }
      ]
    }
  }
}