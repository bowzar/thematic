package com.yulintu.thematic.data.hibernate;

import com.querydsl.sql.SQLQueryFactory;

public interface SQLQueryDSLExecute {

    Object execute(SQLQueryFactory factory);
}
