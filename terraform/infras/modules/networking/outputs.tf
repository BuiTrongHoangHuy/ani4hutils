output "vpc" {
  value = module.vpc
}

output "sg" {
  value = {
    vm = module.vm_sg.security_group.id
    db = module.db_sg.security_group.id
    mq = module.mq_sq.security_group.id
  }
}