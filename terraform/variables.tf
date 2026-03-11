variable "ami"{
    type = string
    description = "The AMI ID to use for the EC2 instance"
}

variable"instance_type" {
    type = string
    description = "The type of EC2 instance to create"
    default = "t2.micro"
}

variable "ecr_repository_name" {
    type = string
    description = "Name of the ECR repository"
    default = "marchbatch2-app"
}

variable "ecr_image_tag_mutability" {
    type = string
    description = "Whether image tags are mutable or immutable"
    default = "MUTABLE"
}

variable "ecr_scan_on_push" {
    type = bool
    description = "Enable image scanning on push"
    default = true
}

variable "ecr_force_delete" {
    type = bool
    description = "Delete repository even when images exist"
    default = false
}

variable "environment" {
    type = string
    description = "Environment tag value"
    default = "Dev"
}

variable "aws_region" {
    type = string
    description = "AWS region for provider operations"
    default = "ap-south-1"
}

variable "aws_profile" {
    type = string
    description = "Optional AWS shared config profile name"
    default = null
}