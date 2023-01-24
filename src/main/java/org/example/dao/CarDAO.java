package org.example.dao;

import org.example.models.taxienum.CarCategory;

import java.io.Serializable;
import java.util.List;

public interface CarDAO<T extends Serializable> extends DAO<T> {

    T getByNumber(String number);

    List<T> getByNumbers(List<String> numbers);

    List<T> getAllByCategory(CarCategory category);

    void updateNumber(T model, String number);
}
