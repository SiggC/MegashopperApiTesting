create schema megashopper;

set search_path to megashopper;

create table customers (
    customer_id         uuid default gen_random_uuid() primary key,
    first_name          varchar not null,
    last_name           varchar not null,
    email               varchar unique not null,
    street_address      varchar not null,
    password_hash       bytea unique not null,
    password_salt       bytea unique not null     
);

create table employees (
    employee_id         uuid default gen_random_uuid() primary key,
    first_name          varchar not null,
    last_name           varchar not null,
    email               varchar unique not null,
    password_hash       bytea unique not null,
    password_salt       bytea unique not null
);


create table items (
    item_id             uuid default gen_random_uuid() primary key,
    title               varchar not null,
    description         varchar not null,
    category            varchar not null,
    price               money not null
);

create table cart ( -- junction table
    item_id             uuid not null,
    customer_id         uuid not null,

    constraint itemcustomer_pk primary key
    (
        item_id,
        customer_id
    ),
    foreign key (item_id) references items(item_id),
    foreign key (customer_id) references customers(customer_id)
);

create table orders (
    order_id            int generated always as identity primary key,
    items               uuid[],
    tracking_number     int unique not null,
    delivery_date       timestamp not null,
    customer_id         uuid,

    constraint customer_id_fk
    foreign key(customer_id)
    references customers(customer_id)
);


