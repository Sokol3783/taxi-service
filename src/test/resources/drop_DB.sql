DO $do$
    DECLARE
        _db       TEXT := 'test_taxi';
        _user     TEXT := 'postgres';
        _password TEXT := 'postgres';
    BEGIN
        PERFORM dblink_connect('host = localhost user = ' || _user || '
                               password = ' || _password ||
                               ' dbname = ' || _db);

        PERFORM dblink_exec(' DROP TABLE IF EXISTS users CASCADE ');
        PERFORM dblink_exec(' DROP TABLE IF EXISTS cars CASCADE ');
        PERFORM dblink_exec(' DROP TABLE IF EXISTS orders CASCADE ');
        PERFORM dblink_exec(' DROP TABLE IF EXISTS ordered_cars ');
        PERFORM dblink_exec(' DROP TABLE IF EXISTS discounts CASCADE ');
        PERFORM dblink_exec(' DROP TABLE IF EXISTS discount_limits CASCADE ');
        PERFORM dblink_exec(' DROP TABLE IF EXISTS price ');

        PERFORM dblink_connect(' host = localhost user = ' || _user || '
                               password = ' || _password ||
                               ' dbname = ' || current_database());

        PERFORM dblink_exec('CREATE OR REPLACE FUNCTION terminate_sessions ()
            RETURNS VOID AS
            $$
            BEGIN
            EXECUTE format(''SELECT pg_terminate_backend(pg_stat_activity.pid)
							  FROM pg_stat_activity
							  WHERE pg_stat_activity.datname = ''''test_taxi''''
								AND pid <> pg_backend_pid()'');
            END;
            $$
            LANGUAGE plpgsql;
        ');

        PERFORM terminate_sessions();

        PERFORM dblink_exec(' DROP DATABASE IF EXISTS test_taxi ');
        PERFORM dblink_exec(' DROP USER IF EXISTS testadmin ');

        PERFORM dblink_disconnect();
    END;
$do$;