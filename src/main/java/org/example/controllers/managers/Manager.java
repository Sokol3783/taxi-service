package org.example.controllers.managers;

import java.util.List;

public interface Manager<T> {

  public T create(T subject);

  public void delete(int id);

  public T findById(int id);

  public void update(T subject);

  public List<T> findAll();

}
