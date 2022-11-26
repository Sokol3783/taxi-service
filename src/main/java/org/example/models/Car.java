package org.example.models;

import lombok.Builder;
import org.example.models.taxienum.CarCategory;

@Builder
public class Car {
    private final User driver;
    private final String number;
    private final CarCategory category;
    private final int capacity;
}
