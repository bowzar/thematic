package com.yulintu.thematic.data.hibernate;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.yulintu.thematic.AssertUtils;
import com.yulintu.thematic.ClassUtils;
import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.data.querydsl.EntityContext;
import com.yulintu.thematic.data.querydsl.QueryDSLUtils;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

public class RepositoryPersistenceQueryDSLImpl extends RepositoryPersistenceImpl implements RepositoryPersistenceQueryDSL {

    //region ctor
    public RepositoryPersistenceQueryDSLImpl(Provider provider) {
        super(provider);
    }
    //endregion

    //region methods - query
    @Override
    public <T> boolean any(EntityPath<T> path) {

        return getJPAQueryFactory()
                .from(path)
                .offset(0)
                .limit(1)
                .select(QueryDSLUtils.getId(path))
                .fetch()
                .size() > 0;
    }

    @Override
    public <T> boolean any(EntityPath<T> path, Predicate predicate) {

        return getJPAQueryFactory()
                .from(path)
                .where(predicate)
                .offset(0)
                .limit(1)
                .select(QueryDSLUtils.getId(path))
                .fetch()
                .size() > 0;
    }

    @Override
    public <T> int count(EntityPath<T> path) {

        return (int) getJPAQueryFactory()
                .from(path)
                .fetchCount();
    }

    @Override
    public <T> int count(EntityPath<T> path, Predicate predicate) {

        return (int) getJPAQueryFactory()
                .from(path)
                .where(predicate)
                .fetchCount();
    }

    @Override
    public <T> T first(EntityPath<T> path) {

        return getJPAQueryFactory()
                .selectFrom(path)
                .offset(0)
                .limit(1)
                .fetchOne();
    }

    @Override
    public <T> T first(EntityPath<T> path, Expression... selector) {

        EntityContext pathType = EntityContext.getByPathType(path.getClass());
        Class<T> typeEntity = pathType.getTypeEntity();

        return QueryDSLUtils.entityFrom(getJPAQueryFactory()
                .from(path)
                .offset(0)
                .limit(1)
                .select(selector)
                .fetchOne(), selector, typeEntity);
    }

    @Override
    public <T> T first(EntityPath<T> path, Predicate predicate) {

        return getJPAQueryFactory()
                .selectFrom(path)
                .where(predicate)
                .offset(0)
                .limit(1)
                .fetchOne();
    }

    @Override
    public <T> T first(EntityPath<T> path, Predicate predicate, Expression... selector) {

        EntityContext pathType = EntityContext.getByPathType(path.getClass());
        Class<T> typeEntity = pathType.getTypeEntity();

        return QueryDSLUtils.entityFrom(getJPAQueryFactory()
                .from(path)
                .where(predicate)
                .offset(0)
                .limit(1)
                .select(selector)
                .fetchOne(), selector, typeEntity);
    }

    @Override
    public <T, R> R first(EntityPath<T> path, Expression<R> selectField) {

        return getJPAQueryFactory()
                .from(path)
                .offset(0)
                .limit(1)
                .select(selectField)
                .fetchOne();
    }

    @Override
    public <T, R> R first(EntityPath<T> path, Predicate predicate, Expression<R> selectField) {

        return getJPAQueryFactory()
                .from(path)
                .where(predicate)
                .offset(0)
                .limit(1)
                .select(selectField)
                .fetchOne();
    }

    @Override
    public <T, R> R first(EntityPath<T> path, Class<R> target) {

        Expression[] selector = QueryDSLUtils.select(path, target);

        return QueryDSLUtils.entityFrom(getJPAQueryFactory()
                .from(path)
                .offset(0)
                .limit(1)
                .select(selector)
                .fetchOne(), selector, target);
    }

    @Override
    public <T, R> R first(EntityPath<T> path, Class<R> target, Expression... selector) {

        return QueryDSLUtils.entityFrom(getJPAQueryFactory()
                .from(path)
                .offset(0)
                .limit(1)
                .select(selector)
                .fetchOne(), selector, target);
    }

