-- CUSTOMER
INSERT INTO customer (id, first_name, last_name, email, balance, locked, community, active_profile)
VALUES (1,'Max','Mustermann','max@example.com',20.0,false,true,true);

INSERT INTO customer (id, first_name, last_name, email, balance, locked, community, active_profile)
VALUES (2,'Anna','Schmidt','anna@example.com',15.5,false,false,true);

INSERT INTO customer (id, first_name, last_name, email, balance, locked, community, active_profile)
VALUES (3,'Tom','Müller','tom@example.com',30.0,false,true,true);

INSERT INTO customer (id, first_name, last_name, email, balance, locked, community, active_profile)
VALUES (4,'Lisa','Becker','lisa@example.com',10.0,false,false,true);

INSERT INTO customer (id, first_name, last_name, email, balance, locked, community, active_profile)
VALUES (5,'Paul','Wagner','paul@example.com',5.0,false,true,true);



-- CONTAINER
INSERT INTO container (id, name, description, category, capacity, stock)
VALUES (1, 'Cola Kasten','Cola Flaschen', 'NON_ALCOHOL',24,10);

INSERT INTO container (id , name, description, category, capacity, stock)
VALUES (2, 'Fanta Kasten','Fanta Flaschen', 'SNACK',24,5);

INSERT INTO container (id, name, description , category, capacity, stock)
VALUES (3, 'Chips Karton','Funny Frisch Karton', 'NON_ALCOHOL',20,8);

INSERT INTO container (id, name, description, category, capacity, stock)
VALUES (4, 'Bier Kasten','Stubbi Karlsberg', 'ALCOHOL',20,2);

INSERT INTO container (id, name, description, category, capacity, stock)
VALUES (5, 'Weinkiste','Weinkiste', 'ALCOHOL',6,4);

INSERT INTO container (id, name, description, category, capacity, stock)
VALUES (6, 'Mixery Kasten','Mixery Flaschen', 'ALCOHOL',24,6);

INSERT INTO container (id, name, description, category, capacity, stock)
VALUES (7, 'Mezzo Mix Kasten','Mezzo Mix Flaschen', 'NON_ALCOHOL',24,7);

INSERT INTO container (id, name, description, category, capacity, stock)
VALUES (8, 'Beckers Pils Kasten','Beckers Pils Flaschen', 'ALCOHOL',20,5);

INSERT INTO container (id, name, description, category, capacity, stock)
VALUES (9, 'Sprite Kasten','Sprite Flaschen', 'NON_ALCOHOL',24,6);

INSERT INTO container (id, name, description, category, capacity, stock)
VALUES (10, 'Paulaner Spezi Kasten','Paulaner Spezi Flaschen', 'NON_ALCOHOL',20,8);

INSERT INTO container (id, name, description, category, capacity, stock)
VALUES (11, 'Weizenbier Kasten','Weizenbier Flaschen', 'ALCOHOL',20,4);

INSERT INTO container (id, name, description, category, capacity, stock)
VALUES (12, 'Karlsberg Fresh Kasten','Karlsberg Fresh Flaschen', 'ALCOHOL',24,5);

INSERT INTO container (id, name, description, category, capacity, stock)
VALUES (13, 'Karlsberg Grapefruit Kasten','Karlsberg Grapefruit Flaschen', 'ALCOHOL',24,5);

-- Dummy Container
INSERT INTO container (id, name, description, category, capacity, stock)
VALUES (14, 'Apfelschorle Kasten','Apfelschorle Flaschen', 'NON_ALCOHOL',24,6);

INSERT INTO container (id, name, description, category, capacity, stock)
VALUES (15, 'Energy Drink Kasten','Energy Drink Dosen', 'NON_ALCOHOL',24,4);

INSERT INTO container (id, name, description, category, capacity, stock)
VALUES (16, 'Mate Kasten','Club Mate Flaschen', 'NON_ALCOHOL',20,5);

-- PRODUCT
INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (1,'Cola','0.33L Flasche',1.00,'NON_ALCOHOL',100,1);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (2,'Fanta','0.33L Flasche',1.00,'NON_ALCOHOL',80,2);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (3,'Chips','Paprika Chips',2.50,'SNACK',60,3);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (4,'Bier','Pils 0.33L',1.00,'ALCOHOL',120,4);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (5,'Wein','Rotwein Flasche',8.50,'ALCOHOL',40,5);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (6,'Mixery','0.33L Flasche',1.50,'ALCOHOL',144,6);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (7,'Mezzo Mix','0.33L Flasche',1.00,'NON_ALCOHOL',168,7);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (8,'Beckers Pils','0.33L Flasche',1.10,'ALCOHOL',100,8);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (9,'Sprite','0.33L Flasche',1.00,'NON_ALCOHOL',144,9);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (10,'Paulaner Spezi','0.5L Flasche',1.30,'NON_ALCOHOL',160,10);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (11,'Weizenbier','0.5L Flasche',1.50,'ALCOHOL',80,11);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (12,'Karlsberg Fresh','0.33L Flasche',1.20,'ALCOHOL',120,12);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (13,'Karlsberg Grapefruit','0.33L Flasche',1.20,'ALCOHOL',120,13);

-- Dummy Produkte
INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (14,'Apfelschorle','0.5L Flasche',1.00,'NON_ALCOHOL',144,14);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (15,'Energy Drink','250ml Dose',1.80,'NON_ALCOHOL',96,15);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (16,'Club Mate','0.5L Flasche',1.40,'NON_ALCOHOL',100,16);

-- Container mit Product verbinden
UPDATE container SET product_id = 1 WHERE id = 1;
UPDATE container SET product_id = 2 WHERE id = 2;
UPDATE container SET product_id = 3 WHERE id = 3;
UPDATE container SET product_id = 4 WHERE id = 4;
UPDATE container SET product_id = 5 WHERE id = 5;
UPDATE container SET product_id = 6 WHERE id = 6;
UPDATE container SET product_id = 7 WHERE id = 7;
UPDATE container SET product_id = 8 WHERE id = 8;
UPDATE container SET product_id = 9 WHERE id = 9;
UPDATE container SET product_id = 10 WHERE id = 10;
UPDATE container SET product_id = 11 WHERE id = 11;
UPDATE container SET product_id = 12 WHERE id = 12;
UPDATE container SET product_id = 13 WHERE id = 13;
UPDATE container SET product_id = 14 WHERE id = 14;
UPDATE container SET product_id = 15 WHERE id = 15;
UPDATE container SET product_id = 16 WHERE id = 16;

-- PURCHASE
INSERT INTO purchase (id, purchase_group_id, product_id, customer_id, creation_date, worth, quantity)
VALUES (1,1,1,1,'2026-01-01',1.50,1);

INSERT INTO purchase (id, purchase_group_id, product_id, customer_id, creation_date, worth, quantity)
VALUES (2,1,2,2,'2026-01-01',3.00,2);

INSERT INTO purchase (id, purchase_group_id, product_id, customer_id, creation_date, worth, quantity)
VALUES (3,2,3,3,'2026-01-02',1.20,1);

INSERT INTO purchase (id, purchase_group_id, product_id, customer_id, creation_date, worth, quantity)
VALUES (4,2,4,4,'2026-01-02',4.00,2);

INSERT INTO purchase (id, purchase_group_id, product_id, customer_id, creation_date, worth, quantity)
VALUES (5,3,5,5,'2026-01-03',8.50,1);