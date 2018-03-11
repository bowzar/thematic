package com.yulintu.thematic.example.provider;


import com.yulintu.thematic.data.hibernate.HibernateConnectionStringBuilder;
import com.yulintu.thematic.data.hibernate.ProviderHibernate;
import com.yulintu.thematic.data.hibernate.ProviderHibernateImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfiguration {

    @Bean
    @Scope("prototype")
    public ProviderHibernate provider() {

        HibernateConnectionStringBuilder builder = new HibernateConnectionStringBuilder();
        builder.setConfigureFilePath("hibernate.sqlserver.cfg.xml");

        return new ProviderHibernateImpl(builder.getConnectionString());
    }
}
