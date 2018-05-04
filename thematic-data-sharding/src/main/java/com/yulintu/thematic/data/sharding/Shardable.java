package com.yulintu.thematic.data.sharding;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Shardable {

    String value() default "";

    ShardReduceType reduceType() default ShardReduceType.CUSTOM;

    Class<ShardReducer> reducer() default ShardReducer.class;
}
