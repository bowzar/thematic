package com.yulintu.thematic.example.annotation;

import com.yulintu.thematic.example.annotation.test.UserAspect;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.yulintu.thematic.data.annotation.test")
@Profile("dev")
@PropertySource("test.properties")
@EnableAspectJAutoProxy
@Import({UserAspect.class})
public class BeanConfiguration {
}
