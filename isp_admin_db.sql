create database if not exists isp_admin_db;
CREATE USER 'isp_admin_local'@'localhost' IDENTIFIED BY 'isp_admin_pwd';
use isp_admin_db;
create table if not exists isp_admin_db.isps (
	id bigint primary KEY AUTO_INCREMENT,
    business_name varchar(50) unique,
    user_id bigint not null unique,
    foreign key (user_id) references users(id)
);
create table if not exists isp_admin_db.tenants(
	id int primary key auto_increment,
    db varchar(100) not null,
    password varchar(255) not null,
    url varchar(255) not null
);

create table if not exists isp_admin_db.users(
	id bigint primary key auto_increment,
    tenant_id varchar(20) not null,
    phone_number varchar(15),
    password varchar(255),
    remember_me boolean not null default false,
    is_phone_number_verified boolean not null default false,
    user_type enum ('ISP', 'EMPLOYEE', 'ADMIN') not null
);
-- alter table users drop column secondary_id;
-- drop table isp_admin_db.users;

create table if not exists isp_admin_db.permissions(
	id bigint primary key auto_increment,
    name varchar(255) not null
);

create table if not exists isp_admin_db.roles(
	id int primary key auto_increment,
    name varchar(50) not null
);

create table if not exists isp_admin_db.user_roles(
	role_id int,
    user_id bigint,
    foreign key(role_id) references isp_admin_db.roles(id),
    foreign key(user_id) references isp_admin_db.users(id)
);
create table if not exists isp_admin_db.user_permissions(
	permission_id bigint,
    user_id bigint,
    foreign key (permission_id) references permissions(id),
    foreign key (user_id) references users(id)
);