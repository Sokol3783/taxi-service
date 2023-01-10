package org.example.models;

import org.example.controllers.managers.CarManager;
import org.example.models.taxienum.CarCategory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArraySet;

public class Fleet {

    private final CopyOnWriteArraySet<Car> carsAvailableToOrder;
    private final CopyOnWriteArraySet<Car> carsOnRoute;

    private final CarManager manager;
    private static Fleet fleet;

    private Fleet() {
        carsAvailableToOrder = new CopyOnWriteArraySet<>();
        carsOnRoute = new CopyOnWriteArraySet<>();
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
        List cars = manager.findAll();
        carsAvailableToOrder.addAll(cars.stream().filter(s -> !carsOnRoute.contains(s)).toList());
    }

    public void setCarsOnRoute(List<Car> cars) {
        if (cars != null) {
            cars.forEach(s -> {
                carsAvailableToOrder.removeIf(ca -> ca.equals(s));
                carsOnRoute.add(s);
            });
        }
    }

    /*
    Deactivate all car;
    */
    public void deactivateCars() {
        carsOnRoute.clear();
        carsAvailableToOrder.clear();
    }

    public void deactivateCar(String number) {
        boolean carDeactivate = carsAvailableToOrder.removeIf(s -> s.getNumber().equalsIgnoreCase(number));
        if (!carDeactivate) {
            carsOnRoute.removeIf(s -> s.getNumber().equalsIgnoreCase(number));
        }
    }

    public boolean activateCar(String number) {
        if (carsAvailableToOrder.stream().anyMatch(s -> s.getNumber().equalsIgnoreCase(number))
                | carsOnRoute.stream().anyMatch(s -> s.getNumber().equalsIgnoreCase(number))) {
            return true;
        }
        return carsAvailableToOrder.add(manager.findByNumber(number));
    }


    /*
    Find the car for category and minimal capacity
     */
    public Optional<Car> findFreeCarByCategory(CarCategory category, int passengers) {
        return carsAvailableToOrder.stream()
                .filter(s -> s.getCategory() != category)
                .filter(s -> s.getCapacity() >= passengers).min(Comparator.comparingInt(Car::getCapacity))
                .stream().findFirst();
    }

    public Optional<Car> findFreeCarExceptCategory(CarCategory category, int passengers) {
        return carsAvailableToOrder.stream()
                .filter(s -> s.getCategory() != (category))
                .filter(s -> s.getCapacity() >= passengers).min(Comparator.comparingInt(Car::getCapacity))
                .stream().findFirst();
    }

    public List<Car> findFreeSeveralCarsByCategory(CarCategory category, int passengers) {
        int sumCapacity = 0;
        List<Car> severalCars = new ArrayList<>();
        carsAvailableToOrder.stream().filter(s -> s.getCategory() == category)
                .sorted(Comparator.comparingInt(Car::getCapacity))
                .takeWhile(s -> {
                    severalCars.add(s);
                    return sumCapacity < passengers;
                });
        if (passengers > sumCapacity) {
            return new ArrayList<>();
        }
        return severalCars;
    }
}
