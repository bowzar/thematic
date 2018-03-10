package com.yulintu.thematic.example.boot;

import com.yulintu.thematic.data.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
@Import({ConnectionAdvices.class})
@PropertySource("application.properties")
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
