package com.yulintu.thematic.data;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAspectJAutoProxy
@Import({
        RepositoryAdvicesConnection.class,
        RepositoryAdvicesTransaction.class})
public class SpringConfiguration {
}
