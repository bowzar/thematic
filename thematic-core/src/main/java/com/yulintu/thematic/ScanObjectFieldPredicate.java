package com.yulintu.thematic;

import java.lang.reflect.Field;

public interface ScanObjectFieldPredicate {

    boolean test(Field field, Object value);
}
