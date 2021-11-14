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

-- insert into system_user (username, password, deleted) values ('admin', 'admin', 'False');
-- insert into admin (id) values (1);
--
-- insert into system_user (username, password, deleted) values ('chef', 'chef', 'False');
-- insert into employees (id, name, surname, image, telephone ) values (2, 'Chef', 'Chefic', 'nesto', '123456');
-- insert into chef (id) values (2);
--
-- insert into system_user (username, password, deleted) values ('manager', 'manager', 'False');
-- insert into employees (id, name, surname, image, telephone ) values (3, 'Manager', 'Manageric', 'nestodr', '654321');
-- insert into manager (id) values (3);
--
-- insert into system_user (username, password, deleted) values ('waiter', 'waiter', 'False');
-- insert into employees (id, name, surname, image, telephone) values (4, 'Waiter', 'Waiterric', 'nestodrr', '123654');
-- insert into waiter (id) values (4);