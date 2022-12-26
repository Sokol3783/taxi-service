CREATE OR REPLACE FUNCTION Update_Discount()
    RETURNS trigger AS
$$
BEGIN
    UPDATE discounts
    SET (amount_spent, percent_discount)=
            (SELECT sum(amount_spent),
                    max((SELECT percent
                         FROM discount_limits
                         WHERE bottom_limit <= amount_spent
                           AND top_limit > amount_spent)) AS percent_discount
             FROM orders
             WHERE orders.client_id = discounts.owner_discount);
END
$$
    LANGUAGE 'plpgsql';
/* */
CREATE TRIGGER order_insert_trigger
    AFTER INSERT
    ON "orders"
    FOR EACH ROW
EXECUTE PROCEDURE Update_Discount();