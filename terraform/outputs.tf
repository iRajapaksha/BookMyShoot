output "instance_ip" {
  description = "The public IP of the app server"
  value       = aws_instance.app_server.public_ip
}
