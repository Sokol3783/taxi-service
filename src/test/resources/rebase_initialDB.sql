--Focused on POSTGRE SQL

DROP TABLE IF EXISTS "discounts";
DROP TABLE IF EXISTS "discount_limits";
DROP TABLE IF EXISTS "cars";
DROP TABLE IF EXISTS "orders" CASCADE;
DROP TABLE IF EXISTS "price" CASCADE;
DROP TABLE IF EXISTS "users" CASCADE;

SELECT 'CREATE DATABASE TAXI
      ENCODING UTF8'
WHERE NOT EXISTS(SELECT FROM pg_database WHERE datname = 'TAXI');

SELECT 'create USER IF NOT EXISTS TAXIADMIN with PASSWORD adminpassword123'
WHERE NOT EXISTS(SELECT FROM pg_user WHERE pg_user.usename = 'TAXIADMIN');

GRANT ALL PRIVILEGES ON DATABASE "TAXI" to "TAXIADMIN";

create TABLE users
(
    user_id    SERIAL PRIMARY KEY,
    password   varchar(50),
    first_name varchar(50),
    last_name  varchar(50),
    phone      varchar(13) unique,
    birthday   DATE,
    email      varchar(50) unique,
    user_role  varchar(15)
);

create TABLE cars
(
    car_id     SERIAL PRIMARY KEY,
    car_number varchar(30) unique,
    car_name   varchar(100),
    category   varchar(15),
    capacity   int
);

create TABLE orders
(
    order_id          SERIAL PRIMARY KEY,
    cars_numbers      varchar[],
    client_id         int references users (user_id),
    address_departure varchar(250),
    destination       varchar(250),
    cost              INTEGER,
    percent_discount  INTEGER,
    create_date       timestamp,
    order_number      INTEGER,
    distance          int
);

CREATE TABLE discounts
(
    discount_id      SERIAL PRIMARY KEY,
    owner_discount   int references users (user_id) UNIQUE,
    amount_spent     INTEGER,
    percent_discount INTEGER
);

CREATE TABLE price
(
    price_id      SERIAL PRIMARY KEY,
    car_category  VARCHAR(15) unique,
    current_price INTEGER,
    date_update   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE discount_limits
(
    discount_limits_id SERIAL PRIMARY KEY,
    bottom_limit       INTEGER NOT NULL,
    top_limit          INTEGER,
    percent            INT     NOT NULL
);

INSERT INTO price(car_category, current_price)
values ('ECONOMY', 1);
INSERT INTO price(car_category, current_price)
values ('STANDARD', 2);
INSERT INTO price(car_category, current_price)
values ('BUSYNESS', 3);

INSERT INTO users(first_name, last_name, phone, user_role, email, password)
VALUES ('Федор', 'Зотов', '380993785685', 'Admin', 'examples@google.com',
        '$31$13$84KCO4I69DF1thjkemsaWGtK6K9HDs-CUhacRn4x10M');

INSERT INTO users(first_name, last_name, phone, user_role, email, password)
VALUES ('IVAN', 'IVANOV', '380993335685', 'User', 'examples2@google.com',
        '$31$13$84KCO4I69DF1thjkemsaWGtK6K9HDs-CUhacRn4x10M');

INSERT INTO users(first_name, last_name, phone, user_role, email, password)
VALUES ('PETER', 'PETROV', '380993335689', 'User', 'examples3@google.com',
        '$31$13$84KCO4I69DF1thjkemsaWGtK6K9HDs-CUhacRn4x10M');

INSERT INTO users(first_name, last_name, phone, user_role, email, password)
VALUES ('Андрей', 'Плеханов', '380999435685', 'User', 'examples4@google.com',
        '$31$13$84KCO4I69DF1thjkemsaWGtK6K9HDs-CUhacRn4x10M');

INSERT INTO users(first_name, last_name, phone, user_role, email, password)
VALUES ('Давид', 'Голиафов', '380993435685', 'User', 'examples5@google.com',
        '$31$13$84KCO4I69DF1thjkemsaWGtK6K9HDs-CUhacRn4x10M');

INSERT INTO users(first_name, last_name, phone, user_role, email, password)
VALUES ('Василий', 'Кузнецов', '380963437885', 'User', 'examples6@google.com',
        '$31$13$84KCO4I69DF1thjkemsaWGtK6K9HDs-CUhacRn4x10M');

INSERT INTO cars(car_number, car_name, category, capacity)
VALUES ('AX197A1', 'Chevrole Aveo', 'ECONOMY', 4);

INSERT INTO cars(car_number, car_name, category, capacity)
VALUES ('AX098A1', 'Шевроле Авео', 'ECONOMY', 3);

INSERT INTO cars(car_number, car_name, category, capacity)
VALUES ('AX097В9', 'Шевроле Авео', 'ECONOMY', 6);

INSERT INTO cars(car_number, car_name, category, capacity)
VALUES ('AX797A1', 'Шевроле AVEO', 'ECONOMY', 3);

INSERT INTO cars(car_number, car_name, category, capacity)
VALUES ('AX807A1', 'BMW m3', 'STANDARD', 2);

INSERT INTO cars(car_number, car_name, category, capacity)
VALUES ('AX847A1', 'BMW m6', 'STANDARD', 6);

INSERT INTO cars(car_number, car_name, category, capacity)
VALUES ('AX817A1', 'BMW m2', 'STANDARD', 3);

INSERT INTO cars(car_number, car_name, category, capacity)
VALUES ('AX877A1', 'Tesla', 'BUSYNESS', 4);

INSERT INTO cars(car_number, car_name, category, capacity)
VALUES ('AX889A1', 'Тесла', 'BUSYNESS', 2);

INSERT INTO cars(car_number, car_name, category, capacity)
VALUES ('AX863A1', 'Honda Accent', 'BUSYNESS', 3);

INSERT INTO price(car_category, current_price)
VALUES ('STANDARD', 2);

INSERT INTO price(car_category, current_price)
VALUES ('ECONOMY', 1);

INSERT INTO price(car_category, current_price)
VALUES ('BUSYNESS', 3);

INSERT INTO discount_limits(bottom_limit, top_limit, percent)
VALUES (1000, 5000, 3);

INSERT INTO discount_limits(bottom_limit, top_limit, percent)
VALUES (5000, 10000, 5);

INSERT INTO discount_limits(bottom_limit, top_limit, percent)
VALUES (10000, 999999, 10);

INSERT INTO orders(cars_numbers, client_id, cost, percent_discount, create_date, distance)
VALUES ('{AX807A1}', 2, 400, 0, '07.01.2023', 31);

INSERT INTO orders(cars_numbers, client_id, cost, percent_discount, create_date, distance)
VALUES ('{AX807A1}', 2, 600, 0, '07.01.2023', 45);

INSERT INTO orders(cars_numbers, client_id, cost, percent_discount, create_date, distance)
VALUES ('{AX877A1}', 3, 1500, 0, '07.01.2023', 70);

INSERT INTO orders(cars_numbers, client_id, cost, percent_discount, create_date, distance)
VALUES ('{AX877A1,AX889A1,AX863A1}', 5, 4500, 0, '07.01.2023', 70);





