package com.yulintu.thematic.data.hibernate;

import com.yulintu.thematic.data.ProviderDbImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class ProviderHibernateImpl extends ProviderDbImpl implements ProviderHibernate {

    //region fields
    private SessionFactory factory;
    private Session currentSession;
    //endregion

    //region ctor
    public ProviderHibernateImpl(String connectionString) {
        super(connectionString);
    }
    //endregion

    //region methods
    @Override
    protected void initialize(String connectionString) {
        factory = HibernateSessionFactoryPool.initialize(connectionString);
        setType(HibernateSessionFactoryPool.getDriverClass(connectionString));
    }

    @Override
    protected boolean onOpenConnection() {

        currentSession = factory.openSession();
        return true;
    }

    @Override
    protected boolean onCloseConnection() {
        currentSession.close();
        currentSession = null;
        return true;
    }

    @Override
    protected boolean onBeginTransaction() {
        currentSession.beginTransaction();
        return true;
    }

    @Override
    protected boolean onCommitTransaction() {
        currentSession.getTransaction().commit();
        return true;
    }

    @Override
    protected boolean onRollbackTransaction() {
        currentSession.getTransaction().rollback();
        return true;
    }

    @Override
    public List query(HibernateSessionQuery callback) {

        return callback.query(currentSession);
    }

    @Override
    public Object execute(HibernateSessionExecute callback) {
        return callback.execute(currentSession);
    }

    @Override
    public Object executeInSession(HibernateSessionExecute callback) {

        Session session = null;
        try {
            session = factory.openSession();
            return callback.execute(session);

        } finally {
            session.close();
        }
    }

    @Override
    public Object executeInTransaction(HibernateSessionExecute callback) {

        Session session = null;
        try {
            session = factory.openSession();

            session.beginTransaction();
            Object val = callback.execute(session);
            session.getTransaction().commit();

            return val;

        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;

        } finally {
            session.close();
        }
    }
    //endregion
}
