variable "project" {
  type    = string
}

variable "function_names" {
  type    = list(string)
}

variable "ecr" {
  type = any
}