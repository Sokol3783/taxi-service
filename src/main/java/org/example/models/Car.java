package org.example.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.models.taxienum.CarCategory;

import java.io.Serializable;

@Builder
@Getter(lazy = true)
@EqualsAndHashCode
@ToString
public class Car implements Serializable {
  private static final long serialVersionUID = 33;
  private final User driver;
  private final String number;
  private final CarCategory category;
  private final int capacity;
  private final String carName;
}
