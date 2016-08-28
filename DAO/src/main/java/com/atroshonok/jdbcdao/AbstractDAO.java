package com.atroshonok.jdbcdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.atroshonok.jdbcdao.exception.ErrorAddingDAOException;
import com.atroshonok.jdbcdao.exception.ErrorUpdatingDAOException;
import com.atroshonok.tests.dao.entities.Entity;

public abstract class AbstractDAO<T extends Entity> implements IEntityDAO<T> {
	protected Logger log = Logger.getLogger(getClass());
	

	protected Connection connection;
	protected String deleteByIdEntitySQL;
	protected String selectEntitiesSQL; 
	protected String selectEntityByIdSQL;
	protected String addEntitySQL; 
	protected String updateEntitySQL; 

	/**
	 * @param connection
	 */
	public AbstractDAO(Connection connection) {
		super();
		this.connection = connection;
		this.initSQLQueries();
	}

	protected abstract void initSQLQueries();

	protected abstract T initEntity(ResultSet resultSet) throws SQLException;

	protected abstract PreparedStatement prepareInsertStatement(T entity, PreparedStatement statement)
			throws SQLException;

	protected abstract PreparedStatement prepareUpdateStatement(T entity, PreparedStatement statement)
			throws SQLException;

	public void closeResultSet(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			log.error("SQL exception closing statement: " + e);
		}
	}

	public void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			log.error("SQL exception closing statement: " + e);
		}
	}

	public T getById(long id) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		T entity = null;
		try {
			statement = connection.prepareStatement(selectEntityByIdSQL);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			resultSet.next();
			entity = initEntity(resultSet);

		} catch (SQLException e) {
			log.error("SQL exception getting by ID object of " + this.getClass() + " type: " + e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
		}
		return entity;
	}

	public List<T> getAll() {
		Statement statement = null;
		ResultSet resultSet = null;
		List<T> entities = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectEntitiesSQL);
			entities = initEntities(resultSet);

		} catch (SQLException e) {
			log.error("SQL exception getting objects list of " + this.getClass() + " type: " + e);
		} finally {
			closeResultSet(resultSet);
			closeStatement(statement);
		}
		return entities;
	}

	protected List<T> initEntities(ResultSet resultSet) throws SQLException {
		List<T> entities = new ArrayList<T>();
		while (resultSet.next()) {
			T entity = initEntity(resultSet);
			entities.add(entity);
		}
		return entities;
	}

	public void addNew(T entity) throws ErrorAddingDAOException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(addEntitySQL);
			statement = prepareInsertStatement(entity, statement);
			statement.executeUpdate();

		} catch (SQLException e) {
			log.error("SQL exception adding object type of: " + this.getClass() + " to database");
			throw new ErrorAddingDAOException(e);
		} finally {
			closeStatement(statement);
		}
	}


	public void update(T entity) throws ErrorUpdatingDAOException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(updateEntitySQL);
			statement = prepareUpdateStatement(entity, statement);
			statement.executeUpdate();

		} catch (SQLException e) {
			log.error("SQL exception updating object type of: " + this.getClass() + " in database");
			throw new ErrorUpdatingDAOException(e);
		} finally {
			closeStatement(statement);
		}
	}

	public void deleteById(long id) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(deleteByIdEntitySQL);
			statement.setLong(1, id);
			statement.executeUpdate();

		} catch (SQLException e) {
			log.error("SQL exception deleting object type of: " + this.getClass() + " from database: " + e);
		} finally {
			closeStatement(statement);
		}
	}

	public void delete(T entity) {
		deleteById(entity.getId());
	}


}
