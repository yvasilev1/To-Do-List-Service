/*
 create schemas
 */

drop schema if exists todo_list_data;
create schema todo_list_data;

drop schema if exists inventory_manager_data;
create schema inventory_manager_data;

set schema 'todo_list_data';

/*
  todo_list logic
 */

CREATE TABLE t_todoitem
(
    id          uuid,
    title       text,
    description text,
    status      text,
    primary key (id)
);

set schema 'inventory_manager_data';

create extension if not exists pgcrypto;

/*
  inventory_manager logic
*/

CREATE TABLE t_location
(
    id   uuid,
    name text,
    primary key (id)
);

INSERT INTO t_location (id, name)
VALUES (gen_random_uuid(), 'Freezer'),
       (gen_random_uuid(), 'Fridge'),
       (gen_random_uuid(), 'Kitchen'),
       (gen_random_uuid(), 'Bathroom'),
       (gen_random_uuid(), 'Cupboard');

CREATE TABLE t_category
(
    id   uuid,
    name text,
    primary key (id)
);

INSERT INTO t_category (id, name)
VALUES (gen_random_uuid(), 'Vegetable'),
       (gen_random_uuid(), 'Fruit'),
       (gen_random_uuid(), 'Cleaning Supply'),
       (gen_random_uuid(), 'Meat'),
       (gen_random_uuid(), 'Dairy');

CREATE TABLE t_inventory_item_type
(
    id   uuid,
    name text,
    primary key (id)
);

INSERT INTO t_inventory_item_type (id, name)
VALUES (gen_random_uuid(), 'Raw'),
       (gen_random_uuid(), 'Chopped'),
       (gen_random_uuid(), 'Meal Prep');


CREATE TABLE t_quantity_type
(
    id   uuid,
    name text,
    primary key (id)
);

INSERT INTO t_quantity_type (id, name)
VALUES (gen_random_uuid(), 'ml'),
       (gen_random_uuid(), 'cups'),
       (gen_random_uuid(), 'grams'),
       (gen_random_uuid(), 'kg'),
       (gen_random_uuid(), 'units'),
       (gen_random_uuid(), 'liter');


CREATE TABLE t_inventory_item
(
    id                  uuid,
    name                text,
    quantity            numeric,
    quantity_type       text,
    location            text,
    category            text,
    inventory_item_type text,
    purchase_location   text,
    purchase_price      numeric,
    date_added          date,
    expiry_added        date,
    is_active           boolean,
    notify_low_quantity boolean,
    notify_expiry_date  boolean,
    primary key (id)
);





