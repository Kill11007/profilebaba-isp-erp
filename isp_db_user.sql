create database BOOTSTRAP;
use BOOTSTRAP;
CREATE USER 'BOOTSTRAP'@'localhost' IDENTIFIED BY '';
GRANT INSERT, UPDATE, DELETE, SELECT ON BOOTSTRAP.* TO 'BOOTSTRAP'@'localhost';
