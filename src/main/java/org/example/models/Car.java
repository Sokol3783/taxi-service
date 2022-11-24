package models;

import lombok.Builder;
import models.enum_model.CarCategory;

@Builder
public class Car {
    private final User driver;
    private final String number;
    private final CarCategory category;
    private final int capacity;
}
