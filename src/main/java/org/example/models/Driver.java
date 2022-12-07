package org.example.models;

import lombok.Builder;

@Builder
public class Driver {

  private final User user;
  private final DriverLicense license;
  private final Car car;
}
