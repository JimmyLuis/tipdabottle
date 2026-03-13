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
VALUES (2, 'Fanta Kasten','Fanta Flaschen', 'SNACK',50,5);

INSERT INTO container (id, name, description , category, capacity, stock)
VALUES (3, 'Chips Karton','Funny Frisch Karton', 'NON_ALCOHOL',20,8);

INSERT INTO container (id, name, description, category, capacity, stock)
VALUES (4, 'Bier Kasten','Stubbi Karlsberg', 'ALCOHOL',100,2);

INSERT INTO container (id, name, description, category, capacity, stock)
VALUES (5, 'Weinkiste','Weinkiste', 'ALCOHOL',12,4);



-- PRODUCT
INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (1,'Cola','0.5L Flasche',1.50,'NON_ALCOHOL',100,1);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (2,'Fanta','0.5L Flasche',1.50,'NON_ALCOHOL',80,2);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (3,'Chips','Paprika Chips',1.20,'SNACK',60,3);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (4,'Bier','Pils 0.5L',2.00,'ALCOHOL',120,4);

INSERT INTO product (id, name, description, price, category, stock, container_id)
VALUES (5,'Wein','Rotwein Flasche',8.50,'ALCOHOL',40,5);
-- Container mit Product verbinden
UPDATE container SET product_id = 1 WHERE id = 1;
UPDATE container SET product_id = 2 WHERE id = 2;
UPDATE container SET product_id = 3 WHERE id = 3;
UPDATE container SET product_id = 4 WHERE id = 4;
UPDATE container SET product_id = 5 WHERE id = 5;


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