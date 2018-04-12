package com.yulintu.thematic.data.hibernate;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.SQLQueryFactory;
import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.data.Repository;
import com.yulintu.thematic.data.RepositoryImpl;
import org.hibernate.Session;

import javax.persistence.EntityManager;

public class RepositoryPersistenceImpl extends RepositoryImpl implements Repository {

    //region ctor
    public RepositoryPersistenceImpl(Provider provider) {
        super(provider);
    }
    //endregion

    //region methods
    public ProviderPersistence getPersistenceProvider() {
        return (ProviderPersistence) getProvider();
    }

    protected EntityManager getEntityManager(){
        return getPersistenceProvider().getCurrentEntityManager();
    }

    protected Session getSession(){
        return getPersistenceProvider().getCurrentSession();
    }

    protected JPAQueryFactory getJPAQueryFactory(){
        return getPersistenceProvider().getCurrentJPAQueryFactory();
    }

    protected SQLQueryFactory getSQLQueryFactory(Configuration config){
        return getPersistenceProvider().getCurrentSQLQueryFactory(config);
    }
    //endregion
}
