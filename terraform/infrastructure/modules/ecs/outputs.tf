output "ecs" {
  value = aws_ecs_cluster.ecs.name
}

output "servicediscovery" {
  value = aws_service_discovery_http_namespace.service_discovery.id
}