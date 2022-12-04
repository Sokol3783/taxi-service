package org.example.models;

import java.util.Date;
import lombok.Builder;

@Builder
public class DriverLicense {

  private final User driver;
  private final String number;
  private final String Category;
  private final Date dateIssue;
}
