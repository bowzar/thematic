package com.yulintu.thematic.data.sharding.test.configurations;

import com.yulintu.thematic.data.FileConnectionStringBuilder;
import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.data.sharding.*;
import org.springframework.context.annotation.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.yulintu.thematic.data.sharding.test")
@Import({
        ServiceAdvicesDbSourceSharding.class,
        ShardReducerFirst.class,
        ShardReducerFirstNotNull.class,
        ShardReducerCombine.class,
        ShardReducerSum.class,
        ShardReducerMax.class,
        ShardReducerMin.class,})
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class TestServiceConfiguration {

    @Bean
    @Scope("prototype")
    public Provider defaultProvider() {
        FileConnectionStringBuilder builder = new FileConnectionStringBuilder();
        builder.setConfigureFilePath("spring.datasource.shards.xml");
        return new ProviderShardingImpl(builder.getConnectionString());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ShardingTransactionManager();
    }
}
