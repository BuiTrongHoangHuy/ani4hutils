output "config" {
  value = {
    primary_console_url = try(aws_mq_broker.broker.instances[0].console_url, "")
    primary_ssl_endpoint = try(aws_mq_broker.broker.instances[0].endpoints[0], "")
    primary_amqp_ssl_endpoint = try(aws_mq_broker.broker.instances[0].endpoints[1], "")
    primary_stomp_ssl_endpoint = try(aws_mq_broker.broker.instances[0].endpoints[2], "")
    primary_mqtt_ssl_endpoint = try(aws_mq_broker.broker.instances[0].endpoints[3], "")
    primary_wss_endpoint = try(aws_mq_broker.broker.instances[0].endpoints[4], "")
    password = random_password.password.result
  }
}