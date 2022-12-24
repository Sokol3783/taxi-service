package org.example.exceptions;

public class DAOException extends RuntimeException {

    public static final String PRICE_NOT_FOUND = "Price hasn't been found.";
    public static final String DISCOUNT_NOT_FOUND = "Discount hasn't been found.";
    public static String USER_NOT_CREATE = "User hasn't been created.";

    public static String USER_NOT_DELETE = "User hasn't been deleted.";
    public static String USER_NOT_FOUND = "User hasn't been found";
    public static String ORDER_NOT_FOUND = "ORDER hasn't been found";

    public static String CAR_NOT_UPDATE = "Car hasn't been update";

    public static String CAR_NOT_FOUND = "Car hasn't been found";

    public static String ORDER_NOT_CREATE = "Order hasn't been created.";

    public static String USER_NOT_UPDATE = "User hasn't been update";
    public static String ROLLBACK_FAIL = "Rollback failed";


    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

    protected DAOException(String message, Throwable cause, boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
