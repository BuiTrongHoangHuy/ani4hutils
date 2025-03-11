terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
}
data "aws_availability_zones" "available" {
  state = "available"
}



module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  name    = "${var.project}-vpc"
  cidr    = var.vpc_cidr
  azs     = data.aws_availability_zones.available.names

  private_subnets  = var.private_subnets
  public_subnets   = var.public_subnets
  database_subnets = var.database_subnets
  create_database_subnet_group = true
}

module "vm_sg" {
  source = "terraform-in-action/sg/aws"
  vpc_id = module.vpc.vpc_id
  ingress_rules = [
    {
      port        = 80
      cidr_blocks = ["0.0.0.0/0"]
    },
    {
      port        = 443
      cidr_blocks = ["0.0.0.0/0"]
    }
    ,
    {
      port        = 22
      cidr_blocks = ["0.0.0.0/0"]
    },
    {
      protocol = "-1"
      from_port = 0
      to_port = 65535
      cidr_blocks = ["172.30.0.0/16","172.31.0.0/16"]
    }
  ]
}

module "db_sg" {
  source = "terraform-in-action/sg/aws"
  vpc_id = module.vpc.vpc_id
  ingress_rules = [
    {
      port            = 3306
      security_groups = [module.vm_sg.security_group.id]
    },
    {
      port = 3306,
      cidr_blocks = ["172.30.0.0/16","172.31.0.0/16"]
    }
  ]
}


module "mq_sq" {
  source = "terraform-in-action/sg/aws"
  vpc_id = module.vpc.vpc_id
  ingress_rules =[
    {
      port =  5672
      security_groups = [module.vm_sg.security_group.id]
    },
    {
      port =  5672
      cidr_blocks = ["172.30.0.0/16","172.31.0.0/16"]
    }
  ]
}

module "opensearch_sg" {
  source = "terraform-in-action/sg/aws"
  vpc_id = module.vpc.vpc_id
  ingress_rules = [
    {
      from_port = 443
      to_port   = 443
      protocol  = "tcp"
      security_groups = [module.vm_sg.security_group.id]
    },
    {
      from_port = 80
      to_port   = 80
      protocol  = "tcp"
      security_groups = [module.vm_sg.security_group.id]
    },
    {
      from_port = 443
      to_port   = 443
      protocol  = "tcp"
      cidr_blocks = ["172.30.0.0/16","172.31.0.0/16"]
    },
    {
      from_port = 80
      to_port   = 80
      protocol  = "tcp"
      cidr_blocks = ["172.30.0.0/16","172.31.0.0/16"]
    },
  ]
}