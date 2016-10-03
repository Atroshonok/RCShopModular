package com.atroshonok.dao;

import java.util.List;

import com.atroshonok.dao.entities.User;

public interface IUserDao extends IDao<User> {

    /**
     * Returns an user using the given login and password. Returns null if has
     * found no one.
     * 
     * @param login
     * @param password
     * @return
     */
    User getUserByLoginPassword(String login, String password);

    /**
     * Returns the all users list. Returns an empty collection if has found no
     * one.
     * 
     * @return
     */
    List<User> getAllUsers();

    /**
     * Returns a list of the users if they have the same login like a method
     * parameter. If there are no any matchings this method returns an empty
     * collection.
     * 
     * @param login
     * @return
     */
    // why do you return list from this method?
    // shouldn't it be singular object?
    List<User> getUsersByLogin(String login);

}
