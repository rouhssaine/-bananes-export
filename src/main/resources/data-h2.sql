-- Liste des destinataires
INSERT INTO customer(id, name, address, postal_code, city, state) VALUES(100, 'TOTO', '5 avenue Anatole France', '75007', 'Paris', 'France');
INSERT INTO customer(id, name, address, postal_code, city, state) VALUES(200, 'TITI', '5 avenue des Champs-Elysées', '75008', 'Paris', 'France');
INSERT INTO customer(id, name, address, postal_code, city, state) VALUES(300, 'TATA', '3 avenue Anatole France', '75007', 'Paris', 'France');

INSERT INTO banana_order (id, customer_id, delivery_date, quantity) VALUES(10, 100, CURRENT_TIMESTAMP, 100);
INSERT INTO banana_order (id, customer_id, delivery_date, quantity) VALUES(11, 100, CURRENT_TIMESTAMP, 100);
INSERT INTO banana_order (id, customer_id, delivery_date, quantity) VALUES(20, 200, CURRENT_TIMESTAMP, 200);
INSERT INTO banana_order (id, customer_id, delivery_date, quantity) VALUES(21, 200, CURRENT_TIMESTAMP, 200);
INSERT INTO banana_order (id, customer_id, delivery_date, quantity) VALUES(30, 300, CURRENT_TIMESTAMP, 300);
INSERT INTO banana_order (id, customer_id, delivery_date, quantity) VALUES(31, 300, CURRENT_TIMESTAMP, 300);
