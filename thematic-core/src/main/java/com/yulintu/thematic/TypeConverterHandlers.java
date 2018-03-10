package com.yulintu.thematic;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TypeConverterHandlers {
    TypeConverterHandler[] value();
}
