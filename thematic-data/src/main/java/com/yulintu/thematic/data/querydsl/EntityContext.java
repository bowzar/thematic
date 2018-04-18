package com.yulintu.thematic.data.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BeanPath;
import com.yulintu.thematic.ClassUtils;
import lombok.Getter;
import lombok.Setter;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EntityContext {

    //region fields
    private static final ConcurrentHashMap<Class<? extends BeanPath>, EntityContext> mapAll = new ConcurrentHashMap<>();

    private HashMap<String, Expression> map = new HashMap<>();
    private HashMap<String, Field> mapFields = null;

    @Getter
    @Setter
    private Class typeEntity;
    //endregion

    //region ctor
    private EntityContext(Class<? extends BeanPath> target) {
        install(target);
    }
    //endregion

    //region methods
    public static void installAll(String... basePackages) {

        Arrays.stream(basePackages).forEach(c -> {

            Reflections reflections = new Reflections(c);
            Set<Class<? extends BeanPath>> types = reflections.getSubTypesOf(BeanPath.class);
            types.forEach(type -> {

                String name = type.getPackage().getName();
                if (name.startsWith(c))
                    EntityContext.from(type);
            });
        });
    }

    public static EntityContext from(Class<? extends BeanPath> target) {

        if (mapAll.containsKey(target))
            return mapAll.get(target);

        EntityContext context = new EntityContext(target);
        mapAll.put(target, context);
        return context;
    }

    public Expression getExpression(String name) {
        return map.containsKey(name) ? map.get(name) : null;
    }

    public Field getField(String name) {
        return mapFields.containsKey(name) ? mapFields.get(name) : null;
    }

    private void install(Class<? extends BeanPath> target) {

        Field primaryField = ClassUtils.getFirstField(target, c -> {

            if (!ClassUtils.isStatic(c))
                return false;

            Class<?> type = c.getType();
            return type == target;
        });

        if (primaryField == null)
            throw new RuntimeException(String.format("无效的 QueryDSL 实体 %s", target.getName()));

        BeanPath path = null;
        try {
            path = (BeanPath) primaryField.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        BeanPath finalPath = path;
        ClassUtils.scanFields(target, c -> {

            if (Modifier.isStatic(c.getModifiers()))
                return true;

            Class<?> type = c.getType();
            if (!Expression.class.isAssignableFrom(type))
                return true;

            try {
                map.put(c.getName(), (Expression) c.get(finalPath));
                return true;

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        ParameterizedType type = (ParameterizedType) target.getGenericSuperclass();
        typeEntity = (Class) type.getActualTypeArguments()[0];

        if (typeEntity == null)
            throw new RuntimeException(String.format("无效的 QueryDSL 实体 %s", target.getName()));

        mapFields = ClassUtils.instanceFieldsToHashMap(typeEntity);
    }
    //endregion
}
