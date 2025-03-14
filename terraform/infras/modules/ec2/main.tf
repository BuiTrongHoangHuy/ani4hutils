terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
}


resource "aws_key_pair" "key" {
  key_name = "hoang"
  public_key = "ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIGdGr8Mh1K0Lh8iizhB7fQIp5aHOIgFtp4xlXlmoDylN 22520457@gm.uit.edu.vn"
}


## Creates IAM Role which is assumed by the Container Instances (aka EC2 Instances)

resource "aws_iam_role" "ec2_instance_role" {
  name               = "${var.project}_EC2_InstanceRole"
  assume_role_policy = data.aws_iam_policy_document.ec2_instance_role_policy.json
}

resource "aws_iam_role_policy_attachment" "ec2_instance_role_policy" {
  role       = aws_iam_role.ec2_instance_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonEC2ContainerServiceforEC2Role"
}

resource "aws_iam_instance_profile" "ec2_instance_role_profile" {
  name  = "${var.project}_EC2_InstanceRoleProfile"
  role  = aws_iam_role.ec2_instance_role.id
}

data "aws_iam_policy_document" "ec2_instance_role_policy" {
  statement {
    actions = ["sts:AssumeRole"]
    effect  = "Allow"

    principals {
      type        = "Service"
      identifiers = [
        "ec2.amazonaws.com",
        "ecs.amazonaws.com"
      ]
    }
  }
}
resource "aws_instance" "api" {
  tags = {
    Name = "server1"
  }
  key_name = aws_key_pair.key.key_name
  availability_zone = var.az
  user_data = templatefile("${path.module}/run.sh",{
    ecs_cluster=var.ecs_cluster
  })
  associate_public_ip_address = true
  ami = "3"
  iam_instance_profile = aws_iam_instance_profile.ec2_instance_role_profile.name
  instance_type = "t2.micro"
  vpc_security_group_ids = [var.sg.vm]
  subnet_id = var.vpc.public_subnets[0]
}


