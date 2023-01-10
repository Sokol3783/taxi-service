package org.example.exceptions;

public class ForwardException extends RuntimeException {

  public static final String FORWARDING_EXCEPTION = "Some problem with forwarding to url %s";
  public static String FORWARD_EMPTY_URL = "There are no URL in forward";

  public ForwardException() {
    super();
  }

  public ForwardException(String message) {
    super(message);
  }

  public ForwardException(String message, Throwable cause) {
    super(message, cause);
  }

  public ForwardException(Throwable cause) {
    super(cause);
  }

  protected ForwardException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}



