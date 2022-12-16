package org.example.models.taxienum;

public enum UserRole {
  ADMIN,
  USER,
  DRIVER;

  public static UserRole getRole(String role) {
    return switch (role.toUpperCase()) {
      case "ADMIN" -> ADMIN;
      case "USER" -> USER;
      case "DRIVER" -> DRIVER;
      default -> throw new IllegalStateException("Unexpected value: " + role);
    };

  }
}
