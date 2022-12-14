package org.example.models;

import java.util.Date;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class DriverLicense {

  private final User driver;
  private final String number;
  private final String Category;
  private final Date dateIssue;

}
