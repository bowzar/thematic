package com.yulintu.thematic;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public abstract class TypeConverter {

    static {

    }

    private HashMap<Class<?>, Method> mapHandlers;

    public TypeConverter() {
        mapHandlers = new HashMap<>();

        Class<? extends TypeConverter> type = this.getClass();
        Method[] methods = MethodUtils.getMethods(type, true, true);

        for (Method m : methods) {
            List<TypeConverterHandler> annotations = null;

            try {
                annotations = MethodUtils.getAnnotations(m, TypeConverterHandler.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            for (TypeConverterHandler h : annotations) {
                mapHandlers.put(h.value(), m);
            }
        }
    }

    public Object toObject(Class<?> targetType, Object value) {
        if (value == null)
            return null;
        if (targetType.isInstance(value)) {
            return value;
        }
        if (mapHandlers.containsKey(targetType)) {

            try {
                return mapHandlers.get(targetType).invoke(this, value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return value;
    }

    public <T> T to(Class<T> targetType, Object value) {
        return (T) toObject(targetType, value);
    }
}
