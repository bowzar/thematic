package com.yulintu.thematic.example.annotation.test;

import com.yulintu.thematic.example.annotation.TransactionAspect;
import com.yulintu.thematic.example.annotation.TransactionAspect2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
//@Conditional(UserCondition.class)
public class UserRepository {

    @Value("${name}")
    private String name;


    @Override
    public String toString() {
        return String.format("%s : %s", this.getClass().getName(), name);
    }

    @TransactionAspect2
    @TransactionAspect
    public int add(int n) {
        return n;
    }
}
