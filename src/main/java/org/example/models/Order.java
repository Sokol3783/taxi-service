package org.example.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.models.taxienum.CarCategory;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Order implements Serializable {

  int id;
  String addressDeparture;
  String destination;
  List<Car> cars;
  User client;
  CarCategory category;
  long cost;
  int discount;
  long orderNumber;
  LocalDateTime createAt;
  long distance;
}
