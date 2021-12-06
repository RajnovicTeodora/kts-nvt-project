insert into system_user (username, password, deleted, loggedFirstTime) values ('admin', 'admin', 'False', 'True');
insert into admin (id) values (1);

insert into system_user (username, password, deleted, loggedFirstTime) values ('chef', 'chef', 'False', 'False');
insert into employees (id, name, surname, image, telephone) values (2, 'Chef', 'Chefic', 'nesto', '123456');
insert into chef (id) values (2);

insert into system_user (username, password, deleted, loggedFirstTime) values ('manager', 'manager', 'False', 'False');
insert into employees (id, name, surname, image, telephone) values (3, 'Manager', 'Manageric', 'nestodr', '654321');
insert into manager (id) values (3);

insert into system_user (username, password, deleted, loggedFirstTime) values ('waiter', 'waiter', 'False', 'False');
insert into employees (id, name, surname, image, telephone) values (4, 'Waiter', 'Waiterric', 'nestodrr', '123654');
insert into waiter (id) values (4);

insert into paychecks (date_from , date_to, paycheck, employee_id) values ('2021-11-01', null, 10, 2);
insert into paychecks (date_from , date_to, paycheck, employee_id) values ('2021-09-01', '2021-09-30', 10, 3);
insert into paychecks (date_from , date_to, paycheck, employee_id) values ('2021-10-01', null, 10, 3);
insert into paychecks (date_from , date_to, paycheck, employee_id) values ('2021-09-01', '2021-10-31', 10, 4);
insert into paychecks (date_from , date_to, paycheck, employee_id) values ('2021-11-01', null, 10, 4);

INSERT INTO menu_item (name, image, approved, deleted) VALUES ('Pizza', 'todo',             true, false);
INSERT INTO menu_item (name, image, approved, deleted) VALUES ('Spaghetti', 'todo',         true, false);
INSERT INTO menu_item (name, image, approved, deleted) VALUES ('Vanilla ice cream', 'todo', true, true);
INSERT INTO menu_item (name, image, approved, deleted) VALUES ('Cheese platter', 'todo',    false, true);
INSERT INTO menu_item (name, image, approved, deleted) VALUES ('Caesar salad', 'todo',      false, false);
INSERT INTO menu_item (name, image, approved, deleted) VALUES ('Chicken soup', 'todo',      true, false);
INSERT INTO menu_item (name, image, approved, deleted) VALUES ('Sprite', 'todo',            true, false);
INSERT INTO menu_item (name, image, approved, deleted) VALUES ('Nes Coffee', 'todo',        false, false);
INSERT INTO menu_item (name, image, approved, deleted) VALUES ('Hot chocolate', 'todo',     true, true);
INSERT INTO menu_item (name, image, approved, deleted) VALUES ('Ovdeeeee', 'todo',              false, false);
INSERT INTO menu_item (name, image, approved, deleted) VALUES ('Wine', 'todo',              true, false);

INSERT INTO dish(id, dish_type) VALUES (1, 'MAIN_DISH');
INSERT INTO dish(id, dish_type) VALUES (2, 'MAIN_DISH');
INSERT INTO dish(id, dish_type) VALUES (3, 'DESERT');
INSERT INTO dish(id, dish_type) VALUES (4, 'ENTREE');
INSERT INTO dish(id, dish_type) VALUES (5, 'SALAD');
INSERT INTO dish(id, dish_type) VALUES (6, 'SOUP');

INSERT INTO drink (id, drink_type, container_type) VALUES (7, 'COLD_DRINK', 'BOTTLE');
INSERT INTO drink (id, drink_type, container_type) VALUES (8, 'COFFEE', 'GLASS');
INSERT INTO drink (id, drink_type, container_type) VALUES (9, 'HOT_DRINK', 'GLASS');
INSERT INTO drink (id, drink_type, container_type) VALUES (10, 'ALCOHOLIC', 'BOTTLE');
INSERT INTO drink (id, drink_type, container_type) VALUES (11, 'ALCOHOLIC', 'PITCHER');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2020-08-02', '2020-10-10', 10, 16, false, 1);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2020-10-11', '2021-10-15', 10, 17, false, 1);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2021-10-16', '2021-10-20', 10, 18, false, 1);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2021-10-21', null, 10, 25, true, 1);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2021-04-10', '2021-05-10', 10, 15, false, 2);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2021-05-11', null, 10, 15, true, 2);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2021-01-10', '2021-03-12', 10, 15, false, 3);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2021-03-13', null, 10, 15, true, 3);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2021-06-20', '2021-06-22', 10, 15, false, 6);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2021-06-23', null, 10, 15, false, 6);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2021-06-20', null, 10, 15, true, 7);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2021-06-20', null, 10, 15, true, 9);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id) VALUES ('2021-06-20', null, 10, 15, false, 11);

INSERT INTO restaurant_order (is_paid, total_price, date, note, time) VALUES (true, 1, '2021-10-22', 'x', '18:18');
INSERT INTO restaurant_order (is_paid, total_price, date, note, time) VALUES (true, 1, '2021-10-22', 'x', '18:18');
INSERT INTO restaurant_order (is_paid, total_price, date, note, time) VALUES (true, 1, '2021-10-11', 'x', '18:18');
INSERT INTO restaurant_order (is_paid, total_price, date, note, time) VALUES (true, 1, '2021-08-11', 'x', '18:18');
INSERT INTO restaurant_order (is_paid, total_price, date, note, time) VALUES (true, 1, '2021-08-11', 'x', '18:18');
INSERT INTO restaurant_order (is_paid, total_price, date, note, time) VALUES (true, 1, '2021-12-11', 'x', '18:18');

INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('IN_PROGRESS', 1, 5, 1, false, 1);
INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('READY', 1, 5, 2, false, 1);
INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('DELIVERED', 1, 5, 3, false, 1);
INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('READY', 1, 5, 4, false, 1);
INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('READY', 1, 5, 5, false, 2);
INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('READY', 1, 5, 6, false, 2);
INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('ORDERED', 1, 5, 1, false, 2);
INSERT INTO ordered_item ( status, priority, quantity, order_id, deleted, menu_item_id) VALUES ('ORDERED', 1, 3, 1, true, 1);

INSERT INTO area (name) VALUES ('First');
INSERT INTO area (name) VALUES ('Second');

INSERT INTO restaurant_table (positionX, positionY, waiter_id, occupied, area_id) VALUES (5, 5, 4, false, 1);
INSERT INTO restaurant_table (positionX, positionY, waiter_id, occupied, area_id) VALUES (6, 6, 4, true, 1);
INSERT INTO restaurant_table (positionX, positionY, waiter_id, occupied, area_id) VALUES (8, 8, null, false, 1);