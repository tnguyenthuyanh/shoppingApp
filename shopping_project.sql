create database if not exists shopping_project;
use shopping_project;

drop table if exists User;
create table if not exists User(
	user_id		int auto_increment primary key,
    email		varchar(20) unique,
    password	varchar(20),
    is_admin	boolean default 0
);

insert into User(email, password, is_admin) values
("1@test.com", "111111", false),
("2@test.com", "222222", false),
("admin", "admin", true);

drop table if exists Permission;
create table if not exists Permission(
	permission_id		int auto_increment primary key,
    value				varchar(30),
	user_id	 			int,
    foreign key (user_id) references User(user_id)
);

insert into Permission(value, user_id) values
("read", 1),
("read", 2),
("read", 3), ("write", 3), ("delete", 3), ("update", 3);
