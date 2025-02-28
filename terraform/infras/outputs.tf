output "db_password" {
  value = module.database.config.password
  sensitive = true
}

output "s3_static_url" {
  value = module.s3.s3_dns_name
}