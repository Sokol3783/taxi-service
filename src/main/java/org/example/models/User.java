package org.example.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.validation.Validate;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class User implements Serializable, Container {

  @Serial
  private static final long serialVersionUID = 27;

  @ToString.Exclude
  private final String password;

  private final UserRole role;

  @Validate(regExp = "^[A-Z][a-z]{1,}$", message = "Wrong name")
  private final String firstName;

  @Validate(regExp = "^[A-Z][a-z]{1,}$", message = "Wrong second name")
  private final String secondName;


  @Validate(regExp = "\\d{4}-\\d{2}-\\d{2}", message = "Wrong birth date")
  private final LocalDate birthDate;

  @Validate(regExp = "^\\+380\\d{9}$", message = "Wrong phone")
  private final String phone;

  @Validate(regExp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Wrong e-mail")
  private final String email;
  private long id;

  @Override
  public boolean isEmpty() {
    if (role == null) {
      return true;
    }
    return firstName.isEmpty() && secondName.isEmpty()
        && phone.isEmpty() && email.isEmpty();
  }

  public void setId(long id) {
    this.id = id;
  }

  public enum UserRole {
    ADMIN,
    USER;

    public static UserRole getRole(String role) {
      return switch (role.toUpperCase()) {
        case "ADMIN" -> ADMIN;
        case "USER" -> USER;
        default -> throw new IllegalStateException("Unexpected value: " + role);
      };
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return role == user.role &&
        Objects.equals(firstName, user.firstName) &&
        Objects.equals(secondName, user.secondName) &&
        Objects.equals(birthDate, user.birthDate) &&
        Objects.equals(phone, user.phone) &&
        Objects.equals(email, user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(role, firstName, secondName, birthDate, phone, email);
  }
}
