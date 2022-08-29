use isp_admin_db;
select * from isp_admin_db.users;
select * from isp_admin_db.tenants;
select * from isp_admin_db.isps;
SELECT * FROM mysql.user;
CREATE USER 'isp_2'@'localhost' IDENTIFIED BY 'password';
use isp_3;
show tables;
drop database isp_2;
drop user 'ISP_2'@'localhost';
GRANT ALL ON *.* TO 'isp_admin_local'@'localhost';
GRANT ALL privileges on isp_2 TO 'ISP_2'@'localhost';
flush privileges;
create table isp_2.temp(id int);
delete from isp_admin_db.isps where business_name = 'ISP_2';
delete from isp_admin_db.users where secondary_id = 40;
-- insert into isp_admin_db.isps (business_name) select business_name from isp_db.isps;

-- insert into isp_admin_db.users (tenant_id, secondary_id, phone_number, password, remember_me, is_phone_number_verified)
-- select 1, id, phone_number, password, remember_me, is_phone_number_verified from isp_db.isps;