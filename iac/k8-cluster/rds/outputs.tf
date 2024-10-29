output "mysql_db_url" {
  value = aws_db_instance.mysql.endpoint
}

output "mysql_host" {
  value = aws_db_instance.mysql.address
}