package com.atroshonok.dao;

import java.util.List;

import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.exceptions.DaoException;

public interface IUserDao extends IDao<User> {

    User getUserByLoginPassword(String login, String password) throws DaoException;

    List<User> getAllUsers();

    /**
     * Returns a list of the users if they have the same login like a method parameter.
     * If there are not any matchings this method returns an empty collection.
     * @param login
     * @return
     */
    List<User> getUsersByLogin(String login);

}
