--Focused on POSTGRE SQL
--All mistakes and warnings specify to do select query of create tables

--I don't find "best practice", so do function to test_base schema and then call it

CREATE SCHEMA IF NOT EXISTS test_base;

CREATE OR REPLACE FUNCTION test_base.create_db(
	)
    RETURNS void
    LANGUAGE 'sql'
AS $BODY$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'TAXI') THEN
       CREATE DATABASE TEST_TAXI ENCODING UTF8;
    END IF;
END
$BODY$;

SELECT test_base.create_db;

CREATE USER IF NOT EXISTS TAXIADMIN with PASSWORD adminpassword123

GRANT ALL PRIVILEGES ON DATABASE "TEST_TAXI" to "TAXIADMIN";

CREATE TABLE IF NOT EXISTS test_base.users
(
    user_id    SERIAL PRIMARY KEY,
    password   varchar(50),
    first_name varchar(50),
    last_name  varchar(50),
    phone      varchar(13) unique,
    birthday   DATE,
    email      varchar(50) unique,
    user_role  varchar(15)
) 

CREATE TABLE IF NOT EXISTS test_base.cars
(
    car_id     SERIAL PRIMARY KEY,
    car_number varchar(30) unique,
    car_name   varchar(100),
    category   varchar(15),
    capacity   int
) 

CREATE TABLE IF NOT EXISTS test_base.orders
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

CREATE TABLE  IF NOT EXISTS test_base.discounts
(
    discount_id      SERIAL PRIMARY KEY,
    owner_discount   int references users (user_id) UNIQUE,
    amount_spent     INTEGER,
    percent_discount INTEGER
)  

CREATE TABLE IF NOT EXISTS test_base.price
(
    price_id      SERIAL PRIMARY KEY,
    car_category  VARCHAR(15) unique,
    current_price INTEGER,
    date_update   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
)  

CREATE TABLE  IF NOT EXISTS test_base.discount_limits
(
    discount_limits_id SERIAL PRIMARY KEY,
    bottom_limit       INTEGER NOT NULL,
    top_limit          INTEGER,
    percent            INT     NOT NULL
)





