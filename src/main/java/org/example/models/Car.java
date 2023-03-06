package org.example.models;

import java.io.Serial;
import java.io.Serializable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.models.taxienum.CarCategory;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Car implements Serializable, Container {

  @Serial
  private static final long serialVersionUID = 33;
  private final User driver;
  private final String number;
  private final CarCategory category;
  private final int capacity;
  private final String carName;

  @Override
  public boolean isEmpty() {
    if (category == null) {
      return true;
    }
    return number.isEmpty() || capacity <= 0 || carName.isEmpty();
  }
}
