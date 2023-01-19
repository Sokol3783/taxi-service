package org.example.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Order implements Serializable {
    private static final long serialVersionUID = 29;
    int id;
    String addressDeparture;
    String destination;
    List<Car> cars;
    User client;
    long cost;
    int percentDiscount;
    long orderNumber;
    LocalDateTime createAt;
    long distance;
}
