resource "random_password" "password" {
  length           = 16
  special          = false
}

resource "aws_db_instance" "database" {
  allocated_storage      = 20
  storage_type           = "gp2"
  engine                 = "mysql"
  engine_version         = "8.0.39"
  instance_class         = "db.t3.micro"
  identifier             = "${var.project}-db-instance"
  username               = "admin"
  availability_zone      = var.az
  password               = random_password.password.result
  db_subnet_group_name   = var.vpc.database_subnet_group
  vpc_security_group_ids = [var.sg.db]
  skip_final_snapshot    = true
  multi_az               = false
}
