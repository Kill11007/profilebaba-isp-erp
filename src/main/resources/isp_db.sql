create table if not exists internet_plans(
	id bigint primary KEY AUTO_INCREMENT,
    name varchar(100) not null,
    product_code varchar(50) null,
    hsn_code varchar(50) null,
    gst_percent decimal(4, 2) UNSIGNED,
    price decimal(8, 2) UNSIGNED not null,
    discount decimal(8, 2) unsigned,
    additional_charge decimal(8, 2) unsigned,
    active boolean not null default false
);

create table if not exists customer_roles(
  id int primary key auto_increment,
  role_name varchar(50) not null unique,
  created_date datetime not null,
  updated_date datetime not null
);

create table if not exists employee_roles(
  id int primary key auto_increment,
  role_name varchar(50) not null unique,
  created_date datetime not null,
  updated_date datetime not null
);

create table if not exists customers(
	id bigint primary key auto_increment,
    name varchar(100) not null,
    billing_name varchar(100),
    billing_area varchar(255) ,
    primary_mobile_no varchar(15),
    secondary_mobile_no varchar(15),
    email varchar(200),
    security_deposit decimal(8,2) unsigned,
    address text,
    gst_no varchar(25),
    customer_code varchar(50),
    remark varchar(255),
    active boolean not null default true,
    user_id bigint,
    role_id int,
    foreign key(role_id) references customer_roles(id)
);

create table if not exists hardware_details(
	id bigint primary key auto_increment,
    customer_id bigint not null,
	router varchar(255),
    ip varchar(20),
    mac varchar(50),
    membership_number varchar(50),
    foreign key(customer_id) references customers(id)
);

-- alter table hardware_details add foreign key(customer_id) references customers(id);

create table if not exists billing_details(
	customer_id bigint primary key,
	start_date date not null,
    opening_outstanding_balance decimal(8, 2) unsigned not null default 0,
    opening_advance_balance decimal(8, 2) unsigned not null default 0,
    monthly_additional_charge decimal(8, 2) unsigned not null default 0,
    monthly_discount decimal(8, 2) unsigned not null default 0,
    bill_duration enum('EOEM', 'DAYS', 'MONTHS') not null default 'EOEM',  -- end of every month
    bill_duration_value integer null,
    bill_type enum('PRE_PAID', 'POST_PAID') not null default 'PRE_PAID',
    gst_type enum('NA', 'IGST', 'CGST_SGST') not null default 'CGST_SGST',
	foreign key(customer_id) references customers(id)
);
-- drop table subscriptions; 
create table if not exists subscriptions(
	id bigint primary key auto_increment,
	customer_id bigint,
    plan_id bigint,
    fixed_bill_amount decimal(8, 2) unsigned,
    quantity int not null default 1,
    start_date date,
    end_date date,
    period int,
    future_days boolean default true,
    status enum('ACTIVE', 'IN_ACTIVE') not null default 'ACTIVE',
    foreign key(plan_id) references internet_plans(id),
    foreign key(customer_id) references customers(id)
);

create table if not exists employees(
	id bigint primary key auto_increment,
    name varchar(50) not null,
    address varchar(255) null,
    email varchar(100),
    phone varchar(20),
    user_id bigint not null unique,
    role_id int,
    foreign key(role_id) references employee_roles(id)
);
create table if not exists payments(
	id bigint primary key auto_increment,
    receipt_no varchar(8) not null,
    customer_id bigint not null,
    collection_agent_id bigint null,
    collected_by varchar(15) not null,
    payment_mode enum('ONLINE', 'CASH') not null default 'CASH',
    previous_balance decimal(8, 2),
    paid_amount decimal(8, 2),
    discount decimal(8, 2) default 0,
    net_amount decimal(8, 2) not null,
    remaining_amount decimal(8, 2) not null,
    comment varchar(255),
    payment_date_time datetime not null,
    foreign key(collection_agent_id) references employees(id),
    foreign key(customer_id) references customers(id)
);
create table if not exists bills(
	id bigint primary key auto_increment,
    bill_no varchar(20) not null,
    invoice_date date not null,
    from_date date not null,
    to_date date not null,
    customer_id bigint not null,
    total decimal(8, 2) not null,
    updated_date date not null,
    foreign key(customer_id) references customers(id)
);
-- alter table isp_2.bills add column udpated_date date not null;
-- alter table isp_2.bill_items add column  created_date datetime not null;
create table if not exists bill_items(
	id bigint primary key auto_increment,
    bill_id bigint not null,
    name varchar(255) not null,
    item_id bigint,
    quantity integer not null,
    hsn_code varchar(25),
    discount decimal(8, 2) default 0,
    additional_charges decimal(8, 2) default 0,
    gst_percent int default 0,
    gst_amount decimal(8, 2) default 0,
    amount decimal(8, 2) not null,
    created_date datetime not null,
    foreign key(bill_id) references bills(id)
);

create table adjusted_balance(
	id bigint primary key auto_increment,
	dated date not null,
    customer_id bigint not null,
    remark varchar(255),
    amount decimal(8, 2) not null,
    previous_balance decimal(8, 2),
    grand_total decimal(8, 2),
    foreign key(customer_id) references customers(id)
);

create table if not exists balance_sheet(
	id bigint primary key auto_increment,
    dated date not null,
    transaction_type enum ('BILL', 'PAYMENT', 'ADJUSTED_BALANCE') not null,
    transaction_id bigint,
	txn_amount decimal(8, 2),
    final decimal(8, 2),
    customer_id bigint not null,
    foreign key (customer_id) references customers(id)
);

-- alter table isp_2.balance_sheet add column customer_id bigint not null;
-- alter table isp_2.balance_sheet add foreign key (customer_id) references isp_2.customers(id);
create table service_areas(
	id bigint primary key auto_increment,
    name varchar(255) not null
);

create table if not exists employee_service_areas(
	service_area_id bigint,
    employee_id bigint,
    foreign key (service_area_id) references service_areas(id),
    foreign key (employee_id) references employees(id)
);

create table if not exists customer_follow_up(
	id bigint primary key auto_increment,
  reason text,
	follow_up_date date,
  customer_id bigint not null,
  foreign key (customer_id) references customers(id),
  updated_date datetime
);

create table if not exists complaints(
	id bigint primary key auto_increment,
    customer_id bigint,
    employee_id bigint,
    message text,
    status varchar(20),
    start_date datetime,
    updated_date datetime,
    created_by_user_id bigint,
    employee_remark text,
    complaint_number varchar(10),
    foreign key(customer_id) references customers(id),
    foreign key(employee_id) references employees(id)
);

create table if not exists customer_care_contacts(
  id bigint primary key auto_increment,
  name varchar(50) not null unique,
  contact_number varchar(20) not null unique
);

create table if not exists user_type_role_permissions(
  id bigint primary key auto_increment,
  user_type varchar(50) not null,
  customer_role_id int,
  employee_role_id int,
  permission_id bigint not null,
  foreign key(customer_role_id) references customer_roles(id),
  foreign key(employee_role_id) references employee_roles(id)
);
-- drop table complaints;
-- alter table user_type_role_permissions modify column user_type varchar(50) not null;