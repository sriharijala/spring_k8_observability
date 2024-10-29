output "cluster_endpoint" {
  description = "Endpoint for EKS control plane"
  value       = module.eks.cluster_endpoint
}


output cluter_CA_data {
  description = "EKS control plane CA Authority data"
  value       = module.eks.cluster_certificate_authority_data
}

output "cluster_security_group_id" {
  description = "Security group ids attached to the cluster control plane"
  value       = module.eks.cluster_security_group_id
}

output "region" {
  description = "AWS region"
  value       = var.region
}

output "cluster_name" {
  description = "Kubernetes Cluster Name"
  value       = module.eks.cluster_name
}

output "mysql_db_url" {
  value = aws_db_instance.mysql.endpoint
}

output "mysql_host" {
  value = aws_db_instance.mysql.address
}

/*
output "grafana_url" {
  value = helm_release.grafana.status.load_balancer[0].ingress[0].hostname
}
*/