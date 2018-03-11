package com.yulintu.thematic.example.annotation.test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class UserAspect {

    @Around("@annotation(com.yulintu.thematic.TransactionAspect2)")
    public Object aroundTransactionAspect2(ProceedingJoinPoint pjp) {

        System.out.println("Around TransactionAspect2!");
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

    @Before("@annotation(com.yulintu.thematic.TransactionAspect)")
    public void beforeTransactionAspect() {
        System.out.println("Before TransactionAspect!");
    }

    @After("@annotation(com.yulintu.thematic.TransactionAspect)")
    public void afterTransactionAspect() {
        System.out.println("After TransactionAspect!");
    }

    @Around("@annotation(com.yulintu.thematic.TransactionAspect)")
    public Object aroundTransactionAspect(ProceedingJoinPoint pjp) {

        System.out.println("Around TransactionAspect!");
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

}
