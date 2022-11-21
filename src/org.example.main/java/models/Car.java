package models;

import lombok.Builder;
import modelsenum.CarCategory;

@Builder
public class Car {
    private final User driver;
    private final String number;
    private final CarCategory category;
    private final int capacity;
}
