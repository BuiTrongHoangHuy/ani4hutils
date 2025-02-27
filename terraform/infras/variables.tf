terraform {
  backend "s3" {}
}

provider "aws" {
  region = "ap-southeast-1"
  version = "< 5.71.0"
}

locals {
  project = "terraform-seminar"
}