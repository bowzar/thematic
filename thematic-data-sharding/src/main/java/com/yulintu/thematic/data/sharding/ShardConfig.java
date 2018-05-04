package com.yulintu.thematic.data.sharding;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface ShardConfig {

    String value() default "";

//    ShardReduceType reduceType() default ShardReduceType.COMBINE;
}
