package com.yulintu.thematic.data.hibernate;


import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.yulintu.thematic.data.Repository;

import java.util.List;

public interface RepositoryPersistenceQueryDSL extends Repository {

    //region methods - query
    <T> boolean any(EntityPath<T> path);

    <T> boolean any(EntityPath<T> path, Predicate predicate);


    <T> int count(EntityPath<T> path);

    <T> int count(EntityPath<T> path, Predicate predicate);


    <T> T first(EntityPath<T> path);

    <T> T first(EntityPath<T> path, Expression... selector);

    <T> T first(EntityPath<T> path, Predicate predicate);

    <T> T first(EntityPath<T> path, Predicate predicate, Expression... selector);

    <T, R> R first(EntityPath<T> path, Expression<R> selectField);

    <T, R> R first(EntityPath<T> path, Predicate predicate, Expression<R> selectField);

    <T, R> R first(EntityPath<T> path, Class<R> target);

    <T, R> R first(EntityPath<T> path, Class<R> target, Expression... selector);

    <T, R> R first(EntityPath<T> path, Predicate predicate, Class<R> target);

    <T, R> R first(EntityPath<T> path, Predicate predicate, Class<R> target, Expression... selector);


    <T> List<T> list(EntityPath<T> path);

    <T> List<T> list(EntityPath<T> path, Expression... selector);

    <T> List<T> list(EntityPath<T> path, Predicate predicate);

    <T> List<T> list(EntityPath<T> path, Predicate predicate, Expression... selector);

    <T, R> List<R> list(EntityPath<T> path, Expression<R> selectField);

    <T, R> List<R> list(EntityPath<T> path, Predicate predicate, Expression<R> selectField);

    <T, R> List<R> list(EntityPath<T> path, Class<R> target);

    <T, R> List<R> list(EntityPath<T> path, Class<R> target, Expression... selector);

    <T, R> List<R> list(EntityPath<T> path, Predicate predicate, Class<R> target);

    <T, R> List<R> list(EntityPath<T> path, Predicate predicate, Class<R> target, Expression... selector);
    //endregion

    //region methods - edit
    <T> int add(T value);

    <T, O> int add(EntityPath<T> path, O value);

    <T> int update(T value);

    <T, O> int update(EntityPath<T> path, O value);

    <T, O> int update(EntityPath<T> path, Predicate predicate, O value);

    <T> int delete(T value);

    <T, O> int delete(EntityPath<T> path, O value);

    <T> int delete(EntityPath<T> path, Predicate predicate);
    //endregion
}
