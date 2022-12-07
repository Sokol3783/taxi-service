package org.example.models;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Builder;
import org.example.models.taxienum.UserRole;

@Builder
public class User implements Serializable {

  private final String password;
  private final UserRole role;
  private final String firstName;
  private final String secondName;
  private final LocalDate birthDate;
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

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  public boolean isEmpty() {
    if (role == null) {
      return true;
    }
    return firstName.isEmpty() && secondName.isEmpty()
        && phone.isEmpty() && email.isEmpty();
  }

}
