use mayank;
show databases;
DROP USER 'mayank'@'localhost';
drop database mayank;
SELECT User FROM mysql.user;
show tables;
use isp_2;
select * from isps;
select * from isp_2.plans;
select * from plans;
select * from employees;
describe hardware_details;
describe billing_details;
describe employee_permissions;
show create table employees;

select * from customers;

select * from billing_details;
select * from hardware_details;
select * from subscriptions;
select * from bills;
select * from bill_items;
select * from payments;
select * from service_areas;
select * from balance_sheet;
select * from complaints;
-- alter table mayank.complaints add column (complaint_number varchar(10));
-- alter table mayank.complaints modify column employee_remark text;
select * from isp_admin_db.isps;
select * from isp_admin_db.tenants;
select * from isp_admin_db.users;
select * from isp_admin_db.plans;
select * from isp_admin_db.permissions;
-- alter table isp_admin_db.permissions add column (feature_name varchar(255));
-- alter table isp_admin_db.permissions add column (parent_id bigint);
-- alter table isp_admin_db.permissions add constraint foreign key (parent_id) references isp_admin_db.permissions(id);
select * from isp_admin_db.user_permissions;
select * from isp_admin_db.user_roles;
select * from isp_admin_db.roles;
select * from isp_admin_db.plan_permissions;
