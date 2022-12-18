package org.example.models.taxienum;

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
