provider "kubernetes" {
  host                   = module.eks.cluster_endpoint
  cluster_ca_certificate = base64decode(module.eks.cluster_certificate_authority_data)
}

provider "aws" {
  region = var.region
}

data "aws_availability_zones" "available" {}

locals {
  cluster_name = "eks-${random_string.suffix.result}"
}

resource "random_string" "suffix" {
  length  = 8
  special = false
}

#--- RDS ---
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
  depends_on           = [module.vpc]
  tags                 = { Name = "${var.project}-mysql" }
  identifier           = var.database_name
  engine               = "mysql"
  instance_class       = "db.t3.micro"
  allocated_storage    = 20
  username             = var.database_username
  password             = var.database_password
  db_subnet_group_name = module.vpc.database_subnet_group_name
  #vpc_security_group_ids = [aws_security_group.rds_sg.id, aws_security_group.ecs_node_sg.id, aws_security_group.ecs_task.id, aws_security_group.http.id]
  vpc_security_group_ids = [module.eks.node_security_group_id]  #critical one to connect node to the database
  publicly_accessible    = true


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

/*
# Prometheus Helm Release
resource "helm_release" "prometheus" {
  name       = "prometheus"
  chart      = "prometheus"
  namespace  = "monitoring"
  repository = "https://prometheus-community.github.io/helm-charts"
  version    = "15.10.0"  # specify the version
  create_namespace = true

  values = [
    <<EOF
    alertmanager:
      enabled: true
    server:
      resources:
        requests:
          cpu: 100m
          memory: 128Mi
        limits:
          cpu: 500m
          memory: 512Mi
    EOF
  ]
}

# Grafana Helm Release
resource "helm_release" "grafana" {
  name       = "grafana"
  chart      = "grafana"
  namespace  = "monitoring"
  repository = "https://grafana.github.io/helm-charts"
  version    = "6.17.0"  # specify the version
  create_namespace = true

  values = [
    <<EOF
    persistence:
      enabled: true
      size: 10Gi
      storageClassName: "gp2"
    adminPassword: "admin123"
    EOF
  ]

  # Expose Grafana using a LoadBalancer Service
  set {
    name  = "service.type"
    value = "LoadBalancer"
  }
}
*/
