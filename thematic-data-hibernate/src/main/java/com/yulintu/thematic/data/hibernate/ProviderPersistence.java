package com.yulintu.thematic.data.hibernate;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.SQLQueryFactory;
import com.yulintu.thematic.data.ProviderDb;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.util.List;

public interface ProviderPersistence extends ProviderDb {

    EntityManager getCurrentEntityManager();
    Session getCurrentSession();
    JPAQueryFactory getCurrentJPAQueryFactory();
    SQLQueryFactory getCurrentSQLQueryFactory(Configuration config);

//    List query(PersistenceQuery callback);

//    Object execute(PersistenceExecute callback);

    Object jpaInSession(EntityManagerExecute callback);

    Object jpaInTransaction(EntityManagerExecute callback);


    Object dslInSession(JPAQueryDSLExecute callback);

    Object dslInTransaction(JPAQueryDSLExecute callback);


    Object dslInSession(Configuration config, SQLQueryDSLExecute callback);

    Object dslInTransaction(Configuration config, SQLQueryDSLExecute callback);


    Object allInSession(Configuration config, PersistenceExecute callback);

    Object allInTransaction(Configuration config, PersistenceExecute callback);
}
