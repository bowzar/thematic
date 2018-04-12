package com.yulintu.thematic.data.hibernate;

import com.querydsl.jpa.impl.JPAQueryFactory;

public interface JPAQueryDSLExecute {

    Object execute(JPAQueryFactory factory);
}
