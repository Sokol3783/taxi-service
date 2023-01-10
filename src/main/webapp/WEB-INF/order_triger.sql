CREATE OR REPLACE FUNCTION update_discount() RETURNS TRIGGER
AS
$update_discount$
DECLARE
    total_amount     numeric(15, 2);
    percent_discount numeric(2, 2);
    client_id        int;
BEGIN

    -- Work out the increment/decrement amount(s).
    IF (TG_OP = 'DELETE') THEN

        total_amount = (SELECT sum(cost) FROM orders WHERE orders.client_id = OLD.client_id);
        percent_discount = (SELECT percent FROM discount_limits WHERE total_amount between bottom_limit AND top_limit);
        client_id = OLD.client_id;

    ELSIF (TG_OP = 'UPDATE') OR (TG_OP = 'INSERT') THEN

        total_amount = (SELECT sum(cost) FROM orders WHERE orders.client_id = NEW.client_id);
        percent_discount = (SELECT percent FROM discount_limits WHERE total_amount between bottom_limit AND top_limit);
        client_id = NEW.client_id;
    END IF;

    -- Insert or update the summary row with the new values.
    <<insert_update>>
    LOOP
        UPDATE discounts
        SET amount_spent     = total_amount,
            percent_discount = percent_discount
        WHERE discounts.owner_discount = client_id;

        EXIT insert_update WHEN found;

        BEGIN
            INSERT INTO discounts (discount_id,
                                   owner_discount,
                                   amount_spent,
                                   percent_discount)
            VALUES (default,
                    owner_discount,
                    total_amount,
                    percent_discount);

            EXIT insert_update;

        EXCEPTION
            WHEN UNIQUE_VIOLATION THEN
            -- do nothing
        END;
    END LOOP insert_update;

    RETURN NULL;

END;
$update_discount$ LANGUAGE plpgsql;

CREATE TRIGGER update_discount
    AFTER INSERT OR UPDATE OR DELETE
    ON orders
    FOR EACH ROW
EXECUTE FUNCTION update_discount();