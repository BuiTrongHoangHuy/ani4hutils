output "ecs" {
  value = aws_ecs_cluster.ecs.name
}

output "servicediscovery" {
  value = aws_ecs_cluster.ecs.id


}