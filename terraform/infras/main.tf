terraform {
  backend "s3" {
    bucket = "ani4h-s3-backend"
    dynamodb_table = "ani4h-s3-backend"
    region = "ap-southeast-1"
    key = "ani4h"
    encrypt = false
  }
}

provider "aws" {
  region = "ap-southeast-1"
}


locals {
  project = "ani4h"
}

data "aws_region" "current" {}

module "networking" {
  source = "./modules/networking"
  project          = local.project
  vpc_cidr         = "10.0.0.0/16"
  private_subnets  = ["10.0.1.0/24", "10.0.2.0/24", "10.0.3.0/24"]
  public_subnets   = ["10.0.4.0/24", "10.0.5.0/24", "10.0.6.0/24"]
  database_subnets = ["10.0.7.0/24", "10.0.8.0/24", "10.0.9.0/24"]
}

# module "lambdas" {
#   source = "./modules/lambda"
# }



module "ecr" {
  source = "./modules/ecr"
}

module "database" {
  source = "./modules/database"

  project = local.project
  vpc     = module.networking.vpc
  sg      = module.networking.sg
}


module "vm" {
  source = "./modules/ec2"
  project = local.project
  vpc =module.networking.vpc
  sg = module.networking.sg
}

module "s3" {
  source = "./modules/s3"
  project = local.project
}