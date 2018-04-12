package com.yulintu.thematic.data.hibernate.postgresql;


public class PostgreSQLSpatialDialect extends org.hibernate.spatial.dialect.postgis.PostgisDialect {

    public PostgreSQLSpatialDialect() {
        super();

//        registerHibernateType(Types.CHAR, StandardBasicTypes.STRING.getName());
//        registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
//        registerHibernateType(Types.NCLOB, StandardBasicTypes.STRING.getName());
//        registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.STRING.getName());
//        registerHibernateType(Types.DECIMAL, StandardBasicTypes.DOUBLE.getName());
//        registerHibernateType(Types.STRUCT, StandardBasicTypes.BINARY.getName());
    }

}
