--Focused on POSTGRE SQL


DROP TABLE IF EXISTS "driverlicenses";
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
	firstName varchar(50),
	lastName varchar(50),
	phone varchar(13) unique,
  birthDate varchar(50),
	email varchar(50),
	role varchar(15)
);

create TABLE cars (
	car_id SERIAL PRIMARY KEY,
	driver int references users(id),
	carNumber varchar(30) unique,
	carName varchar(100),
	category varchar(15),
	capacity int
);

create TABLE orders (
	order_id SERIAL PRIMARY KEY,
	driver_id int references users(id),
	client_id int references users(id),
	creationDate date,
	startTrip date,
	finishTrip date,
	startingPoint varchar(50) not null,
	finishPoint varchar(50) not null,
	cost int not null,
	feedback varchar(250) ,
	serviceQuality int,
	orderNumber int,
	status varchar(20)
);

INSERT INTO users(firstName, lastName, phone,role, email, password)
       VALUES('Федор', 'Зотов', '380993785685', 'Administrator', 'examples@goole.com', 'password1');

INSERT INTO users(firstName, lastName, phone,  role, email, password)
       VALUES('Матвей', 'Ильин', '380993185685', 'Driver', 'examples1@goole.com', 'password1');

INSERT INTO users(firstName, lastName, phone,  role, email, password)
       VALUES('Nicolas', 'Cage', '380993725685', 'Driver', 'examples1@goole.com', 'password1');

INSERT INTO users(firstName, lastName, phone,  role, email, password)
    VALUES('IVAN', 'IVANOV', '380993335685', 'User', 'examples2@goole.com', 'password1');

INSERT INTO users(firstName, lastName, phone,  role, email, password)
       VALUES('PETER', 'PETROV', '380993335689', 'User', 'examples3@goole.com', 'password1');

INSERT INTO users(firstName, lastName, phone,  role, email, password)
       VALUES('Андрей', 'Плеханов', '380999435685', 'User', 'examples4@goole.com', 'password1');

INSERT INTO users(firstName, lastName, phone,  role, email, password)
       VALUES('Давид', 'Голиафов', '380993435685', 'User', 'examples5@goole.com', 'password1');

INSERT INTO users(firstName, lastName, phone,  role, email, password)
       VALUES('Василий', 'Кузнецов', '380963437885', 'User', 'examples6@goole.com', 'password1');

INSERT INTO cars(driver, carNumber, carName,category, capacity)
        VALUES(2, 'AX097A1','Шеврлое Авео', 'Comfort', 4);

INSERT INTO cars(driver, carNumber, carName,category, capacity)
VALUES(3, 'AX807A1','BMW m3', 'Premium', 2);

