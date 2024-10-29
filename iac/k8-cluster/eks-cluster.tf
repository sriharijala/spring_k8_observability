module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "19.13.1"

  cluster_name    = var.project
  cluster_version = "1.25"

  vpc_id                         = module.vpc.vpc_id
  subnet_ids                     = module.vpc.private_subnets
  cluster_endpoint_public_access = true

  eks_managed_node_group_defaults = {
    ami_type = "AL2_x86_64"

  }

  eks_managed_node_groups = {
    one = {
      name = "${var.project}-${var.environment}-k8-ng-1"

      instance_types = [var.ec2-instance-type]

      min_size     = var.min_k8_nodes
      max_size     = var.max_k8_nodes
      desired_size = var.desired_k8_nodes
    }

    two = {
      name = "${var.project}-${var.environment}-k8-ng-2"

      instance_types = [var.ec2-instance-type]

      min_size     = 1
      max_size     = 2
      desired_size = 1
    }

    tags = {
      Environment = var.environment
      Terraform   = "true"
      Project     = var.project
    }

  

    #bootstarp the EC2 with startup scripts
    #enable_bootstrap_user_data    = true
    #user_data_template_path       = "./userData.sh"
  }
}