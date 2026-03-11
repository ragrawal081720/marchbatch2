# Configure the AWS Provider
terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
    # backend "s3" {
    #     access_key = "AKIA5Z6Q2
    #     secret_key = "n2mLh7sQy8ZtXo5v1u9w3e4r5t6y7u8i9o0p
    #     bucket     = "terraform-state-bucket-2024"
    #     key        = "terraform.tfstate"
    #     region     = "ap-south-1"
    # }
  }
}

provider "aws" {
  region  = var.aws_region
  profile = var.aws_profile
}
