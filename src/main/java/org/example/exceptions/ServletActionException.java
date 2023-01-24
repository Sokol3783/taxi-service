package org.example.exceptions;

public class ServletActionException extends Throwable {

    public final static String LOGIN_ERROR = "Something went wrong, you don't authorize";

    public ServletActionException() {
        super();
    }

    public ServletActionException(String message) {
        super(message);
    }

    public ServletActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServletActionException(Throwable cause) {
        super(cause);
    }
}
