package com.yulintu.thematic.data.sharding;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ShardKey {

    String[] value() default "";
}
