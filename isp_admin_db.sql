create database if not exists isp_admin_db;
CREATE USER 'isp_admin_local'@'localhost' IDENTIFIED BY 'isp_admin_pwd';
use isp_admin_db;
GRANT ALL PRIVILEGES ON *.* TO 'isp_admin_local'@'localhost';
GRANT GRANT OPTION ON *.* TO 'isp_admin_local'@'localhost';
create table if not exists isp_admin_db.users(
	id bigint primary key auto_increment,
    tenant_id varchar(20) not null,
    phone_number varchar(15),
    password varchar(255),
    remember_me boolean not null default false,
    is_phone_number_verified boolean not null default false,
    user_type enum ('ISP', 'EMPLOYEE', 'ADMIN', 'CUSTOMER') not null
);

create table if not exists isp_admin_db.isps (
	id bigint primary KEY AUTO_INCREMENT,
    business_name varchar(50) unique,
    user_id bigint not null unique,
    phone varchar(20) not null,
    plan_id bigint,
    foreign key (user_id) references users(id)
);

create table if not exists isp_admin_db.tenants(
	id int primary key auto_increment,
    tenant_id varchar(50) unique,
    db varchar(100) not null,
    password varchar(255) not null,
    url varchar(255) not null
);

create table if not exists isp_admin_db.permissions(
	id bigint primary key auto_increment,
    name varchar(255) not null,
    feature_name varchar(255) null,
    parent_id bigint null,
    url varchar(1000) null,
    icon varchar(255) null,
    foreign key(parent_id) references isp_admin_db.permissions(id)
);

-- alter table isp_admin_db.tenants add column tenant_id varchar(50) unique;
-- alter table isp_admin_db.permissions add column  url varchar(1000) null;
-- alter table isp_admin_db.permissions add column  icon varchar(255) null;
-- alter table isp_admin_db.users modify column user_type enum ('ISP', 'EMPLOYEE', 'ADMIN', 'CUSTOMER') not null;
-- alter table isp_admin_db.isps add column phone varchar(20) not null;
-- drop table isp_admin_db.users;

-- user_role_features table is used to store user_type with the Default feature they can access. 
-- This access however can be modified by adding features in user_permissions table
create table if not exists isp_admin_db.user_role_features(
	id bigint primary key auto_increment,
    user_type enum ('ISP', 'EMPLOYEE', 'ADMIN', 'CUSTOMER') not null,
    permission_id bigint not null,
    foreign key (permission_id) references permissions(id)
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

create table if not exists isp_admin_db.plans(
	id bigint primary key auto_increment,
    name varchar(255),
    rate decimal,
    plan_type varchar(25),
    plan_description text,
    is_default boolean,
    created_date_time datetime not null,
    updated_time date not null
);
-- alter table isp_admin_db.plans add column is_default boolean null;
create table if not exists isp_admin_db.isp_plans(
	id bigint primary key auto_increment,
	isp_id bigint,
    plan_id bigint,
    tenant_id int not null,
    start_date_time datetime not null,
    updated_date_time datetime not null,
    end_date_time datetime,
    foreign key (isp_id) references isps(id),
    foreign key (plan_id) references plans(id),
    foreign key (tenant_id) references tenants(id)
);

create table if not exists isp_admin_db.plan_permissions(
  id bigint primary key auto_increment,
    plan_id bigint not null,
    permission_id bigint not null,
    foreign key (permission_id) references permissions(id),
    foreign key (plan_id) references plans(id)
);