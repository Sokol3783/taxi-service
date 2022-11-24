--Focused on POSTGRE SQL

DROP DATABASE IF EXISTS 'TAXI';

DROP USER IF EXISTS 'TAXIADMIN';

DROP TABLE IF EXISTS 'users';
DROP TABLE IF EXISTS 'cars';
DROP TABLE IF EXISTS 'driverlicenses';
DROP TABLE IF EXISTS 'orders';

create DATABASE 'TAXI';

create USER 'TAXIADMIN' with PASSWORD 'adminpassword123';
GRANT ALL PRIVILEGES ON DATABASE 'DATABASE_name' to my_username;

create TABLE users (
	id int primary key,
	password varchar(30) unique,
	firstName varchar(50),
	lastName varchar(50),
	phone varchar(13) unique,
  birthDate varchar(50),
	email varchar(50),
	role varchar(15)
);

create TABLE cars (
	id serial primary key,
	driver int references users(id),
	carNumber varchar(30) unique,
	category varchar(15),
	capacity int
);

create TABLE orders (
	id serial primary key,
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
