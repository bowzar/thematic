package com.yulintu.thematic.data.provider;


import com.yulintu.thematic.data.HibernateConnectionStringBuilder;
import com.yulintu.thematic.data.ProviderHibernate;
import com.yulintu.thematic.data.ProviderHibernateImpl;
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
