variable "project" {
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

variable "services" {
  type = set(string)
}