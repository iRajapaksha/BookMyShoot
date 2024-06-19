provider "aws" {
  region = "us-west-2"
}

resource "aws_instance" "app_server" {
  ami           = "ami-0cf2b4e024cdb6960" # Update with your preferred AMI
  instance_type = "t2.micro"

  tags = {
    Name = "BookMyShoot"
  }

  key_name = "bookmyshoot" # Ensure this key pair exists in your AWS account

  provisioner "remote-exec" {
    inline = [
      "sudo apt-get update",
      "sudo apt-get install -y docker.io",
      "sudo apt-get install -y docker-compose",
      "sudo usermod -aG docker ubuntu",
      "newgrp docker"
    ]
    connection {
      type        = "ssh"
      user        = "ubuntu"
      private_key = file("~/.ssh/bookmyshoot.pem") # Path to your private key
      host        = self.public_ip
    }
  }
}

output "instance_ip" {
  value = aws_instance.app_server.public_ip
}
