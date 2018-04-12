package com.yulintu.thematic.data.hibernate.oracle;

import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

public class OracleSpatialDialect extends org.hibernate.spatial.dialect.oracle.OracleSpatial10gDialect {

    public OracleSpatialDialect() {
        super();

        registerHibernateType(Types.CHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.NCLOB, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.DECIMAL, StandardBasicTypes.DOUBLE.getName());
//        registerHibernateType(Types.STRUCT, StandardBasicTypes.BINARY.getName());
    }

}
