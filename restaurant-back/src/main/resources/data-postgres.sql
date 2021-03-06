INSERT INTO role_table (id, name)
VALUES (1, 'ADMIN');
INSERT INTO role_table (id, name)
VALUES (2, 'MANAGER');
INSERT INTO role_table (id, name)
VALUES (3, 'HEAD_CHEF');
INSERT INTO role_table (id, name)
VALUES (4, 'CHEF');
INSERT INTO role_table (id, name)
VALUES (5, 'BARTENDER');
INSERT INTO role_table (id, name)
VALUES (6, 'WAITER');

-- LOZINKE SVIH KORISNIKA SU 'test' :)

insert into system_user (username, password, deleted, loggedFirstTime, role_id)
values ('admin', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'False', 'False', 1);
insert into admin (id)
values (1);

insert into system_user (username, password, deleted, loggedFirstTime, role_id)
values ('chef', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'False', 'False', 4);
insert into employees (id, name, surname, image, telephone)
values (2, 'Chef', 'Chefic',
        'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==',
        '123456');
insert into chef (id)
values (2);

insert into system_user (username, password, deleted, loggedFirstTime, role_id)
values ('manager', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'False', 'False', 2);
insert into employees (id, name, surname, image, telephone)
values (3, 'Manager', 'Manageric',
        'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==',
        '654321');
insert into manager (id)
values (3);

insert into system_user (username, password, deleted, loggedFirstTime, role_id)
values ('waiter', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'False', 'False', 6);
insert into employees (id, name, surname, image, telephone)
values (4, 'Waiter', 'Waiterric',
        'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==',
        '123654');
insert into waiter (id)
values (4);

insert into system_user (username, password, deleted, loggedFirstTime, role_id)
values ('Boban123', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'False', 'True', 6);
insert into employees (id, name, surname, image, telephone)
values (5, 'Boban', 'Bobic',
        'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==',
        '456789');
insert into waiter (id)
values (5);

insert into system_user (username, password, deleted, loggedFirstTime, role_id)
values ('misko', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'False', 'True', 5);
insert into employees (id, name, surname, image, telephone)
values (6, 'Mihajlo', 'Milosevic',
        'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==',
        '456789');
insert into bartender (id)
values (6);

insert into system_user (username, password, deleted, loggedFirstTime, role_id)
values ('slavkoo', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'False', 'False', 3);
insert into employees (id, name, surname, image, telephone)
values (7, 'slavkoo', 'slavkovic', 'nestooooooo', '123459');
insert into chef (id)
values (7);
insert into headchef (id)
values (7);

insert into system_user (username, password, deleted, loggedFirstTime, role_id)
values ('otherWaiter', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'False', 'False', 6);
insert into employees (id, name, surname, image, telephone)
values (8, 'otherWaiter', 'Waiterric',
        'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==',
        '123654');
insert into waiter (id)
values (8);

insert into paychecks (date_from, date_to, paycheck, employee_id)
values ('2021-11-01', null, 10, 2);
insert into paychecks (date_from, date_to, paycheck, employee_id)
values ('2021-09-01', '2021-09-30', 10, 3);
insert into paychecks (date_from, date_to, paycheck, employee_id)
values ('2021-10-01', null, 10, 3);
insert into paychecks (date_from, date_to, paycheck, employee_id)
values ('2021-09-01', '2021-10-31', 10, 4);
insert into paychecks (date_from, date_to, paycheck, employee_id)
values ('2021-11-01', null, 10, 4);
insert into paychecks (date_from, date_to, paycheck, employee_id)
values ('2021-11-01', null, 10, 6);

--INGREDIENTS
INSERT INTO ingredient (name, alergen)
VALUES ('plazma', false);
INSERT INTO ingredient (name, alergen)
VALUES ('sladoled', true);
INSERT INTO ingredient (name, alergen)
VALUES ('mleko', false);
INSERT INTO ingredient (name, alergen)
VALUES ('cokolada', false);

INSERT INTO ingredient (name, alergen)
VALUES ('Green salad', true);
INSERT INTO ingredient (name, alergen)
VALUES ('Chicken', true);
INSERT INTO ingredient (name, alergen)
VALUES ('Coffee', true);
INSERT INTO ingredient (name, alergen)
VALUES ('Croutons', true);
INSERT INTO ingredient (name, alergen)
VALUES ('Oat milk', true);

