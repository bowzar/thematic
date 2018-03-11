package com.yulintu.thematic.data.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HibernateSessionFactoryPool {

    //region fields
    private static final Map<String, SessionFactory> mapFactory = new ConcurrentHashMap<>();
    private static final Map<String, Configuration> mapConfiguration = new ConcurrentHashMap<>();
    //endregion

    //region ctor
    public HibernateSessionFactoryPool() {

    }
    //endregion

    //region methods
    public static boolean has(String connectionString) {
        return mapFactory.containsKey(connectionString);
    }

    public static SessionFactory initialize(String connectionString) {

        if (has(connectionString))
            return mapFactory.get(connectionString);

        HibernateConnectionStringBuilder builder = new HibernateConnectionStringBuilder(connectionString);

        String filePath = builder.getConfigureFilePath();
        boolean useFile = !filePath.isEmpty();

        Configuration configure = useFile ? new Configuration().configure(filePath) : new Configuration();

        if (builder.hasKey("hibernate.show_sql"))
            configure.setProperty("hibernate.show_sql", builder.getShowSql());
        if (builder.hasKey("hibernate.format_sql"))
            configure.setProperty("hibernate.format_sql", builder.getFormatSql());

        if (builder.hasKey("hibernate.dialect"))
            configure.setProperty("hibernate.dialect", builder.getDialect());
        if (builder.hasKey("hibernate.connection.driver_class"))
            configure.setProperty("hibernate.connection.driver_class", builder.getDriverClass());
        if (builder.hasKey("hibernate.connection.host") && builder.hasKey("hibernate.connection.database"))
            configure.setProperty("hibernate.connection.url", String.format("%s;databaseName=%s", builder.getHost(), builder.getDatabase()));
        if (builder.hasKey("hibernate.connection.username"))
            configure.setProperty("hibernate.connection.username", builder.getUsername());
        if (builder.hasKey("hibernate.connection.password"))
            configure.setProperty("hibernate.connection.password", builder.getPassword());

        SessionFactory factory = configure.buildSessionFactory();
        mapFactory.put(connectionString, factory);
        mapConfiguration.put(connectionString, configure);

        return factory;
    }

    public static String getDriverClass(String connectionString) {
        return has(connectionString) ? mapConfiguration.get(
                connectionString).getProperty("provider.type") : null;
    }
    //endregion
}
