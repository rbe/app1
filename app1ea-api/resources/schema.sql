create table email (
  id int,
  version int,
  from_address varchar(100),
  to_address varchar(100),
  subject varchar(100),
  body longtext,
  content_type varchar(50),
  sent_date datetime,
  sent_successfully boolean,
  primary key (id)
);

create table person (
  id int,
  version int,
  email_address varchar(100),
  firstname varchar(20),
  surname varchar(50),
  address varchar(100),
  zipcode varchar(20),
  city varchar(100),
  country varchar(100),
  notes longtext,
  primary key (id)
);

create table person_role (
  id int,
  email_address varchar(100),
  role_name varchar(20),
  primary key (email_address, role_name)
);

create table company (
  id int,
  version int,
  customer_number int,
  company_name varchar(200),
  company_name2 varchar(200),
  address varchar(100),
  zipcode varchar(20),
  city varchar(100),
  country varchar(100),
  notes longtext,
  primary key (customer_number)
);
