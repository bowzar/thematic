package com.yulintu.thematic.data.hibernate;

import com.google.common.base.Strings;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityManagerFactoryPool {

    //region fields
    private static final Map<String, EntityManagerFactory> mapFactory = new ConcurrentHashMap<>();
    private static final Map<String, Configuration> mapConfiguration = new ConcurrentHashMap<>();
    //endregion

    //region ctor
    public EntityManagerFactoryPool() {

    }
    //endregion

    //region methods
    public static boolean has(String connectionString) {
        return mapFactory.containsKey(connectionString);
    }

    public static EntityManagerFactory initialize(String connectionString) {

        if (has(connectionString))
            return mapFactory.get(connectionString);

        HibernateConnectionStringBuilder builder = new HibernateConnectionStringBuilder(connectionString);

        String filePath = builder.getConfigureFilePath();
        boolean useFile = !Strings.isNullOrEmpty(filePath);

        Configuration configure = null;

        if (useFile) {
            configure = tryLoad(String.format("file:%s", filePath));
            if (configure == null)
                configure = tryLoad(String.format("file:config/%s", filePath));
            if (configure == null)
                configure = tryLoad(filePath);
        } else {
            configure = new Configuration();
        }

        if (builder.hasKey("hibernate.show_sql"))
            configure.setProperty("hibernate.show_sql", builder.getShowSql());
        if (builder.hasKey("hibernate.format_sql"))
            configure.setProperty("hibernate.format_sql", builder.getFormatSql());

        if (builder.hasKey("hibernate.dialect"))
            configure.setProperty("hibernate.dialect", builder.getDialect());
        if (builder.hasKey("hibernate.connection.driver_class"))
            configure.setProperty("hibernate.connection.driver_class", builder.getDriverClass());
        if (builder.hasKey("hibernate.connection.url"))
            configure.setProperty("hibernate.connection.url", builder.getUrl());
        if (builder.hasKey("hibernate.connection.username"))
            configure.setProperty("hibernate.connection.username", builder.getUsername());
        if (builder.hasKey("hibernate.connection.password"))
            configure.setProperty("hibernate.connection.password", builder.getPassword());

        final Configuration config = configure;
        if (builder.hasKey("mappingClasses"))
            Arrays.stream(builder.getMappingClasses()).forEach(c -> config.addAnnotatedClass(c));

        SessionFactory factory = configure.buildSessionFactory();
        mapFactory.put(connectionString, factory);
        mapConfiguration.put(connectionString, configure);

        return factory;
    }

    public static String getDriverClass(String connectionString) {
        return has(connectionString) ? mapConfiguration.get(
                connectionString).getProperty("provider.type") : null;
    }

    private static Configuration tryLoad(String filePath) {
        try {
            return new Configuration().configure(filePath);

        } catch (Throwable e) {
            return null;
        }
    }
    //endregion
}
