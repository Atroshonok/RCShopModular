/**
 * 
 */
package com.atroshonok.jdbcdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.atroshonok.dao.entities.User;
import com.atroshonok.dao.entities.UserType;
import com.atroshonok.jdbcdao.exception.NoExistUserDAOException;

/**
 * @author Atroshonok Ivan
 *
 */
public class UserDAO extends AbstractDAO<User> {

	private static final String GET_USER_BY_LOGIN_PASS_SQL = "SELECT * FROM users WHERE login = ? AND password = ?";

	public UserDAO(Connection connection) {
		super(connection);
	}

	@Override
	protected void initSQLQueries() {
		deleteByIdEntitySQL = "DELETE FROM users WHERE ID = ? LIMIT 1;";
		selectEntitiesSQL = "SELECT * FROM users;";
		selectEntityByIdSQL = "SELECT * FROM users WHERE ID = ?;";
		addEntitySQL = "INSERT INTO users (registrDate, login, password, email, firstName, lastName, shippingAddress, age, userType, isInBlackList) VALUES (?,?,?,?,?,?,?,?,?,?);";
		updateEntitySQL = "UPDATE users SET registrDate = ?, login = ?, password = ?, email = ?, firstName = ?, "
				+ "lastName = ?, shippingAddress = ?, age = ?, userType = ?, isInBlackList = ? WHERE ID = ? LIMIT 1;";
	}
	
	public User getByLoginPassword(String login, String password) throws NoExistUserDAOException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			statement = connection.prepareStatement(GET_USER_BY_LOGIN_PASS_SQL);
			statement.setString(1, login);
			statement.setString(2, password);
			resultSet = statement.executeQuery();
			resultSet.next();
			user = initEntity(resultSet);

		} catch (SQLException e) {
			log.error("SQL exception getting users by login: " + e);
			throw new NoExistUserDAOException(e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
		}
		return user;
	}

	@Override
	protected User initEntity(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getLong(1));
		user.setRegistrDate(resultSet.getDate(2));
		user.setLogin(resultSet.getString(3));
		user.setPassword(resultSet.getString(4));
		user.setEmail(resultSet.getString(5));
		user.setFirstname(resultSet.getString(6));
		user.setLastname(resultSet.getString(7));
		user.setShippingAddress(resultSet.getString(8));
		user.setAge(resultSet.getInt(9));
		user.setRole(UserType.valueOf(resultSet.getString(10).toUpperCase().trim()));
		user.setInBlackList(Boolean.parseBoolean(resultSet.getString(11).trim()));
		return user;
	}

	// INSERT INTO users (registrDate, login, password, email, firstName,
	// lastName, shippingAddress, age, userType, isInBlackList) VALUES
	// (?,?,?,?,?,?,?,?,?,?);
	@Override
	protected PreparedStatement prepareInsertStatement(User user, PreparedStatement statement) throws SQLException {
		statement.setDate(1, user.getRegistrDate());
		statement.setString(2, user.getLogin());
		statement.setString(3, user.getPassword());
		statement.setString(4, user.getEmail());
		statement.setString(5, user.getFirstname());
		statement.setString(6, user.getLastname());
		statement.setString(7, user.getShippingAddress());
		statement.setInt(8, user.getAge());
		statement.setString(9, user.getRole().toString());
		statement.setString(10, String.valueOf(user.isInBlackList()));
		return statement;
	}

	// UPDATE users SET registrDate = ?, login = ?, password = ?, email = ?,
	// firstName = ?, lastName = ?, shippingAddress = ?, age = ?, userType = ?,
	// isInBlackList = ? WHERE ID = ? LIMIT 1;
	@Override
	protected PreparedStatement prepareUpdateStatement(User user, PreparedStatement statement) throws SQLException {
		prepareInsertStatement(user, statement);
		statement.setLong(11, user.getId());
		return statement;
	}


}
