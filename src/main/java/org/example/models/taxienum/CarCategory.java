package org.example.models.taxienum;

import java.util.concurrent.ConcurrentMap;

public enum CarCategory {
  ECONOMY,
  STANDARD,
  BUSYNESS;

  public static ConcurrentMap<CarCategory, Integer> prices;

  public static CarCategory getCategory(String category) {
    return switch (category.toUpperCase()) {
      case "ECONOMY" -> ECONOMY;
      case "STANDARD" -> STANDARD;
      case "BUSYNESS" -> BUSYNESS;
      default -> throw new IllegalStateException("Unexpected value: " + category);
    };

  }
}
