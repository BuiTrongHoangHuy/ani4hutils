output "db_config" {
  value = module.database.config
  sensitive = true
}

# output "s3_static_url" {
#   value = module.s3.s3_dns_name
# }

output "vm_ip" {
  value = module.vm.lb_dns
  sensitive = true
}

output "cdn" {
  value = module.s3.image_cdn
}

# output "lambdas" {
#   value = module.lambdas.lambda
# }