    @Override
    public <T, R> R first(EntityPath<T> path, Predicate predicate, Class<R> target) {

        Expression[] selector = QueryDSLUtils.select(path, target);

        return QueryDSLUtils.entityFrom(getJPAQueryFactory()
                .from(path)
                .where(predicate)
                .offset(0)
                .limit(1)
                .select(selector)
                .fetchOne(), selector, target);
    }

    @Override
    public <T, R> R first(EntityPath<T> path, Predicate predicate, Class<R> target, Expression... selector) {

        return QueryDSLUtils.entityFrom(getJPAQueryFactory()
                .from(path)
                .where(predicate)
                .offset(0)
                .limit(1)
                .select(selector)
                .fetchOne(), selector, target);
    }

    @Override
    public <T> List<T> list(EntityPath<T> path) {

        return getJPAQueryFactory()
                .selectFrom(path)
                .fetch();
    }

    @Override
    public <T> List<T> list(EntityPath<T> path, Expression... selector) {

        EntityContext pathType = EntityContext.getByPathType(path.getClass());
        Class<T> typeEntity = pathType.getTypeEntity();

        return QueryDSLUtils.entitiesFrom(getJPAQueryFactory()
                .from(path)
                .select(selector)
                .fetch(), selector, typeEntity);
    }

    @Override
    public <T> List<T> list(EntityPath<T> path, Predicate predicate) {

        return getJPAQueryFactory()
                .selectFrom(path)
                .where(predicate)
                .fetch();
    }

    @Override
    public <T> List<T> list(EntityPath<T> path, Predicate predicate, Expression... selector) {

        EntityContext pathType = EntityContext.getByPathType(path.getClass());
        Class<T> typeEntity = pathType.getTypeEntity();

        return QueryDSLUtils.entitiesFrom(getJPAQueryFactory()
                .from(path)
                .where(predicate)
                .select(selector)
                .fetch(), selector, typeEntity);
    }

    @Override
    public <T, R> List<R> list(EntityPath<T> path, Expression<R> selectField) {

        return getJPAQueryFactory()
                .from(path)
                .select(selectField)
                .fetch();
    }

    @Override
    public <T, R> List<R> list(EntityPath<T> path, Predicate predicate, Expression<R> selectField) {

        return getJPAQueryFactory()
                .from(path)
                .where(predicate)
                .select(selectField)
                .fetch();
    }

    @Override
    public <T, R> List<R> list(EntityPath<T> path, Class<R> target) {

        Expression[] selector = QueryDSLUtils.select(path, target);
        return QueryDSLUtils.entitiesFrom(getJPAQueryFactory()
                .from(path)
                .select(selector)
                .fetch(), selector, target);
    }

    @Override
    public <T, R> List<R> list(EntityPath<T> path, Class<R> target, Expression... selector) {

        return QueryDSLUtils.entitiesFrom(getJPAQueryFactory()
                .from(path)
                .select(selector)
                .fetch(), selector, target);
    }

    @Override
    public <T, R> List<R> list(EntityPath<T> path, Predicate predicate, Class<R> target) {

        Expression[] selector = QueryDSLUtils.select(path, target);
        return QueryDSLUtils.entitiesFrom(getJPAQueryFactory()
                .from(path)
                .where(predicate)
                .select(selector)
                .fetch(), selector, target);
    }

    @Override
    public <T, R> List<R> list(EntityPath<T> path, Predicate predicate, Class<R> target, Expression... selector) {

        return QueryDSLUtils.entitiesFrom(getJPAQueryFactory()
                .from(path)
                .where(predicate)
                .select(selector)
                .fetch(), selector, target);
    }

    //endregion


    //region methods - edit
    @Override
    public <T> int add(T value) {
        getEntityManager().persist(value);
        return 1;
    }

