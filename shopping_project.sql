drop database if exists shopping_project;
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
    value				varchar(30) default "read",
	user_id	 			int,
    foreign key (user_id) references User(user_id)
);

insert into Permission(value, user_id) values
("read", 1),
("read", 2),
("read", 3), ("write", 3), ("delete", 3), ("update", 3);

drop table if exists Product;
create table if not exists Permission(
	product_id			int auto_increment primary key,
    name				varchar(20),
	description			varchar(40),
    wholesale_price		double,
    retail_price		double,
    stock_quantity		int
);

drop table if exists ProductWatchlist;
create table if not exists Permission(
	product_id			int,
	user_id	 			int,
    foreign key (user_id) references User(user_id),
    foreign key (product_id) references Product(product_id)
);

drop table if exists Order_table;
create table if not exists Order_table(
	order_id			int auto_increment primary key,
	user_id	 			int,
    order_status		varchar(20),
    date_placed			timestamp default CURRENT_TIMESTAMP,
    foreign key (user_id) references User(user_id)
);

drop table if exists OrderItem;
create table if not exists OrderItem(
	order_item_id		int auto_increment primary key,
    order_id			int,
	product_id			int,
	wholesale_price		double,
    purchased_price		double,
    purchased_quantity	int,
    foreign key (product_id) references Product(product_id),
	foreign key (order_id) references Order_table(order_id)
);

select * from User;
select * from Permission;



