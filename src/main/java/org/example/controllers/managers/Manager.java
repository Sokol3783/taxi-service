package org.example.controllers.managers;

import java.sql.SQLException;
import java.util.List;

public interface Manager<T> {

  public T create(T model) throws SQLException;

  public void delete(int id);

  public T findById(int id);

  public void update(T model);

  public List<T> findAll();

}
