--Focused on POSTGRE SQL

DROP DATABASE IF EXISTS test_taxi;*!=

DROP USER IF EXISTS test_admin;*!=

DO
$do$
    BEGIN
        IF EXISTS (
                SELECT FROM pg_catalog.pg_roles
                WHERE  rolname = 'test_admin') THEN
        ELSE
            CREATE ROLE test_admin LOGIN PASSWORD 'adminpassword123';
            GRANT ALL PRIVILEGES ON DATABASE "test_taxi" to "test_admin";
        END IF;
    END
$do$*!=


DROP TABLE IF EXISTS users;*!=
DROP TABLE IF EXISTS cars;*!=
DROP TABLE IF EXISTS orders;*!=
DROP TABLE IF EXISTS ordered_cars;*!=
DROP TABLE IF EXISTS discounts;*!=
DROP TABLE IF EXISTS price;*!=
DROP TABLE IF EXISTS discount_limits;*!=

CREATE TABLE IF NOT EXISTS users
(
    user_id    SERIAL PRIMARY KEY,
    password   varchar(50),
    first_name varchar(50),
    last_name  varchar(50),
    phone      varchar(13) unique,
    birthday   DATE,
    email      varchar(50) unique,
    user_role  varchar(15)
);*!=

CREATE TABLE IF NOT EXISTS cars
(
    car_id     SERIAL PRIMARY KEY,
    car_number varchar(30) unique,
    car_name   varchar(100),
    category   varchar(15),
    capacity   int
);*!=

CREATE TABLE IF NOT EXISTS orders
(
    order_id          SERIAL PRIMARY KEY,
    client_id         int references users (user_id),
    address_departure varchar(250),
    destination       varchar(250),
    cost              INTEGER,
    percent_discount  INTEGER,
    CREATE_date       timestamp,
    order_number      INTEGER,
    distance          int
)

CREATE TABLE IF NOT EXISTS ordered_cars
(
    ordered_cars_id SERIAL  PRIMARY KEY,
    order_id                int references orders (order_id),
    car_id                  int references cars (car_id)
);*!=

CREATE TABLE  IF NOT EXISTS discounts
(
    discount_id      SERIAL PRIMARY KEY,
    owner_discount   int references users (user_id) UNIQUE,
    amount_spent     INTEGER,
    percent_discount INTEGER
);*!=

CREATE TABLE IF NOT EXISTS price
(
    price_id      SERIAL PRIMARY KEY,
    car_category  VARCHAR(15) unique,
    current_price INTEGER,
    date_update   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);*!=

CREATE TABLE IF NOT EXISTS discount_limits
(
    discount_limits_id SERIAL PRIMARY KEY,
    bottom_limit       INTEGER NOT NULL,
    top_limit          INTEGER,
    percent            INT     NOT NULL
);*!=