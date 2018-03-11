package com.yulintu.thematic.example.annotation.test;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class UserCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {

        Environment env = conditionContext.getEnvironment();
        for (String a : env.getActiveProfiles()) {
            System.out.println("Active profile := " + a);
        }

        String val = env.getProperty("test");
        boolean b = Boolean.valueOf(val);
        System.out.println("test := " + b);

        return true;
    }
}
