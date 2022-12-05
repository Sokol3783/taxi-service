package org.example.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public interface DAO<T extends Serializable> {

  T create(T model, Connection con);

  void update(T model, Connection con);

  T get(int id, Connection con);

  List<T> getAll(Connection con);

  void delete(int id, Connection con);

}
