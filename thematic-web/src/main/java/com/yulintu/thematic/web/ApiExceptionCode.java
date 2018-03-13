package com.yulintu.thematic.web;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ApiExceptionCode {

    long value() default 0L;

    int status() default 500;
}
