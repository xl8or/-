BOGUSdrop table T_PERSON if exists;
create table T_PERSON (ID integer identity primary key, FIRST_NAME varchar(50), LAST_NAME varchar(50) not null);
