package com.yulintu.thematic;


import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodUtils {

    public static Method[] getMethods(Class<?> type, boolean searchSuper, boolean ignoreAccess) {

        List<Method> methods = new ArrayList<>();

        do {
            methods.addAll(
                    Arrays.stream(ignoreAccess ?
                            type.getDeclaredMethods() :
                            type.getMethods()).collect(Collectors.toList()));

            type = type.getSuperclass();

        } while (searchSuper && type != null);

        return methods.toArray(new Method[0]);
    }

    public static <T extends Annotation> List<T> getAnnotations(Method m, Class<? extends Annotation> annotationType) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        ArrayList<T> ts = new ArrayList<>();
        Repeatable repeatable = annotationType.getAnnotation(Repeatable.class);

        Annotation[] annotations = m.getAnnotations();
        for (Annotation c : annotations) {

            Class<? extends Annotation> aClass = c.getClass();

            if (repeatable != null && repeatable.value().isAssignableFrom(aClass)) {
                Method value = aClass.getMethod("value");
                T[] array = (T[]) value.invoke(c);
                ts.addAll(Arrays.stream(array).collect(Collectors.toList()));
            }

            if (annotationType.isAssignableFrom(aClass)) {
                ts.add((T) c);
            }
        }

        return ts;
    }
}
