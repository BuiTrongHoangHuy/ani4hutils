variable "project" {
  type = string
}

variable "ecs_cluster" {
  type = string
}

variable "vpc" {
  type = any
}

variable "sg" {
  type = any
}

variable "az" {
  type = string
}
#
# variable "db_config" {
#   type = object(
#     {
#       user     = string
#       password = string
#       database = string
#       hostname = string
#       port     = string
#     }
#   )
# }