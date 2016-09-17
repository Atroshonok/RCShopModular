package com.atroshonok.services;

import java.io.Serializable;
import java.util.List;

import com.atroshonok.dao.entities.User;
import com.atroshonok.services.exceptions.ErrorAddingUserServiceException;
import com.atroshonok.services.exceptions.ErrorUpdatingUserServiceException;

public interface IUserService {

    User getUserByLoginPassword(String login, String password);

    void saveUserToDataBase(User user) throws ErrorAddingUserServiceException;

    List<User> getAllUsers();

    User getUserById(Serializable userId);

    void updateUserData(User user) throws ErrorUpdatingUserServiceException;

}
