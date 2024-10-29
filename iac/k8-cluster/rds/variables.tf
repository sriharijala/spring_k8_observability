variable "region" {
  description = "AWS region"
  type        = string
  default     = "us-east-1"
}

variable project {
    description = "Project name"
    default = "k8-test" 
}

variable environment {
    description = "Environment name"
    default = "dev" 
}

variable "tags" {
  type        = map(string)
  description = "Common resource tags"
  default = {
    Name = "user-reviews"
  }
}

variable num-of-azs {
    description = "Number of AZs to use in Cluster"
    default = "2"
}

variable ec2-instance-type {
    description = "Type of EC2 instances in cluster"
    default = "t2.micro"
}

variable min_k8_nodes {
    description = "Number of minimum nodes in cluster"
    default = "1"
}

variable max_k8_nodes {
    description = "Number of maximum nodes in cluster"
    default = "2"
}

variable desired_k8_nodes {
    description = "Number of maximum nodes in cluster"
    default = "1"
}


variable "database_name" {
  type    = string
  default = "socialmedia"
}

variable "database_username" {
  type    = string
  default = "sjala"
}

variable "database_password" {
  type    = string
  default = "JalaJala123"
}
