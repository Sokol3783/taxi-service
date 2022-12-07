package org.example.models;

import java.util.Date;
import lombok.Builder;

@Builder
public class DriverLicense {

  private final User driver;
  private final String number;
  private final String Category;
  private final Date dateIssue;

  public User getDriver() {
    return driver;
  }

  public String getNumber() {
    return number;
  }

  public String getCategory() {
    return Category;
  }

  public Date getDateIssue() {
    return dateIssue;
  }

}
