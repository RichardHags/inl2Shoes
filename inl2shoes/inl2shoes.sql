drop database if exists inl1shoes;
create database inl1shoes;
use inl1shoes;

create table customer
(id int not null auto_increment primary key,
firstName varchar(30) null,
lastName varchar(30) null,
postalAdress varchar(50) null,
postalCode int null,
postalRegion varchar(20) null);
-- personnummer, email, telefonnummer

create table brand
(id int not null auto_increment primary key,
brandName varchar(30) not null);

create table category
(id int not null auto_increment primary key,
categoryName varchar(40) not null);

create table shoes
(id int not null auto_increment primary key,
shoeName varchar(20) not null,
size int not null,
color ENUM ('Red', 'Black', 'White', 'Blue', 'Green', 'Gray') not null,
price double not null,
brandId int not null,
storageQuantity int not null,
foreign key (brandId) references brand(id) on delete cascade);

create table partOf
(id int not null auto_increment primary key,
categoryId int not null,
shoesId int not null,
foreign key (categoryId) references category(id),
foreign key (shoesId) references shoes(id));

create table review
(id int not null auto_increment primary key,
grade ENUM ('Mycket nöjd', 'Nöjd', 'Ganska nöjd', 'Missnöjd') not null,
comments varchar (255) null,
customerId int not null,
shoesId int not null,
foreign key (customerId) references customer(id),
foreign key (shoesId) references shoes(id) on delete cascade);

create table orders
(id int not null auto_increment primary key,   
created date null,
expedited boolean,
lastUpdated timestamp default 0 ON UPDATE CURRENT_TIMESTAMP,
customerId int not null,
foreign key (customerId) references customer(id));

create table orderInfo
(id int not null auto_increment primary key,
quantity int not null,
shoesId int not null,
ordersId int not null,
foreign key (shoesId) references shoes(id),
foreign key (ordersId) references orders(id) on delete cascade);

create table outOfStock
(id int not null auto_increment primary key,
outOfStockDate datetime not null DEFAULT CURRENT_TIMESTAMP,
shoesOldId int not null);

insert into customer (firstName, lastName, postalAdress, postalCode, postalRegion) values
('Richard', 'Hagström', 'EnAdress 99', 14244, 'Haninge'),
('Thomas', 'Bylund', 'EnAdress 24', 15243, 'Tumba'),
('Kajsa', 'Holm', 'EnAdress 35', 12243, 'Skogås'),
('Felix', 'Aronsson', 'EnAdress 55', 17511, 'Täby'),
('Sara', 'Sundström', 'EnAdress 112', 11001, 'Djursholm');

insert into brand (brandName) values
('Ecco'),
('Adidas'),
('Nike'),
('Bughatti'),
('Puma'),
('Riptide'),
('New balance'),
('Vagabond');

insert into category (categoryName) values
('Sneaker'),
('Skateskor'),
('Löparskor'),
('Fotbollskor'),
('Stövlar'),
('Sandaler'),
('Toflor'),
('Basketskor');

insert into shoes (shoeName, size, color, price, brandId, storageQuantity) values
('Lightning', 38, 'black', 899.90, 1, 5),
('Thunder', 42, 'white', 599.90, 2, 2),
('Storm', 36, 'black', 1299.90, 3, 15),
('Blaze', 44, 'gray', 639.90, 4, 55),
('Freeze', 39, 'green', 1200, 5, 7),
('Fury', 42, 'white', 999.90, 8, 9),
('Tornado', 43, 'red', 400, 7, 1),
('Earthquake', 38, 'black', 879.90, 1, 23);

insert into partOf (categoryId, shoesId) values
(6, 1),
(4, 2),
(3, 3),
(1, 4),
(8, 5),
(5, 6),
(2, 7),
(6, 8);

insert into orders (created, expedited, customerId) values
('2014-05-11', true, 1),
('2015-03-23', true, 2),
('2014-11-21', true, 3),
('2017-02-16', false, 4),
('2016-12-01', false, 2),
('2017-09-04', false, 1);

