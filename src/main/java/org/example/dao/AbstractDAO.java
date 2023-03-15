package org.example.dao;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractDAO<T extends Serializable> implements DAO<T> {

  @Override
  public void update(T model) {
    throw new UnsupportedOperationException();
  }

  @Override
  public T create(T model) {
    return null;
  }

  @Override
  public T get(long id) {
    return null;
  }

  @Override
  public List<T> getAll() {
    return null;
  }

  @Override
  public void delete(long id) {
    throw new UnsupportedOperationException("SAY NO TO DELETIONS!");
  }
}
