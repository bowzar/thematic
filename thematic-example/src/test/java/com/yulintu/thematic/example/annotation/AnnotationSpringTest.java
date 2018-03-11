package com.yulintu.thematic.example.annotation;

import com.yulintu.thematic.example.annotation.test.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BeanConfiguration.class)
@ActiveProfiles("dev")
public class AnnotationSpringTest {

    @Resource
    private UserRepository userRepository;

    @Resource
    private Environment env;

    @Value("${name:haha}")
    private String name;

    @Test
    public void printEnv() {
        System.out.println(env.getProperty("name"));
    }

    @Test
    public void testGetBean() {

//        System.setProperty("test", "true");
        System.setProperty("spring.profiles.active", "dev");

        System.out.println(name);

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        UserRepository ur0 = ac.getBean(UserRepository.class);
        UserRepository ur1 = ac.getBean(UserRepository.class);

        System.out.println(userRepository == ur1);
        System.out.println(ur0 == ur1);
        System.out.println(ur1);

        System.out.println(ur0.add(4));
        System.out.println(userRepository.toString());
        System.out.println(ur0.add(1));
    }
}
