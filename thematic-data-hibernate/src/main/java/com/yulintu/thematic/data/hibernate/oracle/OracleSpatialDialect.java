package com.yulintu.thematic.data.hibernate.oracle;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.Oracle12cIdentityColumnSupport;
import org.hibernate.dialect.pagination.LimitHandler;
import org.hibernate.dialect.pagination.SQL2008StandardLimitHandler;
import org.hibernate.service.ServiceRegistry;
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

    @Override
    protected void registerDefaultProperties() {
        super.registerDefaultProperties();
        this.getDefaultProperties().setProperty("hibernate.jdbc.use_get_generated_keys", "true");
    }

    @Override
    public LimitHandler getLimitHandler() {
        return SQL2008StandardLimitHandler.INSTANCE;
    }

    @Override
    public String getNativeIdentifierGeneratorStrategy() {
        return "sequence";
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new Oracle12cIdentityColumnSupport();
    }
}
