package org.example.models;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;
import org.example.models.taxienum.UserRole;

@Builder
public class User implements Serializable {

  private final String password;
  private final UserRole role;
  private final String firstName;
  private final String secondName;
  private final Date birthDate;
  private final String phone;
  private final String email;
  

  public UserRole getRole() {
    return role;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getSecondName() {
    return secondName;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

}
