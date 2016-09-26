package com.atroshonok.services;

import java.io.Serializable;
import java.util.List;

import com.atroshonok.dao.entities.User;
import com.atroshonok.services.exceptions.ErrorAddingUserServiceException;
import com.atroshonok.services.exceptions.ErrorUpdatingUserServiceException;
import com.atroshonok.services.exceptions.LoginAlreadyExistServiceException;
import com.atroshonok.services.exceptions.ServiceException;

public interface IUserService {

    User getUserByLoginPassword(String login, String password) throws ServiceException;

    /**
     * Saves an user to the database. Throws {@code LoginAlreadyExistServiceException}
     * if a user with so login already exist in the database.
     * 
     * @param user
     * @throws ErrorAddingUserServiceException
     * @throws LoginAlreadyExistServiceException
     */
    void saveUserToDataBase(User user) throws ErrorAddingUserServiceException, LoginAlreadyExistServiceException;

    List<User> getAllUsers() throws ServiceException;

    User getUserById(Serializable userId);

    void updateUserData(User user) throws ErrorUpdatingUserServiceException;

}
