output "lb_dns" {
  value = aws_instance.api.public_ip
}