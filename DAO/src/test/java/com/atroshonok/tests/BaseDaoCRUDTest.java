/**
 * 
 */
package com.atroshonok.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.atroshonok.dao.Dao;
import com.atroshonok.dao.dbutils.HibernateUtil;
import com.atroshonok.dao.entities.Entity;
import com.atroshonok.dao.exceptions.DaoException;

/**
 * @author Ivan Atroshonok
 *
 */
public abstract class BaseDaoCRUDTest<T extends Entity> {
    protected static Logger log = Logger.getLogger(BaseDaoCRUDTest.class);
    protected static HibernateUtil util = HibernateUtil.getInstance();
    protected T entity = null;
    protected Dao<T> baseDao = getEntityDao();
    protected Transaction transaction;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * Returns a DAO object of needed class.
     */
    abstract protected Dao<T> getEntityDao();

    /**
     * Creates a new entity and initialize all its fields. First not abstract
     * class that extends BaseDaoCRUDTest<T> class must override this method.
     */
    abstract protected T createAndInitEntity();

    /**
     * Changes the entity fields. First not abstract class that extends
     * BaseDaoCRUDTest<T> class must override this method.
     * @param entity 
     */
    abstract protected void changeEntity(T entity);

//    protected static void dropEntityTable() {
//	SQLQuery sqlQuery = util.getSession().createSQLQuery("DROP TABLE" + getEntityTableName() + ";");
//	sqlQuery.executeUpdate();
//    }

//    /**
//     * @throws java.lang.Exception
//     */
//    @AfterClass
//    public static void tearDownAfterClass() throws Exception {
//	dropEntityTable();
//	util.getSession().close();
//    }

    @Before
    public void setUp() {
	entity = createAndInitEntity();
	System.out.println("entity!!!! " + entity);
	Long entityId = saveEntityToDB(entity);
	entity.setId(entityId);
	util.getSession().clear();
    }

    private Long saveEntityToDB(T entity) {
	Serializable entityId = null;
	try {
	    transaction = util.getSession().beginTransaction();
	    entityId = baseDao.save(entity);
	    transaction.commit();
	} catch (DaoException e) {
	    log.error("Error saving entity: " + entity, e);
	    transaction.rollback();
	}
	return Long.parseLong(entityId.toString());// Нужна ли проверка на null
    }

    /**
     * Test method for
     * {@link com.atroshonok.dao.BaseDao#saveOrUpdate(java.lang.Object)}.
     */
    @Test
    public void testSaveOrUpdate() {
	changeEntity(entity);
	saveOrUpdateEntity();
	util.getSession().clear();
	T loadedEntity = getEntity(entity.getId());
	compareEntityWithLoadedEntity(loadedEntity);
    }

    private void saveOrUpdateEntity() {
	try {
	    transaction = util.getSession().beginTransaction();
	    baseDao.saveOrUpdate(entity);
	    transaction.commit();
	} catch (DaoException e) {
	    log.error("Error updating entity: " + entity, e);
	    transaction.rollback();
	}
    }

    @Test
    public void testGet() {
	Entity gotEntity = getEntity(entity.getId());
	compareEntityWithLoadedEntity(gotEntity);
    }

    private T getEntity(Long entityId) {
	T tempEntity = null;
	try {
	    transaction = util.getSession().beginTransaction();
	    tempEntity = baseDao.get(entityId);
	    log.info("Got Entity: " + tempEntity);
	    transaction.commit();
	} catch (DaoException e) {
	    log.error("Error getting entity in class: " + this.getClass(), e);
	    transaction.rollback();
	}
	return tempEntity;
    }

    /**
     * Test method for
     * {@link com.atroshonok.dao.BaseDao#load(java.io.Serializable)}.
     */
    @Test
    public void testLoad() {
	T loadedEntity = loadEntity(entity.getId());
	compareEntityWithLoadedEntity(loadedEntity);
    }

    private T loadEntity(Long entityId) {
	T loadedEntity = null;
	try {
	    transaction = util.getSession().beginTransaction();
	    loadedEntity = baseDao.load(entityId);
	    transaction.commit();
	} catch (DaoException e) {
	    log.error("Error loading entity in class: " + this.getClass(), e);
	    transaction.rollback();
	}
	return loadedEntity;
    }

    private void compareEntityWithLoadedEntity(Entity loadedEntity) {
	if (loadedEntity != null) {
	    assertEquals(entity, loadedEntity);
	} else {
	    fail("test method does't work right in class: " + this.getClass());
	}
    }

    /**
     * Test method for
     * {@link com.atroshonok.dao.BaseDao#delete(java.lang.Object)}.
     */
    @Test
    public void testDelete() {
	util.getSession().saveOrUpdate(entity);
	deleteEntity();
	T gotEntity = getEntity(entity.getId());
	assertNull(gotEntity);
    }

    private void deleteEntity() {
	try {
	    transaction = util.getSession().beginTransaction();
	    baseDao.delete(entity);
	    transaction.commit();
	} catch (DaoException e) {
	    log.error("Error deleting entity in class: " + this.getClass(), e);
	    transaction.rollback();
	}
    }

}
