package org.example.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Car implements Serializable, Container {

  @Serial
  private static final long serialVersionUID = 33;
  private final User driver;
  private final String number;
  private final CarCategory category;
  private final int capacity;
  private final String carName;

  private long id;

  @Override
  public boolean isEmpty() {
    if (category == null) {
      return true;
    }
    return number.isEmpty() || capacity <= 0 || carName.isEmpty();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Car car = (Car) o;
    return capacity == car.capacity && driver.equals(car.driver) && number.equals(car.number)
        && category == car.category && Objects.equals(carName, car.carName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(driver, number, category, capacity, carName);
  }

  public void setId(long id) {
    this.id = id;
  }

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

  enum CarStatus {
    AvailableToOrder,
    OnRoute
  }

}
