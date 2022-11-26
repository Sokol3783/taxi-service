package org.example.models;

import java.util.Date;
import javax.management.relation.Role;
import lombok.Builder;

@Builder
public class User {
  private final String password;
  private final Role role;
  private final String firstName;
  private final String secondName;
  private final Date birthDate;
  private final DriverLicense license;
  private final String phone;
  private final String email;
}
