package org.example.dao;

import org.example.models.Car;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface OrderDAO<T extends Serializable> extends DAO<T> {

    T getByNumber(String number);

    List<T> getByNumbers(List<String> numbers);

    T replaceCar(T model, Map<Car, List<Car>> cars);
}
