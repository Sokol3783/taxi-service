package org.example.dao;

import java.io.Serializable;
import java.util.List;

public interface DAO<T extends Serializable> {

    T create(T model);

    void update(T model);

    T get(int id);

    List<T> getAll();

    void delete(int id);

}
