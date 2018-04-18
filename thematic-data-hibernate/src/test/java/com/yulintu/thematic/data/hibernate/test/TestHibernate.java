package com.yulintu.thematic.data.hibernate.test;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.dml.SQLUpdateClause;
import com.querydsl.sql.spatial.PostGISTemplates;
import com.yulintu.thematic.data.querydsl.EntityContext;
import com.yulintu.thematic.data.querydsl.QueryDSLUtils;
import com.yulintu.thematic.data.hibernate.EntityManagerFactoryPool;
import com.yulintu.thematic.data.hibernate.HibernateConnectionStringBuilder;
import com.yulintu.thematic.data.hibernate.ProviderPersistenceImpl;
import com.yulintu.thematic.data.hibernate.test.entities.*;
import com.yulintu.thematic.data.hibernate.test.sentities.QSJZD;
import com.yulintu.thematic.data.hibernate.test.sentities.QXZQH_XZDY;
import com.yulintu.thematic.data.hibernate.test.sentities.sMzdw;
import com.yulintu.thematic.spatial.GeometryUtils;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TestHibernate {

    @Test
    public void testBuilder() {
        HibernateConnectionStringBuilder builder = new HibernateConnectionStringBuilder();
        builder.setDialect("org.hibernate.spatial.dialect.postgis.PostgisPG95Dialect");
        builder.setDriverClass("org.postgresql.Driver");
        builder.setUrl("jdbc:postgresql://192.168.20.10:5432/data_burg");
        builder.setUsername("sde");
        builder.setPassword("123456");
        builder.setConfigureFilePath("");
        builder.setMappingClasses(new Class[]{MZDW.class, SJZD.class});

        String connectionString = builder.getConnectionString();
        EntityManagerFactory factory = EntityManagerFactoryPool.initialize(connectionString);

    }

    @Test
    public void testCreation() {
        HibernateConnectionStringBuilder builder = new HibernateConnectionStringBuilder();
        builder.setConfigureFilePath("hibernate.oracle.cfg.xml");
        ProviderPersistenceImpl provider = new ProviderPersistenceImpl(builder.getConnectionString());

        Object o = provider.jpaInSession(em -> {

            return em.createNativeQuery("select * from xzqh_xzdy where shape is not null and ROWNUM < 10", XZQH_XZDY.class)
                    .getResultList();
        });

    }

    @Test
    public void testQueryDSL() {

        HibernateConnectionStringBuilder builder = new HibernateConnectionStringBuilder();
        builder.setConfigureFilePath("hibernate.oracle.cfg.xml");
        ProviderPersistenceImpl provider1 = new ProviderPersistenceImpl(builder.getConnectionString());
        builder.setConfigureFilePath("hibernate.postgresql.cfg.xml");
        ProviderPersistenceImpl provider2 = new ProviderPersistenceImpl(builder.getConnectionString());

        Object o = provider1.dslInSession(factory -> {
            return factory
                    .from(QSJZD.sJZD)
                    .where(QSJZD.sJZD.bm.contains("3"))
                    .select(QSJZD.sJZD.bm)
                    .fetch();
        });

        XZQH_XZDY o1 = (XZQH_XZDY) provider1.dslInSession(factory -> {
            return factory.selectFrom(QXZQH_XZDY.xZQH_XZDY).fetchFirst();
        });

        Mzdw o11 = (Mzdw) provider2.dslInSession(factory -> {
            return factory.selectFrom(sMzdw.mzdw).fetchFirst();
        });

        Object o2 = provider2.allInTransaction(new Configuration(PostGISTemplates.builder().build()), (em, jpa, sql) -> {

            Mzdw mzdw = new Mzdw();
            mzdw.setShape(GeometryUtils.toLatteGeometry(o1.getShape()));
            mzdw.setMj(5.44);
            mzdw.setDwmc("HIDE");

            em.persist(mzdw);

            em.flush();
            em.clear();

            List<Mzdw> fetch = jpa.selectFrom(sMzdw.mzdw).fetch();

            SQLUpdateClause update = sql.update(sMzdw.mzdw);

            update.where(sMzdw.mzdw.dwmc.eq("HIDE"))
                    .set(sMzdw.mzdw.mj, 6.66)
                    .addBatch();

            update.where(sMzdw.mzdw.dwmc.eq("HIDE"))
                    .set(sMzdw.mzdw.mj, 7.77)
                    .addBatch();

            System.out.println(update.execute());

//            return result;

            Mzdw mzdw2 = new Mzdw();
            mzdw.setShape(o11.getShape());
            mzdw.setMj(8.88);
            mzdw.setDwmc("haha");

            QueryDSLUtils.update(
                    jpa.update(sMzdw.mzdw).
                            where(sMzdw.mzdw.dwmc.eq("HIDE")),
                    sMzdw.mzdw, mzdw2)
                    .execute();

            return 1;
        });

    }

    @Test
    public void testQueryDSLUtils() {

        Expression[] expressions = QueryDSLUtils.asExpressionArray(
                QXZQH_XZDY.xZQH_XZDY.bm,
                QXZQH_XZDY.xZQH_XZDY.mc,
                QXZQH_XZDY.xZQH_XZDY.shape);

        Expression[] attributes = QueryDSLUtils.selectAttributes(QXZQH_XZDY.xZQH_XZDY);
        Expression[] attributes1 = QueryDSLUtils.selectAttributes(sMzdw.mzdw);
        Expression[] expressions2 = QueryDSLUtils.selectExcept(QXZQH_XZDY.xZQH_XZDY, QXZQH_XZDY.xZQH_XZDY.shape);

        HibernateConnectionStringBuilder builder = new HibernateConnectionStringBuilder();
        builder.setConfigureFilePath("hibernate.oracle.cfg.xml");
        ProviderPersistenceImpl provider1 = new ProviderPersistenceImpl(builder.getConnectionString());

        Expression[] expressions1 = QueryDSLUtils.selectAttributes(QSJZD.sJZD);
        Object o = provider1.dslInSession(factory -> {
            return factory
                    .from(QSJZD.sJZD)
                    .where(QSJZD.sJZD.bm.contains("3"))
                    .select(expressions1)
                    .fetchFirst();
        });

        SJZD sjzd = QueryDSLUtils.entityFrom((Tuple) o, expressions1, SJZD.class);

        Object o1 = provider1.dslInSession(factory -> {
            return factory
                    .from(QSJZD.sJZD)
                    .where(QSJZD.sJZD.bm.contains("3"))
                    .select(expressions1)
                    .fetch();
        });

        List<SJZD> sjzds = QueryDSLUtils.entitiesFrom((List<Tuple>) o1, expressions1, SJZD.class);
    }

    @Test
    public void testScanPackage() {
        EntityContext.installAll("com.yulintu.thematic.data");
    }
}
