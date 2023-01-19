package org.example.dao;

import java.io.Serializable;

public interface DAOUser<T extends Serializable> extends DAO<T> {

    T get(String login);

    T findUserPhoneMailAndPassword(String login, String password);

    void updatePassword(T model, String newPassword);

    T create(T model, String password);
}
