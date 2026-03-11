/*
module "s3" {
  source = "./s3"
}

module "ec2" {
  source = "./ec2"
  ami = var.ami
  instance_type = var.instance_type
}
*/

module "ecr" {
  source               = "./ecr"
  repository_name      = var.ecr_repository_name
  image_tag_mutability = var.ecr_image_tag_mutability
  scan_on_push         = var.ecr_scan_on_push
  force_delete         = var.ecr_force_delete
  environment          = var.environment
}