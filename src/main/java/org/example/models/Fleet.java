package org.example.models;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import org.example.controllers.managers.CarManager;

public class Fleet {

  private final CopyOnWriteArraySet<Car> carsAvailableToOrder;
  private final CopyOnWriteArraySet<Car> carsOnRoute;

  private final CarManager manager;
  private static Fleet fleet;

  public Fleet() {
    carsAvailableToOrder = new CopyOnWriteArraySet();
    carsOnRoute = new CopyOnWriteArraySet();
    manager = new CarManager();
  }

  public static Fleet getInstance() {
    synchronized (Fleet.class) {
      if (fleet == null) {
        fleet = new Fleet();
      }
    }
    return fleet;
  }


  /*
  Set status "available to order" to all cars, except cars "on board"
  */
  public void setCarsAvailableToOrder() {
    List<Car> cars = manager.findAll();
    carsOnRoute.addAll(cars.stream().filter(s -> !carsOnRoute.contains(s)).toList());
  }

  /*
  Deactivate all car;
  */
  public void deactivateCars() {
    carsOnRoute.clear();
    carsAvailableToOrder.clear();
  }

  public void deactivateCar(String number) {
    boolean carDeactivate = carsAvailableToOrder.removeIf(s -> {
      return s.getNumber().equalsIgnoreCase(number);
    });

    if (!carDeactivate) {
      carsOnRoute.removeIf(s -> {
        return s.getNumber().equalsIgnoreCase(number);
      });
    }
  }

  public boolean activateCar(String number) {
    if (carsAvailableToOrder.stream().anyMatch(s -> s.getNumber().equalsIgnoreCase(number))
        | carsOnRoute.stream().anyMatch(s -> s.getNumber().equalsIgnoreCase(number))) {
      return true;
    }
    return carsAvailableToOrder.add(manager.findByNumber(number));
  }
}
