package org.example.dao.postgres;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.example.dao.DAO;
import org.example.models.Car;

public class CarDAO implements DAO<Car> {

  @Override
  public Car create(Car model, Connection con) throws SQLException {
    return null;
  }

  @Override
  public void update(Car model, Connection con) {

  }

  @Override
  public Car get(int id, Connection con) {
    return null;
  }

  @Override
  public List<Car> getAll(Connection con) {
    return null;
  }

  @Override
  public void delete(int id, Connection con) {
    throw new IllegalArgumentException();
  }
}
