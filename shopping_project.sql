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
create table if not exists Product(
	product_id			int auto_increment primary key,
    name				varchar(20),
	description			varchar(40),
    wholesale_price		double,
    retail_price		double,
    stock_quantity		int
);

insert into Product(name, description, wholesale_price, retail_price, stock_quantity) values
("book", "Harry Potter series", 19.50, 25.00, 5),
("bookcase", "White 3-shelf bookcase", 23.15, 27.00, 9),
("phone", "Iphone XR", 600.00, 900.00, 1),
("Headphone", "Wireless Sony headphone", 200.00, 250.00, 0),
("Laptop", "Dell Laptop", 600.00, 700.00, 2);

drop table if exists ProductWatchlist;
create table if not exists ProductWatchlist(
	productWatchlist_id	int auto_increment primary key,
	product_id			int,
	user_id	 			int,
    foreign key (user_id) references User(user_id),
    foreign key (product_id) references Product(product_id)
);

drop table if exists Order_table;
create table if not exists Order_table(
	order_id			int auto_increment primary key,
	user_id	 			int,
    order_status		varchar(20) default "Processing",
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
select * from Product;
select * from Order_table;
select * from OrderItem;
select * from ProductWatchlist;

-- update Order_table set order_status = "Canceled" where order_id = 5;
-- update Order_table set order_status = "Processing" where order_id = 1;
-- update Product set stock_quantity = 1 where product_id = 4;

-- select sum(oi.purchased_price*oi.purchased_quantity) as spent, u.email from OrderItem oi
-- join order_table o on o.order_id = oi.order_id 
-- join User u on u.user_id = o.user_id
-- where o.order_status = "Completed"
-- group by u.email
-- order by spent desc
-- limit 2;

-- select sum(purchased_quantity) as quantity, p.product_id, p.name, p.description
-- from Product p
-- join OrderItem oi on p.product_id = oi.product_id
-- join Order_table o on o.order_id = oi.order_id
-- where o.order_status = "Completed"
-- group by p.product_id
-- order by quantity desc;


-- select o.date_placed, p.product_id, p.name, p.description, oi.purchased_quantity, oi.purchased_price
-- from Product p
-- join OrderItem oi on p.product_id = oi.product_id
-- join Order_table o on o.order_id = oi.order_id
-- where o.user_id = 2 and o.order_status != "Canceled"
-- order by date_placed desc;

-- select Sum((oi.purchased_quantity*(oi.purchased_price-oi.wholesale_price))) as profit, p.product_id, p.name, p.description
-- from Product p
-- join OrderItem oi on p.product_id = oi.product_id
-- join Order_table o on o.order_id = oi.order_id
-- where o.order_status = "Completed"
-- group by p.product_id
-- order by profit desc;