INSERT INTO ingredient (name, alergen)
VALUES ('Cheese', false);
INSERT INTO ingredient (name, alergen)
VALUES ('Tomato sauce', false);
INSERT INTO ingredient (name, alergen)
VALUES ('Lasagna noodles', false);

--PIZZA
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Pizza',
        'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==',
        true, false);

INSERT INTO dish(id, dish_type)
VALUES (1, 'MAIN_DISH');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2020-08-02', '2020-10-10', 10, 16, false, 1);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2020-10-11', '2021-10-15', 10, 17, false, 1);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-10-16', '2021-10-20', 10, 18, false, 1);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-10-21', null, 10, 25, true, 1);

--SPAGHETTI
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Spaghetti', 'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==', true, false);

INSERT INTO dish(id, dish_type)
VALUES (2, 'MAIN_DISH');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-04-10', '2021-05-10', 10, 15, false, 2);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-05-11', null, 10, 15, true, 2);

--VANILLA ICE CREAM
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Vanilla ice cream', 'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==', true, true);

INSERT INTO dish(id, dish_type)
VALUES (3, 'DESERT');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-01-10', '2021-03-12', 10, 15, false, 3);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-03-13', null, 10, 15, true, 3);

--CHEESE PLATTER
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Cheese platter', 'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==', false, true);

INSERT INTO dish(id, dish_type)
VALUES (4, 'ENTREE');

--CAESAR SALAD
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Caesar salad', 'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==', false, false);

INSERT INTO dish(id, dish_type)
VALUES (5, 'SALAD');

INSERT INTO menuItemIngredients (menu_item_id, ingredient_id)
VALUES (5, 5);
INSERT INTO menuItemIngredients (menu_item_id, ingredient_id)
VALUES (5, 6);
INSERT INTO menuItemIngredients (menu_item_id, ingredient_id)
VALUES (5, 8);

--CHICKEN SOUP
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Chicken soup', 'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==', true, false);

INSERT INTO dish(id, dish_type)
VALUES (6, 'SOUP');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-06-20', '2021-06-22', 10, 15, false, 6);
INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-06-23', null, 10, 15, false, 6);

--SPRITE
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Sprite', 'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==', true, false);

INSERT INTO drink (id, drink_type, container_type)
VALUES (7, 'COLD_DRINK', 'BOTTLE');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-06-20', null, 10, 15, true, 7);

--NES COFFEE
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Nes Coffee', 'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==', false, false);
INSERT INTO drink (id, drink_type, container_type)
VALUES (8, 'COFFEE', 'GLASS');

INSERT INTO menuItemIngredients (menu_item_id, ingredient_id)
VALUES (8, 7);
INSERT INTO menuItemIngredients (menu_item_id, ingredient_id)
VALUES (8, 9);

--HOT CHOCOLATE
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Hot chocolate', 'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==', true, true);

INSERT INTO drink (id, drink_type, container_type)
VALUES (9, 'HOT_DRINK', 'GLASS');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-06-20', null, 10, 15, true, 9);

--WINE - BOTTLE
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Wine', 'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==', true, false);

INSERT INTO drink (id, drink_type, container_type)
VALUES (10, 'ALCOHOLIC', 'BOTTLE');

--WINE - PITCHER
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Wine', 'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==', true, false);

INSERT INTO drink (id, drink_type, container_type)
VALUES (11, 'ALCOHOLIC', 'PITCHER');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-06-20', null, 10, 15, false, 11);

--COCKTA
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Cockta',
        'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==',
        true, false);

INSERT INTO drink (id, drink_type, container_type)
VALUES (12, 'COLD_DRINK', 'GLASS');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-06-20', null, 10, 15, true, 12);

--LEMONADE
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Lemonade',
        'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==',
        true, false);

INSERT INTO drink (id, drink_type, container_type)
VALUES (13, 'COLD_DRINK', 'GLASS');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-06-20', null, 10, 15, true, 13);

--MEATBALLS
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Meatballs',
        'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==',
        true, false);

INSERT INTO dish(id, dish_type)
VALUES (14, 'MAIN_DISH');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-06-20', null, 10, 15, true, 14);

--PANCAKES
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Pancakes',
        'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==',
        true, false);

