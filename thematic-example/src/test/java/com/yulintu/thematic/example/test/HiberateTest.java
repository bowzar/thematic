package com.yulintu.thematic.example.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class HiberateTest {

    private static final SessionFactory sessionFactory = createSessionFactory();

    private static SessionFactory createSessionFactory() {
        Configuration configure = new Configuration().configure();

//        configure.getProperties().setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServer2008Dialect");
//        configure.getProperties().setProperty("hibernate.connection.driver_class", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        configure.getProperties().setProperty("hibernate.connection.url", "jdbc:sqlserver://localhost:1433;databaseName=Authorization");
//        configure.getProperties().setProperty("hibernate.connection.username", "sa");
//        configure.getProperties().setProperty("hibernate.connection.password", "123456");

        return configure.buildSessionFactory();
    }

    private static void executeInTransaction(SessionExecutable se) {

        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            se.execute(session);

            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;

        } finally {
            session.close();
        }
    }

    private static void execute(SessionExecutable se) {

        Session session = null;

        try {
            session = sessionFactory.openSession();

            se.execute(session);

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }

    @Test
    public void testManual() {

        execute(session -> {

            List list0 = session.createSQLQuery(
                    "select obj.name UserName, gp.name GroupName from [dbo].[SecurityObject] obj join [dbo].[SecurityGroup] gp on obj.[Group] = gp.id").
                    setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            List list1 = session.createSQLQuery(
                    "select * from SecurityObject").
                    addEntity("SecurityObject", User.class).setFirstResult(1).setMaxResults(3).list();
            List list2 = session.createSQLQuery(
                    "select obj.*, gp.name GroupName from [dbo].[SecurityObject] obj join [dbo].[SecurityGroup] gp on obj.[Group] = gp.id").
                    addEntity(UserGroup.class).list();
        });
    }

    @Test
    public void testAdd() {

        executeInTransaction(session -> {

            for (int i = 0; i < 1000; i++) {

                User user = new User();
                user.setName("HIDE");
                session.save(user);

                if (i % 100 == 0) {
                    session.flush();
                    session.clear();
                }
            }
        });
    }

    @Test
    public void testUpdate() {

        executeInTransaction(session -> {

            int i = session
                    .createSQLQuery("update  SecurityObject set properties = :p where name = :name ")
                    .setParameter("name", "HIDE")
                    .setParameter("p", "hahahah")
                    .executeUpdate();

            session.doWork(new Work() {
                @Override
                public void execute(Connection connection) throws SQLException {
                    PreparedStatement s = connection.prepareStatement("");
                }
            });

            System.out.println(i);
        });
    }

    @Test
    public void testDelete() {

        executeInTransaction(session -> {

            int i = session
                    .createSQLQuery("delete from SecurityObject where name = :name ")
                    .setParameter("name", "HIDE")
                    .executeUpdate();

            System.out.println(i);
        });
    }
}
