package com.yulintu.thematic.example.repository;

import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.data.Repository;
import com.yulintu.thematic.data.RepositoryFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BeanConfiguration.class)
public class TestRepository {

    @Resource(name = "provider")
    private Provider provider;

    @Resource(name = "providerSQLServer")
    private Provider providerSQLServer;

    @Resource(name = "providerPostgreSQL")
    private Provider providerPostgreSQL;

    @Resource(name = "providerOracle")
    private Provider providerOracle;

    @Test
    public void testCommonQuery() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        Provider provider = ac.getBean(Provider.class);
        Repository repository = ac.getBean(Repository.class, provider);
    }

    @Test
    public void testType() {

        System.out.println(new RepositoryFactory(provider).create(RepositoryEmployee.class));
//        System.out.println(new RepositoryFactory(providerOracle).create(RepositoryEmployee.class));
//        System.out.println(new RepositoryFactory(providerSQLServer).create(RepositoryEmployee.class));
//        System.out.println(new RepositoryFactory(providerPostgreSQL).create(RepositoryEmployee.class));
    }
}
