package com.yulintu.thematic.example.boot;

import com.yulintu.thematic.data.*;
import com.yulintu.thematic.data.hibernate.HibernateConnectionStringBuilder;
import com.yulintu.thematic.data.hibernate.ProviderHibernateImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
@Import({ServiceCacheAdvices.class,ServiceConnectionAdvices.class,ServiceTransactionAdvices.class, })
@ComponentScan({"com.yulintu.thematic.example.employee"})
public class BeanConfiguration extends SpringConfiguration {

    @Value("${thematic.datasource.name}")
    private String dsConfigName;

    @Bean
    @Scope("prototype")
    public Provider provider() {

        HibernateConnectionStringBuilder builder = new HibernateConnectionStringBuilder();
        builder.setConfigureFilePath(dsConfigName);
        return new ProviderHibernateImpl(builder.getConnectionString());
    }
}
