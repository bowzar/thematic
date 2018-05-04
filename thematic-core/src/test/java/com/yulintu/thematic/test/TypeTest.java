package com.yulintu.thematic.test;

import com.yulintu.thematic.ClassUtils;
import com.yulintu.thematic.JavaTypeConverter;
import org.joda.time.DateTime;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class TypeTest {

    @Test
    public void testTypeConverter() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        JavaTypeConverter converter = new JavaTypeConverter();

        System.out.println(converter.to(String.class, 532532));
        System.out.println(converter.to(Integer.class, "6543"));
        System.out.println(converter.to(Boolean.class, "6543"));
        System.out.println(converter.to(Boolean.class, "false"));
        System.out.println(converter.to(Boolean.class, "true"));
        System.out.println(converter.to(Boolean.class, 143));
        System.out.println(converter.to(Boolean.class, 0));
        System.out.println(converter.to(boolean.class, "6543"));
        System.out.println(converter.to(boolean.class, "6543"));
        System.out.println(converter.to(boolean.class, "false"));
        System.out.println(converter.to(boolean.class, "true"));
        System.out.println(converter.to(boolean.class, 143));
        System.out.println(converter.to(boolean.class, 0));
        System.out.println(converter.to(UUID.class, UUID.randomUUID()));
        System.out.println(converter.to(UUID.class, UUID.randomUUID().toString()));
        System.out.println(converter.to(DateTime.class, "2015-11-15T12:0:0.43"));
        System.out.println(converter.to(DateTime.class, "2015-11-15"));
    }

    @Test
    public void testScanPackage() {
//        ClassUtils.scanClassByPackage("com.yulintu.thematic", c -> {
//
//            Class type = (Class) c;
//            System.out.println(type.getName());
//
//            return true;
//        });
    }
}