    @Override
    public <T, O> int add(EntityPath<T> path, O value) {

        EntityContext context = EntityContext.getByPathType(path.getClass());
        AssertUtils.notNull(context, "context");

        Object obj = ClassUtils.newInstance(context.getTypeEntity());

        try {
            BeanUtils.copyProperties(obj, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return add(obj);
    }

    @Override
    public <T> int update(T value) {

        getEntityManager().merge(value);
        return 1;

//        EntityContext context = EntityContext.getByEntityType(value.getClass());
//
//        EntityPath path = context.getEntityPath();
//        Field idField = context.getIdField();
//        Expression idExpression = context.getIdExpression();
//        AssertUtils.notNull(idField, "idField");
//
//        Object id = ClassUtils.getValueFrom(value, idField);
//
//        return (int) QueryDSLUtils.update(
//                getJPAQueryFactory()
//                        .update(path)
//                        .where(Expressions.predicate(
//                                Ops.EQ,
//                                idExpression,
//                                Expressions.constant(id))),
//                path,
//                value).execute();
    }

    @Override
    public <T, O> int update(EntityPath<T> path, O value) {

        EntityContext context = EntityContext.getByPathType(path.getClass());

        String idName = context.getIdName();
        Expression idExpression = context.getIdExpression();
        AssertUtils.notNull(idExpression, "idExpression");

        Class<?> valueType = value.getClass();
        HashMap<String, Field> map = ClassUtils.fieldsToHashMap(valueType);
        if (!map.containsKey(idName))
            throw new RuntimeException(String.format("未找到类型 %s 的 Id 字段", valueType.getName()));

        Object id = ClassUtils.getValueFrom(value, map.get(idName));

        return (int) QueryDSLUtils.update(
                getJPAQueryFactory()
                        .update(path)
                        .where(Expressions.predicate(
                                Ops.EQ,
                                idExpression,
                                Expressions.constant(id))),
                path,
                value).execute();
    }

    @Override
    public <T, O> int update(EntityPath<T> path, Predicate predicate, O value) {

        return (int) QueryDSLUtils.update(
                getJPAQueryFactory()
                        .update(path)
                        .where(predicate), path, value).execute();
    }

    @Override
    public <T> int delete(T value) {

        getEntityManager().remove(value);
        return 1;
//
//
//        EntityContext context = EntityContext.getByEntityType(value.getClass());
//
//        EntityPath path = context.getEntityPath();
//        Field idField = context.getIdField();
//        Expression idExpression = context.getIdExpression();
//        AssertUtils.notNull(idField, "idField");
//
//        Object id = ClassUtils.getValueFrom(value, idField);
//
//        return (int) getJPAQueryFactory()
//                .delete(path)
//                .where(Expressions.predicate(
//                        Ops.EQ,
//                        idExpression,
//                        Expressions.constant(id)))
//                .execute();
    }

    @Override
    public <T, O> int delete(EntityPath<T> path, O value) {

        EntityContext context = EntityContext.getByPathType(path.getClass());

        String idName = context.getIdName();
        Expression idExpression = context.getIdExpression();
        AssertUtils.notNull(idExpression, "idExpression");

        Class<?> valueType = value.getClass();
        HashMap<String, Field> map = ClassUtils.fieldsToHashMap(valueType);
        if (!map.containsKey(idName))
            throw new RuntimeException(String.format("未找到类型 %s 的 Id 字段", valueType.getName()));

        Object id = ClassUtils.getValueFrom(value, map.get(idName));

        return (int) getJPAQueryFactory()
                .delete(path)
                .where(Expressions.predicate(
                        Ops.EQ,
                        idExpression,
                        Expressions.constant(id)))
                .execute();
    }

    @Override
    public <T> int delete(EntityPath<T> path, Predicate predicate) {

        return (int) getJPAQueryFactory()
                .delete(path)
                .where(predicate)
                .execute();
    }
    //endregion
}
