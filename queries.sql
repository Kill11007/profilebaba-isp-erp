use mayank;
show databases;
-- DROP USER 'mayank'@'localhost';
-- drop database mayank;
SELECT User FROM mysql.user;
show tables;
use isp_2;
select * from isps;
select * from isp_2.plans;
select * from internet_plans;
select * from employees;
-- alter table employees add column (role_id int null);
-- alter table employees add constraint foreign key (role_id) references employee_role(id);
-- alter table employees drop FOREIGN KEY employees_ibfk_5;
describe hardware_details;
describe billing_details;
describe employee_permissions;
show create table employees;

select * from customers;
-- alter table customers drop column role_id;
-- alter table customers add column (role_id int null);
-- alter table customers add constraint foreign key (role_id) references customer_role(id);
select * from customer_role;
select * from employee_role;
select * from user_type_role_permissions;
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
-- alter table isp_admin_db.isps drop column plan_id;
-- alter table subscriptions drop FOREIGN KEY subscriptions_ibfk_1;
select * from isp_admin_db.tenants;
select * from isp_admin_db.users;
select * from isp_admin_db.plans;
select * from isp_admin_db.permissions;
-- alter table isp_admin_db.permissions add column (icons varchar(255));
-- alter table isp_admin_db.permissions add column (parent_id bigint);
-- alter table subscriptions add constraint foreign key (plan_id) references internet_plans(id);
select * from isp_admin_db.user_permissions;
select * from isp_admin_db.user_roles;
select * from isp_admin_db.roles;
select * from isp_admin_db.plan_permissions;
select * from isp_admin_db.isp_plans;
-- alter table isp_admin_db.isp_plans add column (tenant_id int not null);
-- alter table isp_admin_db.isp_plans drop column tenant_id;
-- alter table isp_admin_db.isp_plans add constraint foreign key (tenant_id) references isp_admin_db.tenants(id);
select * from isp_admin_db.user_role_features;

-- drop table plans;

select
        customer0_.id as id1_9_,
        customer0_.active as active2_9_,
        customer0_.address as address3_9_,
        customer0_.billing_area as billing_4_9_,
        customer0_.billing_name as billing_5_9_,
        customer0_.customer_code as customer6_9_,
        customer0_.role_id as role_id15_9_,
        customer0_.email as email7_9_,
        customer0_.gst_no as gst_no8_9_,
        customer0_.name as name9_9_,
        customer0_.primary_mobile_no as primary10_9_,
        customer0_.remark as remark11_9_,
        customer0_.secondary_mobile_no as seconda12_9_,
        customer0_.security_deposit as securit13_9_,
        customer0_.user_id as user_id14_9_ 
    from
        customers customer0_ 
    where
        customer0_.user_id=32