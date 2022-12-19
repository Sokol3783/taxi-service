package org.example.controllers.managers;

import java.util.List;
import org.example.dao.BasicConnectionPool;
import org.example.dao.SimpleConnectionPool;
import org.example.dao.postgres.CarDAO;
import org.example.models.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarManager implements Manager<Car> {

  private final CarDAO dao;
  private final SimpleConnectionPool pool;
  private static final Logger log = LoggerFactory.getLogger(CarManager.class);


  public CarManager() {
    dao = new CarDAO();
    pool = BasicConnectionPool.getInstance();
  }


  @Override
  public Car create(Car model) {
    return null;
  }

  @Override
  public void delete(int id) {

  }

  @Override
  public Car findById(int id) {
    return null;
  }

  @Override
  public void update(Car model) {

  }

  @Override
  public List findAll() {
    return null;
  }

  public Car findByNumber(String number) {
    return null;
  }
}
