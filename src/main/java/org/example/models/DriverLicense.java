package org.example.models;

import java.util.Date;
import lombok.Builder;

@Builder
public class DriverLicense {
  private String number;
  private String Category;
  private Date dateIssue;
}
