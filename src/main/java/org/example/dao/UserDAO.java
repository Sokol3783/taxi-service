package org.example.dao;

import java.io.Serializable;

public interface UserDAO<T extends Serializable> extends DAO<T> {

  //SAVE hash of password
  T getUserPhoneMailAndPassword(String login, String password);

  //SAVE hash of password
  void updatePassword(T model, String newPassword);

  T create(T model, String password);
}
