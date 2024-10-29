#-- RDS database
resource "aws_security_group" "rds_sg" {
  depends_on = [aws_vpc.main]

  tags   = var.tags
  name   = "${var.project}-rds-sg"
  vpc_id = aws_vpc.main.id



  /* after testing uncomment this and remove the otehr ingress block wide open one.teams*/
  ingress {
    from_port = 3306
    to_port   = 3306
    protocol  = "tcp"
     cidr_blocks = [aws_subnet.public[0].cidr_block, aws_subnet.public[1].cidr_block]
  }

  egress {
    description = "output from MySQL"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}


resource "aws_db_subnet_group" "rds_subnet" {
  depends_on = [aws_subnet.private]
  tags       = { Name = "${var.project}-rds_subnet" }
  name       = "${var.project}-rds-subnet-group"
  subnet_ids = [aws_subnet.private[0].id, aws_subnet.private[1].id, aws_subnet.public[0].id, aws_subnet.public[1].id]
}

resource "aws_iam_role" "rds_monitoring_role" {
  name = "rds-monitoring-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = "sts:AssumeRole",
        Effect = "Allow",
        Principal = {
          Service = "monitoring.rds.amazonaws.com"
        }
      }
    ]
  })
}

resource "aws_iam_policy_attachment" "rds_monitoring_attachment" {
  name       = "rds-monitoring-attachment"
  roles      = [aws_iam_role.rds_monitoring_role.name]
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonRDSEnhancedMonitoringRole"
}

resource "aws_db_instance" "mysql" {
  depends_on             = [aws_db_subnet_group.rds_subnet]
  tags                   = { Name = "${var.project}-mysql" }
  identifier             = var.database_name
  engine                 = "mysql"
  instance_class         = "db.t3.micro"
  allocated_storage      = 20
  username               = "sjala"
  password               = "JalaJala123"
  db_subnet_group_name   = aws_db_subnet_group.rds_subnet.name
  vpc_security_group_ids = [aws_security_group.rds_sg.id, aws_security_group.ecs_node_sg.id, aws_security_group.ecs_task.id, aws_security_group.http.id]


  apply_immediately   = true
  deletion_protection = false #
  db_name             = var.database_name
  #multi_az = true 

  backup_retention_period = 0 # Number of days to retain automated backups
  #backup_window = "03:00-04:00" # Preferred UTC backup window (hh24:mi-hh24:mi format)
  #maintenance_window = "mon:04:00-mon:04:30" # Preferred UTC maintenance window

  # Enable automated backups
  skip_final_snapshot       = true
  final_snapshot_identifier = "${var.project}-db-snap"

}
