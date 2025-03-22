variable "project" {
  type    = string
}

variable "function_names" {
  type    = list(string)
}

variable "account_id" {
  type = any
}

variable "region" {
  type = string
}