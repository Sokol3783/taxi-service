package org.example.dao;

import org.example.models.taxienum.CarCategory;

import java.io.Serializable;
import java.util.List;

public interface DAOCar<T extends Serializable> extends DAO<T> {

    T getByNumber(String number);

    List<T> getAllByCategory(CarCategory category);

    void updateNumber(T model, String number);
}
