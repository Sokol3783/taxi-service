package org.example.exceptions;

public class RedirectException extends RuntimeException {

  public static String REDIRECT_MESSAGE = "";
  public static String REDIRECT_EMPTY_URL = "There no URL in redirect";

  public RedirectException() {
    super();
  }

  public RedirectException(String message) {
    super(message);
  }

  public RedirectException(String message, Throwable cause) {
    super(message, cause);
  }

  public RedirectException(Throwable cause) {
    super(cause);
  }
}
