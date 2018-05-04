package com.yulintu.thematic.data.hibernate.test.configurations;

import com.yulintu.thematic.data.BasicTransactionManager;
import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.data.ServiceAdvicesDbSource;
import com.yulintu.thematic.data.hibernate.HibernateConnectionStringBuilder;
import com.yulintu.thematic.data.hibernate.ProviderPersistenceImpl;
import org.springframework.context.annotation.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.yulintu.thematic.data.hibernate.test")
@Import({ServiceAdvicesDbSource.class})
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class TestServiceConfiguration {

    @Bean
    @Scope("prototype")
    public Provider defaultProvider() {
        HibernateConnectionStringBuilder builder = new HibernateConnectionStringBuilder();
        builder.setConfigureFilePath("spring.datasource.oracle.xml");
        builder.setConfigureFileType("spring");
        return new ProviderPersistenceImpl(builder.getConnectionString());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new BasicTransactionManager();
    }
}
