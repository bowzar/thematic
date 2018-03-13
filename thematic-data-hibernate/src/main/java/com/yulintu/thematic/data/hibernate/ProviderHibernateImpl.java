package com.yulintu.thematic.data.hibernate;

import com.yulintu.thematic.AssertUtils;
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

    @Override
    protected void finalize() throws Throwable {
        currentSession = null;
        factory = null;
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
        AssertUtils.notNull(currentSession, "currentSession");
        currentSession.beginTransaction();
        return true;
    }

    @Override
    protected boolean onCommitTransaction() {
        AssertUtils.notNull(currentSession, "currentSession");
        currentSession.getTransaction().commit();
        return true;
    }

    @Override
    protected boolean onRollbackTransaction() {
        AssertUtils.notNull(currentSession, "currentSession");
        currentSession.getTransaction().rollback();
        return true;
    }

    @Override
    public List query(HibernateSessionQuery callback) {
        AssertUtils.notNull(currentSession, "currentSession");
        return callback.query(currentSession);
    }

    @Override
    public Object execute(HibernateSessionExecute callback) {
        AssertUtils.notNull(currentSession, "currentSession");
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
