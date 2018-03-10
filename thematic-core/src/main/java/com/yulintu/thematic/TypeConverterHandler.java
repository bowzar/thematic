package com.yulintu.thematic;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(value = TypeConverterHandlers.class)
public @interface TypeConverterHandler {
    Class<?> value();
}
