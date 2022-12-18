package org.example.models;

import java.io.Serializable;
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
  List<User> drivers;
  List<Car> cars;
  User client;
  CarCategory category;
  long cost;
  int discount;
  long orderNumber;
}
