package com.yulintu.thematic.data.hibernate;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.SQLQueryFactory;
import com.yulintu.thematic.AssertUtils;
import com.yulintu.thematic.data.ProviderDbImpl;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Connection;

public class ProviderPersistenceImpl extends ProviderDbImpl implements ProviderPersistence {

    //region fields
    private DataSource dataSource = null;
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    //endregion

    //region ctor
    public ProviderPersistenceImpl(String connectionString) {
        super(connectionString);
    }

    public ProviderPersistenceImpl(String connectionString, DataSource dataSource) {
        super(null);
        setConnectionString(connectionString);
        this.dataSource = dataSource;

        initialize(connectionString);
    }

    @Override
    protected void finalize() {
        dataSource = null;
        entityManager = null;
        factory = null;
    }

    //endregion

    //region methods
    @Override
    protected void initialize(String connectionString) {

        if (dataSource == null) {
            factory = EntityManagerFactoryPool.initialize(connectionString);
            setType(EntityManagerFactoryPool.getProviderType(connectionString));

        } else {
            factory = EntityManagerFactoryPool.initialize(connectionString, dataSource);
            setType(EntityManagerFactoryPool.getProviderType(dataSource));
        }
    }

    @Override
    protected boolean onOpenConnection() {

        entityManager = factory.createEntityManager();
        return true;
    }

    @Override
    protected boolean onCloseConnection() {
        entityManager.close();
        entityManager = null;
        return true;
    }

    @Override
    protected boolean onBeginTransaction() {
        AssertUtils.notNull(entityManager, "entityManager");
        entityManager.getTransaction().begin();
        return true;
    }

    @Override
    protected boolean onCommitTransaction() {
        AssertUtils.notNull(entityManager, "entityManager");
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    protected boolean onRollbackTransaction() {
        AssertUtils.notNull(entityManager, "entityManager");
        entityManager.getTransaction().rollback();
        return true;
    }

    @Override
    public EntityManager getCurrentEntityManager() {
        AssertUtils.notNull(entityManager, "entityManager");
        return entityManager;
    }

    @Override
    public Session getCurrentSession() {
        AssertUtils.notNull(entityManager, "entityManager");
        return entityManager.unwrap(Session.class);
    }

    @Override
    public JPAQueryFactory getCurrentJPAQueryFactory() {
        return new JPAQueryFactory(this::getCurrentEntityManager);
    }

    @Override
    public SQLQueryFactory getCurrentSQLQueryFactory(Configuration config) {

        final Connection[] cnn = {null};
        getCurrentSession().doWork(connection -> cnn[0] = connection);
        return new SQLQueryFactory(config, () -> cnn[0]);
    }

    @Override
    public Object jpaInSession(EntityManagerExecute callback) {

        EntityManager em = null;
        try {
            em = factory.createEntityManager();
            return callback.execute(em);

        } finally {
            em.close();
        }
    }

    @Override
    public Object jpaInTransaction(EntityManagerExecute callback) {

        EntityManager em = null;
        try {
            em = factory.createEntityManager();

            em.getTransaction().begin();
            Object val = callback.execute(em);
            em.getTransaction().commit();

            return val;

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;

        } finally {
            em.close();
        }
    }

    @Override
    public Object dslInSession(JPAQueryDSLExecute callback) {

        EntityManager em = null;
        try {
            em = factory.createEntityManager();
            return callback.execute(new JPAQueryFactory(em));

        } finally {
            em.close();
        }
    }

    @Override
    public Object dslInTransaction(JPAQueryDSLExecute callback) {

        EntityManager em = null;
        try {
            em = factory.createEntityManager();

            em.getTransaction().begin();
            Object val = callback.execute(new JPAQueryFactory(em));
            em.getTransaction().commit();

            return val;

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;

        } finally {
            em.close();
        }
    }

    @Override
    public Object dslInSession(Configuration config, SQLQueryDSLExecute callback) {

        EntityManager em = null;
        try {
            em = factory.createEntityManager();

            final Connection[] cnn = {null};
            em.unwrap(Session.class).doWork(connection -> cnn[0] = connection);

            return callback.execute(new SQLQueryFactory(config, () -> cnn[0]));

        } finally {
            em.close();
        }
    }

    @Override
    public Object dslInTransaction(Configuration config, SQLQueryDSLExecute callback) {

        EntityManager em = null;
        try {
            em = factory.createEntityManager();

            em.getTransaction().begin();

            final Connection[] cnn = {null};
            em.unwrap(Session.class).doWork(connection -> cnn[0] = connection);
            Object val = callback.execute(new SQLQueryFactory(config, () -> cnn[0]));

            em.getTransaction().commit();

            return val;

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;

        } finally {
            em.close();
        }
    }

    @Override
    public Object allInSession(Configuration config, PersistenceExecute callback) {

        EntityManager em = null;
        try {
            em = factory.createEntityManager();

            final Connection[] cnn = {null};
            em.unwrap(Session.class).doWork(connection -> cnn[0] = connection);

            return callback.execute(em, new JPAQueryFactory(em),
                    config == null ? null : new SQLQueryFactory(config, () -> cnn[0]));

        } finally {
            em.close();
        }
    }

    @Override
    public Object allInTransaction(Configuration config, PersistenceExecute callback) {

        EntityManager em = null;
        try {
            em = factory.createEntityManager();

            em.getTransaction().begin();

            final Connection[] cnn = {null};
            em.unwrap(Session.class).doWork(connection -> cnn[0] = connection);
            Object val = callback.execute(em, new JPAQueryFactory(em),
                    config == null ? null : new SQLQueryFactory(config, () -> cnn[0]));

            em.getTransaction().commit();

            return val;

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;

        } finally {
            em.close();
        }
    }
    //endregion
}
