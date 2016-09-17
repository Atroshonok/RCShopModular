package com.atroshonok.dao;

import java.util.List;

import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.exceptions.DaoException;

public interface IUserDao extends IDao<User> {

    User getUserByLoginPassword(String login, String password) throws DaoException;

    List<User> getAllUsers();

}
