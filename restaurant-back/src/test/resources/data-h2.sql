INSERT INTO role_table (id, name) VALUES (1, 'ADMIN');
INSERT INTO role_table (id, name) VALUES (2, 'MANAGER');
INSERT INTO role_table (id, name) VALUES (3, 'HEAD_CHEF');
INSERT INTO role_table (id, name) VALUES (4, 'CHEF');
INSERT INTO role_table (id, name) VALUES (5, 'BARTENDER');
INSERT INTO role_table (id, name) VALUES (6, 'WAITER');

insert into system_user (username, password, deleted, loggedFirstTime) values ('admin', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'False', 'True');
insert into admin (id) values (1);

insert into system_user (username, password, deleted, loggedFirstTime) values ('chef', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'False', 'False');
insert into employees (id, name, surname, image, telephone) values (2, 'Chef', 'Chefic', 'nesto', '123456');
insert into chef (id) values (2);

insert into system_user (username, password, deleted, loggedFirstTime) values ('manager', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'False', 'False');
insert into employees (id, name, surname, image, telephone) values (3, 'Manager', 'Manageric', 'nestodr', '654321');
insert into manager (id) values (3);

insert into system_user (username, password, deleted, loggedFirstTime) values ('waiter', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'False', 'False');
insert into employees (id, name, surname, image, telephone) values (4, 'Waiter', 'Waiterric', 'nestodrr', '123654');
insert into waiter (id) values (4);

insert into paychecks (date_from , date_to, paycheck, employee_id) values ('2021-11-01', null, 10, 2);
insert into paychecks (date_from , date_to, paycheck, employee_id) values ('2021-09-01', '2021-09-30', 10, 3);
insert into paychecks (date_from , date_to, paycheck, employee_id) values ('2021-10-01', null, 10, 3);
insert into paychecks (date_from , date_to, paycheck, employee_id) values ('2021-09-01', '2021-10-31', 10, 4);
insert into paychecks (date_from , date_to, paycheck, employee_id) values ('2021-11-01', null, 10, 4);

INSERT INTO menu_item (id, name, image, approved, deleted) VALUES (1, 'Pizza', 'todo', true, false);
INSERT INTO dish(id, dish_type) VALUES (1, 'MAIN_DISH');

INSERT INTO menu_item (id, name, image, approved, deleted) VALUES (4, 'Meatballs', 'todo', true, false);
INSERT INTO dish(id, dish_type) VALUES (4, 'MAIN_DISH');

INSERT INTO menu_item (id, name, image, approved, deleted) VALUES (2, 'Ice Latte', 'todo', true, false);
INSERT INTO drink (id, drink_type, container_type) VALUES (2, 'COLD_DRINK', 'BOTTLE');

INSERT INTO menu_item (id, name, image, approved, deleted) VALUES (3, 'Lemonade', 'todo', true, false);
INSERT INTO drink (id, drink_type, container_type) VALUES (3, 'COLD_DRINK', 'BOTTLE');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2020-08-02', '2020-10-10', 10, 16, false, 1);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2020-10-11', null, 10, 16, false, 1);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2020-10-11', null, 10, 16, true, 3);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2020-10-11', null, 10, 16, true, 4);

INSERT INTO area (id, name) VALUES (1, 'First floor');
INSERT INTO area (id, name) VALUES (2, 'Second floor');
INSERT INTO area (id, name) VALUES (3, 'Garden');

INSERT INTO restaurant_table (positionX, positionY, waiter_id, occupied, area_id, tableNum) VALUES (5, 5, 4, false, 1, 1);
INSERT INTO restaurant_table (positionX, positionY, waiter_id, occupied, area_id, tableNum) VALUES (6, 6, 4, true, 1, 2);
INSERT INTO restaurant_table (positionX, positionY, waiter_id, occupied, area_id, tableNum) VALUES (8, 8, null, false, 1, 3);
INSERT INTO restaurant_table (positionX, positionY, waiter_id, occupied, area_id, tableNum) VALUES (9, 9, 4, false, 1, 4);
INSERT INTO restaurant_table (positionX, positionY, waiter_id, occupied, area_id, tableNum) VALUES (7, 7, 4, true, 1, 5);
INSERT INTO restaurant_table (positionX, positionY, waiter_id, occupied, area_id, tableNum) VALUES (3, 3, 4, false, 1, 6);

INSERT INTO restaurant_order (is_paid, total_price, date, note, time, waiter_id, restaurant_table_id, order_number) VALUES (true, 1, '2021-10-22', 'x', '18:18', 4, 2, 1);
INSERT INTO restaurant_order (is_paid, total_price, date, note, time, waiter_id, restaurant_table_id, order_number) VALUES (true, 1, '2021-10-22', 'x', '18:18', 4, 2, 2);
INSERT INTO restaurant_order (is_paid, total_price, date, note, time, waiter_id, restaurant_table_id, order_number) VALUES (false, 1, '2021-12-07', 'x', '18:18', 4, 2, 3);
INSERT INTO restaurant_order (is_paid, total_price, date, note, time, waiter_id, restaurant_table_id, order_number) VALUES (false, 1, '2021-12-11', 'x', '11:11', 4, 2, 4);
INSERT INTO restaurant_order (is_paid, total_price, date, note, time, waiter_id, restaurant_table_id, order_number) VALUES (true, 1, '2021-12-11', 'x', '18:18', 4, 2, 5);
INSERT INTO restaurant_order (is_paid, total_price, date, note, time, waiter_id, restaurant_table_id, order_number) VALUES (true, 1, '2021-12-11', 'x', '18:18', 4, 2, 6);

INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('IN_PROGRESS', 1, 5, 1, false, 1);
INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('READY', 1, 5, 2, false, 1);
INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('IN_PROGRESS', 1, 5, 4, false, 2);
INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('READY', 1, 5, 5, false, 2);
INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('ORDERED', 1, 5, 1, false, 2);
INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('ORDERED', 1, 3, 1, true, 1);
INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('ORDERED', 1, 1, 3, false, 1);
INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('ORDERED', 1, 1, 3, true, 1);
INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('ORDERED', 1, 1, 6, false, 3);

INSERT INTO ingredient (name, alergen) VALUES ('plazma', false);
INSERT INTO ingredient (name, alergen) VALUES ('sladoled', true);
INSERT INTO ingredient (name, alergen) VALUES ('mleko', false);
INSERT INTO ingredient (name, alergen) VALUES ('cokolada', false);