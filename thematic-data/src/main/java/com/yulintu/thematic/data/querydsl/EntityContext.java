package com.yulintu.thematic.data.querydsl;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.yulintu.thematic.ClassUtils;
import lombok.Getter;
import org.reflections.Reflections;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EntityContext {

    //region fields
    private static final ConcurrentHashMap<Class<? extends EntityPath>, EntityContext> mapPath = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class, EntityContext> mapEntity = new ConcurrentHashMap<>();

    private HashMap<String, Expression> mapExpressions = new HashMap<>();
    private HashMap<String, Field> mapFields = null;

    @Getter
    private Class typeEntity;

    @Getter
    private Class<? extends EntityPath> typePath;

    @Getter
    private EntityPath entityPath;

    @Getter
    private String idName;
    //endregion

    //region ctor
    private EntityContext() {
    }
    //endregion

    //region methods
    public static void installAll(String... basePackages) {

        Arrays.stream(basePackages).forEach(c -> {

            Reflections reflections = new Reflections(c);
            Set<Class<? extends EntityPath>> types = reflections.getSubTypesOf(EntityPath.class);
            types.forEach(type -> {

                String name = type.getPackage().getName();
                if (name.startsWith(c))
                    EntityContext.from(type);
            });

            Set<Class<?>> entityTypes = reflections.getTypesAnnotatedWith(Entity.class);
            entityTypes.forEach(type -> {

                String name = type.getPackage().getName();
                if (!name.startsWith(c))
                    return;

                if (mapEntity.containsKey(type))
                    return;

                mapEntity.put(type, null);
            });
        });
    }

    public static EntityContext from(Class<? extends EntityPath> target) {

        if (mapPath.containsKey(target))
            return mapPath.get(target);

        Field primaryField = ClassUtils.getFirstField(target, c -> {

            if (!ClassUtils.isStatic(c))
                return false;

            Class<?> type = c.getType();
            return type == target;
        });

        if (primaryField == null)
            throw new RuntimeException(String.format("无效的 QueryDSL 实体 %s", target.getName()));

        EntityPath path = null;
        try {
            path = (EntityPath) primaryField.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        EntityContext context = new EntityContext();
        context.entityPath = path;

        ClassUtils.scanFields(target, c -> {

            if (Modifier.isStatic(c.getModifiers()))
                return true;

            Class<?> type = c.getType();
            if (!Expression.class.isAssignableFrom(type))
                return true;

            try {
                context.mapExpressions.put(c.getName(), (Expression) c.get(context.entityPath));
                return true;

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        ParameterizedType type = (ParameterizedType) target.getGenericSuperclass();
        context.typeEntity = (Class) type.getActualTypeArguments()[0];

        if (context.typeEntity == null)
            throw new RuntimeException(String.format("无效的 QueryDSL 实体 %s", target.getName()));

        context.mapFields = ClassUtils.instanceFieldsToHashMap(context.typeEntity);

        Optional<Field> first = context.mapFields.values().stream()
                .filter(c -> c.getDeclaredAnnotation(Id.class) != null).findFirst();

        first.ifPresent(field -> context.idName = field.getName());

        mapPath.put(target, context);
        mapEntity.put(context.typeEntity, context);

        return context;
    }

    public static EntityContext getByPathType(Class type) {
        return mapPath.getOrDefault(type, null);
    }

    public static EntityContext getByEntityType(Class type) {
        return mapEntity.getOrDefault(type, null);
    }

    public static Class[] getAllEntities() {

        ArrayList<Class> list = new ArrayList<>();

        mapEntity.forEach((type, context) -> {
            list.add(type);
        });

        return list.toArray(new Class[0]);
    }

    public Expression getExpression(String name) {
        return mapExpressions.containsKey(name) ? mapExpressions.get(name) : null;
    }

    public Field getField(String name) {
        return mapFields.getOrDefault(name, null);
    }

    public Expression getIdExpression() {
        return mapExpressions.getOrDefault(idName, null);
    }

    public Field getIdField() {
        return mapFields.getOrDefault(idName, null);
    }

    //endregion
}