INSERT INTO dish(id, dish_type)
VALUES (15, 'DESERT');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-06-20', null, 10, 15, true, 15);

--BEER
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Beer',
        'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==',
        true, false);

INSERT INTO drink (id, drink_type, container_type)
VALUES (16, 'ALCOHOLIC', 'BOTTLE');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-06-20', null, 10, 15, true, 16);

--FRUIT SALAD
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Fruit Salad',
        'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==',
        true, false);

INSERT INTO dish(id, dish_type)
VALUES (17, 'SALAD');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-06-20', null, 10, 15, true, 17);

--CREAM SOUP
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Cream soup',
        'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==',
        true, false);

INSERT INTO dish(id, dish_type)
VALUES (18, 'SOUP');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-06-20', null, 10, 15, true, 18);

--CAPPUCCINO
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Cappuccino',
        'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==',
        true, false);

INSERT INTO drink (id, drink_type, container_type)
VALUES (19, 'COFFEE', 'GLASS');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-06-20', null, 10, 15, true, 19);

--TEA
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Tea',
        'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==',
        true, false);

INSERT INTO drink (id, drink_type, container_type)
VALUES (20, 'HOT_DRINK', 'GLASS');

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-06-20', null, 10, 15, true, 20);

--PUMPKIN SPICE LATTE
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Pumpkin Spice Late', 'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==', false, false);

INSERT INTO drink (id, drink_type, container_type)
VALUES (21, 'HOT_DRINK', 'GLASS');

INSERT INTO menuItemIngredients (menu_item_id, ingredient_id)
VALUES (21, 7);
INSERT INTO menuItemIngredients (menu_item_id, ingredient_id)
VALUES (21, 9);

