variable "repository_name" {
  type        = string
  description = "Name of the ECR repository"
}

variable "image_tag_mutability" {
  type        = string
  description = "Whether image tags are mutable or immutable"
  default     = "MUTABLE"
}

variable "scan_on_push" {
  type        = bool
  description = "Enable image scanning when images are pushed"
  default     = true
}

variable "force_delete" {
  type        = bool
  description = "Delete repository even if it contains images"
  default     = false
}

variable "environment" {
  type        = string
  description = "Environment name used in tags"
  default     = "Dev"
}
