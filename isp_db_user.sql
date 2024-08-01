create database if not exists BOOTSTRAP;
use BOOTSTRAP;
CREATE USER if not exists 'BOOTSTRAP'@'localhost' IDENTIFIED BY '';
GRANT INSERT, UPDATE, DELETE, SELECT ON BOOTSTRAP.* TO 'BOOTSTRAP'@'localhost';
INSERT INTO `isp_admin_db`.`tenants` (`id`, `db`, `tenant_id`, `password`, `url`) VALUES ('1', 'BOOTSTRAP', 'BOOTSTRAP', '', 'jdbc:mysql://localhost:3306/BOOTSTRAP');