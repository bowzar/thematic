package com.yulintu.thematic.data.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.querydsl.spatial.GeometryExpression;
import com.yulintu.thematic.ArrayUtils;
import com.yulintu.thematic.AssertUtils;
import com.yulintu.thematic.ClassUtils;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class QueryDSLUtils {


    //region methods - select
    public static Expression[] asExpressionArray(Expression... items) {
        return items;
    }

    public static Expression[] select(EntityPath target, Class template) {

        HashMap<String, Field> map = ArrayUtils.toHashMap(
                c -> c.getName(),
                c -> c,
                ClassUtils.getInstanceFields(template));

        ArrayList<Expression> list = new ArrayList<>();

        Field[] fields = target.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(c -> {

            if (Modifier.isStatic(c.getModifiers()))
                return;

            Class<?> type = c.getType();

            if (map.containsKey(c.getName())) {
                try {
                    list.add((Expression) c.get(target));

                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return list.toArray(new Expression[0]);
    }

    public static Expression[] selectAttributes(EntityPath target) {

        ArrayList<Expression> list = new ArrayList<>();

        Field[] fields = target.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(c -> {

            if (Modifier.isStatic(c.getModifiers()))
                return;

            Class<?> type = c.getType();

            if (c.getName().toLowerCase().equals("shape") || GeometryExpression.class.isAssignableFrom(type))
                return;

            if (Expression.class.isAssignableFrom(type)) {

                try {
                    list.add((Expression) c.get(target));

                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return list.toArray(new Expression[0]);
    }

    public static Expression[] selectExcept(EntityPath target, Expression... excludes) {

        ArrayList<Expression> list = new ArrayList<>();
        HashSet<Expression> expressions = ArrayUtils.toHashSet(excludes);

        Field[] fields = target.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(c -> {

            if (Modifier.isStatic(c.getModifiers()))
                return;

            Class<?> type = c.getType();

            if (expressions.contains(ClassUtils.getValueFrom(target, c)))
                return;

            if (Expression.class.isAssignableFrom(type)) {

                try {
                    list.add((Expression) c.get(target));

                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return list.toArray(new Expression[0]);
    }

    public static Expression getId(EntityPath target) {

        EntityContext context = EntityContext.from(target.getClass());
        return context.getIdExpression();
    }
    //endregion

    //region methods - fetch
    public static <T> T entityFrom(Tuple tuple, Expression[] expressions, Class<T> target) {

        if (tuple == null)
            return null;

        HashMap<String, Field> map = ArrayUtils.toHashMap(
                c -> c.getName(),
                c -> c,
                target.getDeclaredFields());

        return innerEntityFrom(tuple, expressions, map, target);
    }

    public static <T> List<T> entitiesFrom(List<Tuple> tuples, Expression[] expressions, Class<T> target) {

        if (tuples == null || tuples.size() == 0)
            return new ArrayList<>();

        HashMap<String, Field> map = ArrayUtils.toHashMap(
                c -> c.getName(),
                c -> c,
                target.getDeclaredFields());

        ArrayList<T> list = new ArrayList<>();
        for (Tuple tuple : tuples) {
            list.add(innerEntityFrom(tuple, expressions, map, target));
        }

        return list;
    }

    private static <T> T innerEntityFrom(Tuple tuple, Expression[] expressions, HashMap<String, Field> map, Class<T> target) {

        T obj = ClassUtils.newInstance(target);

        Arrays.stream(expressions).forEach(c -> {

            Path path = (Path) c;
            PathMetadata metadata = path.getMetadata();
            String name = metadata.getName();
            if (!map.containsKey(name))
                return;

            Object val = tuple.get(c);
            try {
                Field field = map.get(name);
                if (!field.isAccessible())
                    field.setAccessible(true);

                field.set(obj, val);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        return obj;
    }
    //endregion

    //region methods - update

    public static JPAUpdateClause update(JPAUpdateClause update, EntityPath target, Object value) {

        AssertUtils.notNull(value, "value");

        ClassUtils.scanFields(value, (field, obj) -> {

            Id annotation = field.getDeclaredAnnotation(Id.class);
            if (annotation != null)
                return true;

            EntityContext context = EntityContext.from(target.getClass());
            Expression expression = context.getExpression(field.getName());
            if (expression == null)
                return true;

            update.set((Path) expression, obj);
            return true;
        });

        return update;
    }

    //endregion

}
