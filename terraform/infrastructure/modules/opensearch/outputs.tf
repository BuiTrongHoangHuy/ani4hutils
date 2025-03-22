output "config" {
  value = {
    user = aws_opensearch_domain.opensearch.advanced_security_options[0].master_user_options[0].master_user_name
    password = aws_opensearch_domain.opensearch.advanced_security_options[0].master_user_options[0].master_user_password
    cluster_endpoint = aws_opensearch_domain.opensearch.endpoint
    kibana_endpoint = aws_opensearch_domain.opensearch.dashboard_endpoint
  }
}

