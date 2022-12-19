--Focused on POSTGRE SQL

DROP TABLE IF EXISTS "discounts";
DROP TABLE IF EXISTS "cars";
DROP TABLE IF EXISTS "orders";
DROP TABLE IF EXISTS "users" cascade;

SELECT 'create DATABASE TAXI
      ENCODING UTF8'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'TAXI');

SELECT 'create USER IF NOT EXISTS TAXIADMIN with PASSWORD adminpassword123'
  WHERE NOT EXISTS (SELECT FROM pg_user WHERE pg_user.usename = 'TAXIADMIN');

GRANT ALL PRIVILEGES ON DATABASE "TAXI" to "TAXIADMIN";

create TABLE users (
	user_id SERIAL PRIMARY KEY,
	password varchar(30),
	first_name varchar(50),
	last_name varchar(50),
	phone varchar(13) unique,
  birthday DATE,
	email varchar(50),
	user_role varchar(15)
);

create TABLE cars (
	car_id SERIAL PRIMARY KEY,
	car_number varchar(30) unique,
	car_name varchar(100),
	category varchar(15),
	capacity int
);

create TABLE orders (
	order_id SERIAL PRIMARY KEY,
	car_orders INTEGER[],
	client_id int references users(user_id),
  address_departure varchar(250),
  destination varchar(250),
  cost INTEGER,
  discount INTEGER,
  create_date timestamp,
  order_number INTEGER
);

CREATE TABLE discounts (
  discount_id SERIAL PRIMARY KEY,
  owner_discount int references users(user_id),
  amount_spent INTEGER,
  procent_discount INTEGER
);

CREATE TABLE price (
  price_id SERIAL PRIMARY KEY,
  car_category VARCHAR(15),
  current_price INTEGER
);

INSERT INTO users(first_name, last_name, phone, user_role, email, password)
       VALUES('Федор', 'Зотов', '380993785685', 'Admin', 'examples@google.com', 'password1');

INSERT INTO users(first_name, last_name, phone,  user_role, email, password)
    VALUES('IVAN', 'IVANOV', '380993335685', 'User', 'examples2@google.com', 'password1');

INSERT INTO users(first_name, last_name, phone,  user_role, email, password)
       VALUES('PETER', 'PETROV', '380993335689', 'User', 'examples3@google.com', 'password1');

INSERT INTO users(first_name, last_name, phone,  user_role, email, password)
       VALUES('Андрей', 'Плеханов', '380999435685', 'User', 'examples4@google.com', 'password1');

INSERT INTO users(first_name, last_name, phone,  user_role, email, password)
       VALUES('Давид', 'Голиафов', '380993435685', 'User', 'examples5@google.com', 'password1');

INSERT INTO users(first_name, last_name, phone,  user_role, email, password)
       VALUES('Василий', 'Кузнецов', '380963437885', 'User', 'examples6@google.com', 'password1');

INSERT INTO cars(car_number, car_name,category, capacity)
        VALUES('AX097A1','Шеврлое Авео', 'Comfort', 4);

INSERT INTO cars(car_number, car_name,category, capacity)
VALUES('AX807A1','BMW m3', 'Premium', 2);