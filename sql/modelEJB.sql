-- drop tables
drop table user_coins;
drop table users;
drop table coins;
drop table countries;
drop sequence user_seq;
drop sequence coin_seq;

-- create tables
Create table users (
  user_id Number(20,0) NOT NULL ,
  username Varchar2(50),
  password Varchar2(50),
  admin Number(1)
);

Create table coins (
  coin_id Number(20,0) NOT NULL ,
  country Number(20,0),
  year Number(20,0),
  name Varchar2(50),
  metall Varchar2(50),
  diameter_mm Number(20,0),
  value Number(20,0),
  weight Number(20,0),
  fullname Varchar2(50)
);

Create table user_coins (
  user_id Number(20,0) NOT NULL ,
  coin_id Number(20,0) NOT NULL);

Create table countries (
	country_id Number(20,0) NOT NULL ,
	name Varchar2(50)
);

-- create primary keys
Alter table users add primary key (user_id);
Alter table coins add primary key (coin_id);
Alter table countries add primary key (country_id);


-- create foreign keys    
Alter table user_coins
	add Constraint r_11 foreign key (user_id)
	references users (user_id) on delete cascade;
Alter table coins
	add Constraint r_14 foreign key (country)
	references countries (country_id) on delete cascade;
Alter table user_coins
	add Constraint r_12 foreign key (coin_id)
	references coins (coin_id) on delete cascade;

-- create sequences
CREATE SEQUENCE user_seq
START WITH 2
INCREMENT BY 1
NOMAXVALUE;

CREATE SEQUENCE coin_seq
START WITH 3
INCREMENT BY 1
NOMAXVALUE;

commit;