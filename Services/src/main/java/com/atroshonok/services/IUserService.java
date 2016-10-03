package com.atroshonok.services;

import java.io.Serializable;
import java.util.List;

import com.atroshonok.dao.entities.User;
import com.atroshonok.services.exceptions.ErrorAddingUserServiceException;
import com.atroshonok.services.exceptions.ErrorUpdatingUserServiceException;
import com.atroshonok.services.exceptions.LoginAlreadyExistServiceException;
import com.atroshonok.services.exceptions.ServiceException;

public interface IUserService {

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
     * Saves the given user. Throws
     * com.atroshonok.services.exceptions.LoginAlreadyExistServiceException if
     * an user with so login already exist. The method throws a
     * com.atroshonok.services.exceptions.ErrorAddingUserServiceException if
     * can't save the user.
     * 
     * @param user
     * @throws ErrorAddingUserServiceException
     * @throws LoginAlreadyExistServiceException
     */
    void saveUserToDataBase(User user) throws ErrorAddingUserServiceException, LoginAlreadyExistServiceException;

    /**
     * Returns the all users list. Returns an empty collection if has found no
     * one.
     * 
     * @return
     * @throws ServiceException
     */
    List<User> getAllUsers() throws ServiceException;

    /**
     * Returns an user using the given id. This method returns null if has found
     * no one.
     * 
     * @param userId
     * @return
     */
    User getUserById(Serializable userId);

    /**
     * Updates the given user. The method throws a
     * com.atroshonok.services.exceptions.ErrorUpdatingUserServiceException if
     * can't update the user.
     * 
     * @param user
     * @throws ErrorUpdatingUserServiceException
     */
    void updateUserData(User user) throws ErrorUpdatingUserServiceException;

}
