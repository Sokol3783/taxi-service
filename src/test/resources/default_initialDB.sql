DO $do$
    DECLARE
        _db       TEXT := 'test_taxi';
        _user     TEXT := 'postgres';
        _password TEXT := 'postgres';
    BEGIN
        DROP EXTENSION IF EXISTS dblink;
        CREATE EXTENSION IF NOT EXISTS dblink;
        IF EXISTS(SELECT 1 FROM pg_database WHERE datname = _db) THEN
            RAISE NOTICE 'Database already exists';
        ELSE


            PERFORM dblink_connect('host=localhost user=' || _user || ' password=' || _password ||
                                   ' dbname=' || current_database());

            PERFORM dblink_exec('DO $$
            BEGIN
            IF NOT EXISTS(
                SELECT
                FROM pg_catalog.pg_roles
                WHERE rolname = ''testadmin''
                ) THEN
                CREATE USER testadmin WITH PASSWORD ''testadmin123'';
                END IF;
            END $$;');

            PERFORM dblink_exec('CREATE DATABASE ' || _db || ' OWNER testadmin');


            PERFORM dblink_disconnect();

            PERFORM dblink_connect('host=localhost user=' || _user || ' password=' || _password ||
                                   ' dbname=' || _db);

            PERFORM dblink_exec('ALTER DEFAULT PRIVILEGES IN SCHEMA public
                GRANT ALL ON TABLES TO testadmin');

            PERFORM dblink_exec('CREATE TABLE IF NOT EXISTS users
                (user_id SERIAL PRIMARY KEY, password varchar(50), first_name varchar(50), last_name varchar(50), phone varchar(13) unique, birthday DATE, email varchar(50) unique, user_role varchar(15))');

            PERFORM dblink_exec('CREATE TABLE IF NOT EXISTS test_taxi.public.users
                (user_id SERIAL PRIMARY KEY, password varchar(50), first_name varchar(50), last_name varchar(50), phone varchar(13) unique, birthday DATE, email varchar(50) unique, user_role varchar(15));');
            PERFORM dblink_exec('CREATE TABLE IF NOT EXISTS test_taxi.public.cars
                (car_id SERIAL PRIMARY KEY, car_number varchar(30) unique, car_name varchar(100), category varchar(15), capacity INTEGER);');
            PERFORM dblink_exec('CREATE TABLE IF NOT EXISTS test_taxi.public.orders
                (order_id SERIAL PRIMARY KEY, client_id INTEGER references users (user_id), address_departure varchar(250), destination varchar(250), cost INTEGER, percent_discount INTEGER, CREATE_date timestamp, order_number INTEGER, distance INTEGER);');
            PERFORM dblink_exec('CREATE TABLE IF NOT EXISTS test_taxi.public.ordered_cars
                (ordered_cars_id SERIAL PRIMARY KEY, order_id INTEGER references orders (order_id), car_id INTEGER references cars (car_id));');
            PERFORM dblink_exec('CREATE TABLE IF NOT EXISTS discounts
                (discount_id SERIAL PRIMARY KEY, owner_discount INTEGER references users (user_id), amount_spent INTEGER, percent_discount INTEGER);');
            PERFORM dblink_exec('CREATE TABLE IF NOT EXISTS test_taxi.public.price
                (price_id SERIAL PRIMARY KEY, car_category VARCHAR(15) unique, current_price INTEGER, date_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);');
            PERFORM dblink_exec('CREATE TABLE IF NOT EXISTS test_taxi.public.discount_limits
                (discount_limits_id SERIAL PRIMARY KEY, bottom_limit INTEGER NOT NULL, top_limit INTEGER, percent INTEGER NOT NULL);');

            PERFORM dblink_exec('GRANT ALL ON SCHEMA public TO testadmin');
            PERFORM dblink_exec('GRANT ALL ON ALL TABLES IN SCHEMA public TO testadmin');
            PERFORM dblink_exec('GRANT ALL ON ALL SEQUENCES IN SCHEMA public TO testadmin');
            PERFORM dblink_exec('GRANT ALL ON ALL FUNCTIONS IN SCHEMA public TO testadmin');
            /*
            PERFORM dblink_exec('GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO testadmin');
            PERFORM dblink_exec('GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO testadmin');
             */

            PERFORM dblink_disconnect();

        END IF;
    END $do$;