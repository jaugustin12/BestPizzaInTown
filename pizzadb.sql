drop table orders;
drop table ordereditems;
drop table customers;
DROP TABLE pizza;
DROP TABLE sides;
DROP TABLE drinks;


CREATE TABLE customers (
   PhoneNumber VARCHAR (15) NOT NULL, 
   FirstName varchar (20) NOT NULL,
   LastName  varchar (30) NOT NULL,
   Email VARCHAR (254),
   PRIMARY KEY (PhoneNumber)
);

CREATE TABLE pizza (
   PizzaId  SMALLINT NOT NULL,
   PizzaDes VARCHAR (50) NOT NULL,
   Price    FLOAT  NOT NULL,
   PRIMARY KEY (PizzaId)
);

CREATE TABLE sides (
   SidesId  SMALLINT NOT NULL,
   SidesDes VARCHAR (50) NOT NULL,
   Price    FLOAT  NOT NULL,
   PRIMARY KEY (SidesId)
);

CREATE TABLE drinks (
   DrinkId  SMALLINT NOT NULL,
   DrinkDes VARCHAR (50) NOT NULL,
   Price    FLOAT  NOT NULL,
   PRIMARY KEY (DrinkId)
);


CREATE TABLE ordereditems (
   OrderId   INT NOT NULL,
   PizzaId   SMALLINT NOT NULL,
   PizzaQn   SMALLINT NOT NULL,  
   SidesId   SMALLINT NOT NULL,
   SidesQn   SMALLINT NOT NULL, 
   DrinkId   SMALLINT NOT NULL,
   DrinkQn   SMALLINT NOT NULL, 
   PRIMARY KEY (OrderId),
   FOREIGN KEY (SidesId) REFERENCES sides (SidesId),
   FOREIGN KEY (PizzaId) REFERENCES pizza (PizzaId),
   FOREIGN KEY (DrinkId) REFERENCES drinks (DrinkId)
);



CREATE TABLE orders (
   OrderId   INT NOT NULL,
   PhoneNumber VARCHAR (15) NOT NULL,
   OrderTime  TIMESTAMP NOT NULL,
   TotalPrice FLOAT NOT NULL,
   FOREIGN KEY (OrderId) REFERENCES ordereditems (OrderId),
   FOREIGN KEY (PhoneNumber) REFERENCES customers (PhoneNumber)
);




INSERT INTO pizza (PizzaId, PizzaDes, Price)
VALUES 
   (1, 'Pepperoni Pizza', 8), 
   (2, 'Sausage Pizza', 9),
   (3, 'Cheese Pizza', 6);

INSERT INTO sides (SidesId, SidesDes, Price)
VALUES 
   (1, 'Cheesesticks', 4), 
   (2, 'Buffalo Wings', 6);

INSERT INTO drinks (DrinkId, DrinkDes, Price)
VALUES 
   (1, 'Pepsi', 2), 
   (2, 'Bottle Water', 1);

INSERT INTO customers (PhoneNumber, FirstName, LastName, Email)
VALUES 
   ('111-111-1111', 'Alice', 'A', 'a@a.com'), 
   ('111-111-1112', 'Bob', 'B', 'b@a.com'), 
   ('111-111-1113', 'Charles', 'C', 'c@a.com'), 
   ('111-111-1114', 'David', 'D', 'd@a.com'), 
   ('111-111-1115', 'Ellen', 'E', 'e@a.com'), 
   ('111-111-1116', 'Frank', 'F', 'f@a.com'), 
   ('111-111-1117', 'Grace', 'G', 'g@a.com'), 
   ('111-111-1118', 'Helen', 'H', 'h@a.com'), 
   ('111-111-1119', 'Jack', 'J', 'j@a.com');

INSERT INTO ordereditems (OrderId, PizzaId, PizzaQn, SidesId, SidesQn, DrinkId, DrinkQn)
VALUES 
(1, 1, 1, 1, 0, 1, 0),   
(2, 1, 1, 1, 1, 1, 1), 
(3, 1, 1, 1, 1, 1, 1), 
(4, 1, 1, 2, 1, 1, 0), 
(5, 1, 1, 2, 1, 1, 1), 
(6, 2, 1, 1, 0, 1, 0), 
(7, 2, 1, 1, 1, 1, 1), 
(8, 2, 1, 1, 0, 1, 1), 
(9, 1, 1, 1, 1, 2, 1), 
(10, 1, 2, 1, 2, 1, 1), 
(11, 1, 2, 2, 1, 1, 1), 
(12, 3, 1, 1, 1, 1, 1), 
(13, 3, 1, 1, 1, 2, 1), 
(14, 1, 1, 1, 3, 1, 1), 
(15, 2, 2, 1, 1, 1, 1), 
(16, 1, 4, 1, 2, 1, 0), 
(17, 1, 3, 2, 2, 1, 1), 
(18, 3, 2, 1, 1, 1, 1), 
(19, 3, 1, 1, 1, 1, 1), 
(20, 2, 1, 1, 0, 1, 1);


INSERT INTO orders (OrderId, PhoneNumber, OrderTime, TotalPrice)
VALUES 
   (1, '111-111-1111', '2018-11-11 13:23:44', 8), 
   (2, '111-111-1112', '2018-11-11 15:23:44', 14), 
   (3, '111-111-1113', '2018-11-11 19:23:44',14), 
   (4, '111-111-1114', '2018-11-11 23:23:44',14), 
   (5, '111-111-1115', '2018-11-12 13:23:44',16), 
   (6, '111-111-1116', '2018-11-12 14:23:44', 9), 
   (7, '111-111-1117', '2018-11-13 13:23:44', 15), 
   (8, '111-111-1118', '2018-11-13 14:23:44', 11), 
   (9, '111-111-1119', '2018-11-13 18:23:44', 13),
   (10, '111-111-1111', '2018-11-13 19:23:44', 26), 
   (11, '111-111-1113', '2018-11-13 20:23:44', 24), 
   (12, '111-111-1112', '2018-11-14 13:23:44', 12), 
   (13, '111-111-1115', '2018-11-14 14:23:44', 13), 
   (14, '111-111-1114', '2018-11-14 14:25:44', 22), 
   (15, '111-111-1111', '2018-11-15 13:23:44',24), 
   (16, '111-111-1116', '2018-11-16 13:23:44', 40), 
   (17, '111-111-1111', '2018-11-16 14:23:44',38), 
   (18, '111-111-1118', '2018-11-16 19:23:44', 12), 
   (19, '111-111-1119', '2018-11-16 21:23:44', 15),
   (20, '111-111-1111', '2018-11-17 13:23:44', 11);
