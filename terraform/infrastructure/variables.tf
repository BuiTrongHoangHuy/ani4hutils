variable "region" {
  default = ""
}

variable "function_names" {
  default = ["lambda-master","lambda-worker"]
  type = list(string)
}


variable "services" {
  default = ["auth","film"]
  type = set(string)
}

