package org.example.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.models.taxienum.UserRole;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Getter(lazy = true)
@EqualsAndHashCode
@ToString
public class User implements Serializable, Container {

  private static final long serialVersionUID = 27;

  @ToString.Exclude
  private final String password;
  private final UserRole role;
  private final String firstName;
  private final String secondName;
  private final LocalDate birthDate;
  private final String phone;
  private final String email;

  public boolean isEmpty() {
    if (role == null) {
      return true;
    }
    return firstName.isEmpty() && secondName.isEmpty()
        && phone.isEmpty() && email.isEmpty();
  }

}