--LASAGNA
INSERT INTO menu_item (name, image, approved, deleted)
VALUES ('Lasagna', 'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO
        9TXL0Y4OHwAAAABJRU5ErkJggg==', true, false);

INSERT INTO dish(id, dish_type)
VALUES (22, 'MAIN_DISH');
INSERT INTO menuItemIngredients (menu_item_id, ingredient_id)
VALUES (22, 10);
INSERT INTO menuItemIngredients (menu_item_id, ingredient_id)
VALUES (22, 11);
INSERT INTO menuItemIngredients (menu_item_id, ingredient_id)
VALUES (22, 12);

INSERT INTO menu_item_price (date_from, date_to, purchase_price, price, active, item_id)
VALUES ('2021-06-20', null, 10, 15, true, 22);


-- AREA
INSERT INTO area (name)
VALUES ('First');
INSERT INTO area (name)
VALUES ('Second');

--TABLES
INSERT INTO restaurant_table (positionX, positionY, waiter_id, occupied, area_id, tableNum)
VALUES (5, 5, 4, false, 1, 1);
INSERT INTO restaurant_table (positionX, positionY, waiter_id, occupied, area_id, tableNum)
VALUES (6, 6, 4, true, 1, 2);
INSERT INTO restaurant_table (positionX, positionY, waiter_id, occupied, area_id, tableNum)
VALUES (8, 8, null, false, 1, 3);

--ORDERS
INSERT INTO restaurant_order (is_paid, total_price, date, note, time, waiter_id, restaurant_table_id, order_number)
VALUES (true, 1, '2021-10-22', 'x', '18:18', 4, 2, 1);
INSERT INTO restaurant_order (is_paid, total_price, date, note, time, waiter_id, restaurant_table_id, order_number)
VALUES (true, 1, '2021-10-22', 'x', '18:18', 4, 2, 2);
INSERT INTO restaurant_order (is_paid, total_price, date, note, time, waiter_id, restaurant_table_id, order_number)
VALUES (true, 1, '2021-10-11', 'x', '18:18', 4, 2, 3);
INSERT INTO restaurant_order (is_paid, total_price, date, note, time, waiter_id, restaurant_table_id, order_number)
VALUES (true, 1, '2021-08-11', 'x', '18:18', 4, 2, 4);
INSERT INTO restaurant_order (is_paid, total_price, date, note, time, waiter_id, restaurant_table_id, order_number)
VALUES (true, 1, '2021-08-11', 'x', '18:18', 4, 2, 5);
INSERT INTO restaurant_order (is_paid, total_price, date, note, time, waiter_id, restaurant_table_id, order_number)
VALUES (true, 1, '2021-12-11', 'x', '18:18', 4, 2, 6);
INSERT INTO restaurant_order (is_paid, total_price, date, note, time, waiter_id, restaurant_table_id, order_number)
VALUES (false, 1, '2021-12-11', 'x', '18:18', 4, 2, 7);
INSERT INTO restaurant_order (is_paid, total_price, date, note, time, waiter_id, restaurant_table_id, order_number)
VALUES (false, 60, '2021-12-11', 'Old note', '18:18', 4, 2, 8);

INSERT INTO ordered_item (status, priority, quantity, order_id, deleted, menu_item_id, employee_id)
VALUES ('IN_PROGRESS', 1, 5, 1, false, 1, 2);
INSERT INTO ordered_item (status, priority, quantity, order_id, deleted, menu_item_id, employee_id)
VALUES ('READY', 1, 5, 2, false, 1, 2);
INSERT INTO ordered_item (status, priority, quantity, order_id, deleted, menu_item_id, employee_id)
VALUES ('DELIVERED', 1, 5, 3, false, 1, 2);
INSERT INTO ordered_item (status, priority, quantity, order_id, deleted, menu_item_id, employee_id)
VALUES ('READY', 1, 5, 4, false, 1, 2);
INSERT INTO ordered_item (status, priority, quantity, order_id, deleted, menu_item_id, employee_id)
VALUES ('READY', 1, 5, 5, false, 2, 2);
INSERT INTO ordered_item (status, priority, quantity, order_id, deleted, menu_item_id, employee_id)
VALUES ('READY', 1, 5, 6, false, 2, 2);
INSERT INTO ordered_item (status, priority, quantity, order_id, deleted, menu_item_id, employee_id)
VALUES ('ORDERED', 1, 5, 1, false, 2, 2);
INSERT INTO ordered_item (status, priority, quantity, order_id, deleted, menu_item_id, employee_id)
VALUES ('ORDERED', 1, 3, 1, true, 1, 2);
INSERT INTO ordered_item (status, priority, quantity, order_id, deleted, menu_item_id, employee_id)
VALUES ('ORDERED', 1, 1, 7, false, 1, 2);
---
INSERT INTO ordered_item (status, priority, quantity, order_id, deleted, menu_item_id, employee_id)
VALUES ('ORDERED', 3, 1, 8, false, 22, 2);
INSERT INTO ordered_item (status, priority, quantity, order_id, deleted, menu_item_id, employee_id)
VALUES ('ORDERED', 1, 2, 8, false, 1, 2);

INSERT INTO activeIngredients (ordered_item_id, ingredient_id)
VALUES (10, 10);
INSERT INTO activeIngredients (ordered_item_id, ingredient_id)
VALUES (10, 11);
INSERT INTO activeIngredients (ordered_item_id, ingredient_id)
VALUES (10, 12);
INSERT INTO activeIngredients (ordered_item_id, ingredient_id)
VALUES (11, 1);
INSERT INTO activeIngredients (ordered_item_id, ingredient_id)
VALUES (11, 3);


INSERT INTO menuItemIngredients (menu_item_id, ingredient_id)
VALUES (1, 1);
INSERT INTO menuItemIngredients (menu_item_id, ingredient_id)
VALUES (1, 2);
INSERT INTO menuItemIngredients (menu_item_id, ingredient_id)
VALUES (1, 3);

INSERT INTO notification (is_active, text, employee_id)
VALUES (false, 'Status changed for Pizza to READY for order number 3, at table number 2', 4);
INSERT INTO notification (is_active, text, employee_id)
VALUES (true, 'Status changed for Pizza to IN_PROGRESS for order number 1, at table number 2', 4);
INSERT INTO notification (is_active, text, employee_id)
VALUES (true, 'Status changed for Pizza to READY for order number 2, at table number 2', 4);
INSERT INTO notification (is_active, text, employee_id)
VALUES (true, 'Status changed for Spaghetti to READY for order number 5, at table number 2', 4);
INSERT INTO notification (is_active, text, employee_id)
VALUES (true, 'Status changed for Pizza to READY for order number 4, at table number 2', 4);
INSERT INTO notification (is_active, text, employee_id)
VALUES (true, 'Status changed for Spaghetti to READY for order number 6, at table number 2', 4);
