package org.example.models.taxienum;

public enum UserRole {
  ADMIN,
  USER,
  DRIVER;

  public static UserRole getRole(String role) {
    switch (role) {
      case "ADMIN":
        return ADMIN;
      case "USER":
        return USER;
      case "DRIVER":
        return DRIVER;
    }
    return null;
  }

}