insert into orderInfo (quantity, ordersId, shoesId) values
(2, 1, 1),
(1, 2, 2),
(4, 3, 3),
(1, 4, 4),
(2, 5, 5),
(1, 6, 6),
(3, 1, 7),
(1, 2, 8);

insert into review (grade, comments, customerId, shoesId) values
('Nöjd', null, 1, 1),
('Missnöjd', null, 2, 2),
('Ganska nöjd', 'meh', 3, 3),
('Mycket nöjd', '', 4, 4);

-- Index:
-- Märken(brandName), det känns som något som man söker ofta på eftersom ett märke kan ha väldigt många olika skor. Ett annat alternativ
-- skulle kunna vara shoes.size eftersom det också är något man skulle behöva söka ofta på i en databas av det här slaget. Sedan ett
-- tredje alternativ skulle vara efternamn eller personnummer(om man har det) som är något som man kanske behöver söka på ofta.
create index IX_brandName on brand(brandName);

-- referensintegritet:
-- I shoes tabellen har jag delete on cascade vid brand då jag tänker att om ett märke försvinner från ens lagersaldo 
-- (att man inte kan / vill sälja ett viss märke) så bör även skorna som tillhör det märket försvinna.
-- samma sak vid review så tänkte jag att om en viss sko försvinner / tas bort så ska även reviewen försvinna för den skon, då det känns
-- som att det inte finns någon anledning att den finns kvar.
-- Sedan vid OrderInfo så kände jag att om en hel order tas bort så finns det ingen anledning att spara en orderInfo. 
-- Jag funderade även på att ha customerId som delete on cascade, men satte istället null på alla variabler i custommer tabellen då 
-- jag tyckte att om en kund vill att man tar bort hens info så borde ändå kundId finnas kvar, men all annan info raderas. 
-- Så hens unika Id finns kvar och kan inte tas över av någon annan kund men infon om kunden är borta (som namn, telefonnummer, 
-- adress osv).

-- Angående 3NF:
-- Så på customer tabellen så valde jag att inte använda normalisering för att jag kände att just i den här uppgiften så behövdes
-- det inte att dela upp postnummer och ort då uppgiften inte är så stor. Samt att det är skönt att kunna se alla de uppgifterna i 
-- samma tabell. Skulle jag dock jobba med en större databas där man kanske också hanterar leverantörer och orter med alla dess olika
-- adresser och postnummer m.m så skulle jag tänka igenom mitt beslut mer och säkert dela upp uppgifterna. Så vitt jag förstår så 
-- kan ett adressnamn finnas på flera orter men enbart ett namn får finns på en och samma ort. Så i en större databas skulle man
-- behöva skilja mer på adressuppgifter.

DROP VIEW IF EXISTS customerTotalValue;
CREATE VIEW customerTotalValue as
select customer.id, customer.firstName, customer.lastName, sum(shoes.price * orderinfo.quantity) as totaltPris from customer
inner join orders on orders.customerId = customer.id
inner join orderinfo on orderInfo.ordersId = orders.id
inner join shoes on orderInfo.shoesId = shoes.id
group by customer.lastName
order by totaltPris desc;

DROP VIEW IF EXISTS categoryView;
CREATE VIEW categoryView as
select category.categoryName, shoes.shoeName, storageQuantity from partOf
inner join category on partOf.categoryId = category.id
inner join shoes on partOf.shoesId = shoes.id
group by shoes.id
order by category.categoryName desc;

DROP VIEW IF EXISTS shoeView;
CREATE VIEW shoeView as
select shoes.id, shoes.shoeName, brand.brandName, shoes.size, shoes.color, shoes.price,  shoes.storageQuantity from shoes
inner join brand on shoes.brandId = brand.id
having storageQuantity > 0;

DROP TRIGGER IF EXISTS outOfStock

DELIMITER //
CREATE TRIGGER outOfStock
AFTER UPDATE ON shoes
FOR EACH ROW
BEGIN

IF (NEW.storageQuantity = 0) 
THEN
	
    INSERT INTO OutOfStock (outOfStockDate, shoesOldId) VALUES (current_date(), NEW.id);

END IF;
END //
DELIMITER ;
