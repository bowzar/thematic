package com.yulintu.thematic;

import com.google.common.base.Strings;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class ClassUtils {

    //region methods - package
//    public static void scanClassByPackage(String packageName, Predicate predicate) {
//
//        String classpath = ClassUtils.class.getResource("/").getPath();
//        packageName = packageName.replace(".", File.separator);
//        String searchPath = classpath + packageName;
//
//        File file = new File(searchPath);
//        innerScanClassByPackage(classpath, file, predicate);
//    }
//
//    private static void innerScanClassByPackage(String classpath, File root, Predicate predicate) {
//
//        if (root.isDirectory()) {
//
//            File[] files = root.listFiles();
//            for (File file : files) {
//                innerScanClassByPackage(classpath, file, predicate);
//            }
//
//            return;
//        }
//
//        if (!root.getName().endsWith(".class"))
//            return;
//
//        String fileName = root.getPath().replace(classpath.replace("/", File.separator), "").replace(File.separator, ".").replace(".class", "");
//        Class type = null;
//
//        try {
//            type = Class.forName(fileName);
//            predicate.test(type);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
    //endregion


    //region methods - class

    public static boolean isTrue(Object value) {
        if (value == null)
            return false;

        Class<?> type = value.getClass();
        if (value instanceof Number)
            return JavaTypeConverter.getInstance().toDouble(value) != 0;

        if (value instanceof Boolean)
            return ((Boolean) value);

        if (value instanceof String)
            return !Strings.isNullOrEmpty((String) value);

        return true;
    }

    public static <T> T newInstance(Class<T> type) {
        try {
            return type.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class getClass(String name) {

        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Constructor<T> getConstructor(Class<T> type, Class... args) {

        try {
            return type.getConstructor(args);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T newInstance(Constructor<T> constructor, Object... args) {

        try {
            return constructor.newInstance(args);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion

    //region methods - fields
    public static Object getValueFrom(Object target, Field field) {
        try {
            if (!field.isAccessible())
                field.setAccessible(true);

            return field.get(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Field[] getInstanceFields(Class target) {

        ArrayList<Field> list = new ArrayList<>();

        Field[] fields = target.getDeclaredFields();
        Arrays.stream(fields).forEach(c -> {

            if (!isStatic(c))
                list.add(c);
        });

        return list.toArray(new Field[0]);
    }

    public static boolean isStatic(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    public static Field getField(Class target, String name) {

        try {
            return target.getField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getFieldValue(Object target, String name) {

        try {
            return target.getClass().getField(name).get(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

    public static Field getFirstField(Class target, Predicate<Field> predicate) {

        final Field[] field = {null};
        Field[] fields = target.getDeclaredFields();
        Arrays.stream(fields).anyMatch(c -> {

            boolean val = predicate.test(c);
            if (val)
                field[0] = c;

            return val;
        });

        return field[0];
    }

    public static void scanFields(Class target, Predicate<Field> predicate) {

        Field[] fields = target.getDeclaredFields();
        Arrays.stream(fields).anyMatch(c -> !predicate.test(c));

    }

    public static void scanInstanceFields(Class target, Predicate<Field> predicate) {

        Field[] fields = target.getDeclaredFields();
        Arrays.stream(fields).anyMatch(c -> {

            if (isStatic(c))
                return false;

            return !predicate.test(c);
        });

    }

    public static void scanFields(Object target, ScanObjectFieldPredicate predicate) {

        Field[] fields = target.getClass().getDeclaredFields();
        Arrays.stream(fields).anyMatch(c -> {

            if (isStatic(c))
                return false;

            try {
                if (!c.isAccessible())
                    c.setAccessible(true);

                return !predicate.test(c, c.get(target));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static HashMap<String, Field> fieldsToHashMap(Class target) {

        HashMap<String, Field> map = new HashMap<>();

        scanFields(target, c -> {
            map.put(c.getName(), c);
            return true;
        });

        return map;
    }

    public static HashMap<String, Field> instanceFieldsToHashMap(Class target) {

        HashMap<String, Field> map = new HashMap<>();

        scanFields(target, c -> {
            if (isStatic(c))
                return true;

            map.put(c.getName(), c);
            return true;
        });

        return map;
    }
    //endregion
}
