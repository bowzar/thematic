package com.yulintu.thematic.example.repository;


import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.data.hibernate.HibernateConnectionStringBuilder;
import com.yulintu.thematic.data.hibernate.ProviderHibernateImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configuration
@PropertySource("test.properties")
public class BeanConfiguration {

    @Value("${thematic.datasource.name:hibernate.cfg.xml}")
    private String dsConfigName;

    @Bean
    @Scope("prototype")
    public Provider provider() {

        HibernateConnectionStringBuilder builder = new HibernateConnectionStringBuilder();
        builder.setConfigureFilePath(dsConfigName);

        return new ProviderHibernateImpl(builder.getConnectionString());
    }

    @Bean
    @Scope("prototype")
    public Provider providerSQLServer() {

        HibernateConnectionStringBuilder builder = new HibernateConnectionStringBuilder();
        builder.setConfigureFilePath("hibernate.sqlserver.cfg.xml");

        return new ProviderHibernateImpl(builder.getConnectionString());
    }

    @Bean
    @Scope("prototype")
    public Provider providerOracle() {

        HibernateConnectionStringBuilder builder = new HibernateConnectionStringBuilder();
        builder.setConfigureFilePath("hibernate.oracle.cfg.xml");

        return new ProviderHibernateImpl(builder.getConnectionString());
    }

    @Bean
    @Scope("prototype")
    public Provider providerPostgreSQL() {

        HibernateConnectionStringBuilder builder = new HibernateConnectionStringBuilder();
        builder.setConfigureFilePath("hibernate.postgresql.cfg.xml");

        return new ProviderHibernateImpl(builder.getConnectionString());
    }
}
