terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
}

resource "aws_instance" "api" {
  tags = {
    Name = "server1"
  }
  root_block_device {
    delete_on_termination = false
    volume_type = "gp2"
    volume_size = 20
  }
  availability_zone = var.az
  user_data = base64encode(file("${path.module}/run.sh"))
  associate_public_ip_address = true
  ami = "ami-0198a868663199764"
  instance_type = "t2.micro"
  vpc_security_group_ids = [var.sg.vm]
  subnet_id = var.vpc.public_subnets[0]
}


