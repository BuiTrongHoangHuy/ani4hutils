terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
}

resource "aws_s3_bucket" "film_master_storage" {
  bucket = "${var.project}-film-master"
  force_destroy = true
}

resource "aws_s3_bucket" "film_storage" {
  bucket = "${var.project}-film-storage"
  force_destroy = true
}


resource "aws_s3_bucket" "image_storage" {
  bucket = "${var.project}-image-storage"
  force_destroy = true
}
resource "aws_s3_bucket_acl" "acl" {
  bucket = aws_s3_bucket.image_storage.id
  depends_on = [aws_s3_bucket_ownership_controls.example]
  acl = "private"
}
resource "aws_s3_bucket_ownership_controls" "example" {
  bucket = aws_s3_bucket.image_storage.id
  rule {
    object_ownership = "BucketOwnerPreferred"
  }
}
resource "aws_s3_bucket_policy" "cloudfront_s3_bucket_policy" {
  bucket = aws_s3_bucket.image_storage.id
  policy = jsonencode({
    "Version": "2008-10-17",
    "Id": "PolicyForCloudFrontPrivateContent",
    "Statement": [
      {
        "Sid": "1",
        "Effect": "Allow",
        "Principal": {
          "AWS": aws_cloudfront_origin_access_identity.resource.iam_arn
        },
        "Action": "s3:GetObject",
        "Resource": "${aws_s3_bucket.image_storage.arn}/*"
      }
    ]
  })
}

resource "aws_cloudfront_origin_access_identity" "resource" {

}

resource "aws_cloudfront_distribution" "image_distribution" {
  enabled = true
  default_cache_behavior {

    allowed_methods        = ["GET", "HEAD", "OPTIONS"]
    cached_methods         = ["GET", "HEAD", "OPTIONS"]
    target_origin_id       = aws_s3_bucket.image_storage.id
    viewer_protocol_policy = "redirect-to-https"
    compress               = true
    forwarded_values {
      query_string = false
      cookies {
        forward = "none"
      }
    }
  }

  restrictions {
    geo_restriction {
      restriction_type = "none"
    }
  }
  price_class = "PriceClass_All"
  origin {
    domain_name = aws_s3_bucket.image_storage.bucket_regional_domain_name
    origin_id   = aws_s3_bucket.image_storage.id
    s3_origin_config {
      origin_access_identity = aws_cloudfront_origin_access_identity.resource.cloudfront_access_identity_path
    }
  }
  viewer_certificate {
    cloudfront_default_certificate = true
  }
}

resource "aws_s3_bucket" "admin_web" {
  bucket = "${var.project}-admin-web"
  force_destroy = true
}

