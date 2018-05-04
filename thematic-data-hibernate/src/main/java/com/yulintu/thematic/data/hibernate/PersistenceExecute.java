package com.yulintu.thematic.data.hibernate;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.SQLQueryFactory;

import javax.persistence.EntityManager;

public interface PersistenceExecute {

    Object execute(EntityManager session, JPAQueryFactory jpaQueryFactory, SQLQueryFactory sqlQueryFactoryF);
}
