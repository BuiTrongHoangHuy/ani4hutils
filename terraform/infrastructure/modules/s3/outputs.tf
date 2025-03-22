output "image_cdn" {
  value = aws_cloudfront_distribution.image_distribution.domain_name
}