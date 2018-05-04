package com.yulintu.thematic.data.hibernate;

import com.google.common.base.Strings;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yulintu.thematic.data.ClassPathXmlApplicationContextPool;
import com.yulintu.thematic.data.DataException;
import com.yulintu.thematic.data.querydsl.EntityContext;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EntityManagerFactoryPool {

    //region fields
    private static final Logger logger = LoggerFactory.getLogger(EntityManagerFactoryPool.class);
    private static final Map<String, EntityManagerFactory> mapFactory = new ConcurrentHashMap<>();
    private static final Map<DataSource, EntityManagerFactory> mapDataSourceToFactory = new ConcurrentHashMap<>();
    private static final Map<String, Configuration> mapConfiguration = new ConcurrentHashMap<>();
    private static final Map<DataSource, Configuration> mapDataSourceToConfiguration = new ConcurrentHashMap<>();
    //endregion

    //region ctor
    public EntityManagerFactoryPool() {

    }
    //endregion

    //region methods
    public static String getProviderType(String connectionString) {
        return has(connectionString) ? mapConfiguration.get(
                connectionString).getProperty("provider.type") : null;
    }

    public static String getProviderType(DataSource dataSource) {
        return has(dataSource) ? mapDataSourceToConfiguration.get(
                dataSource).getProperty("provider.type") : null;
    }

    public static boolean has(String connectionString) {
        return mapFactory.containsKey(connectionString);
    }

    public static boolean has(DataSource dataSource) {
        return mapDataSourceToFactory.containsKey(dataSource);
    }

    public static EntityManagerFactory initialize(String connectionString) {

        if (has(connectionString)) {
            logger.info("已经存在使用连接字符串创建的 EntityManagerFactory. {}", connectionString);
            return mapFactory.get(connectionString);
        }

        HibernateConnectionStringBuilder builder = new HibernateConnectionStringBuilder(connectionString);

        String filePath = builder.getConfigureFilePath();
        String type = builder.getConfigureFileType();
        boolean useFile = !Strings.isNullOrEmpty(filePath);

        SessionFactory factory = null;
        if (useFile && "spring".equals(type.toLowerCase())) {
            factory = loadBySpring(connectionString, filePath);

        } else if (useFile) {
            factory = loadByHibernate(connectionString, builder, filePath, null);

        } else {
            factory = loadByString(connectionString, builder, null);
        }

        return factory;
    }

    public static EntityManagerFactory initialize(String connectionString, DataSource dataSource) {

        if (has(dataSource)) {
            logger.info("已经存在使用数据源创建的 EntityManagerFactory. {}", dataSource);
            return mapDataSourceToFactory.get(dataSource);
        }

        HibernateConnectionStringBuilder builder = new HibernateConnectionStringBuilder(connectionString);

        String filePath = builder.getConfigureFilePath();
        String type = builder.getConfigureFileType();
        boolean useFile = !Strings.isNullOrEmpty(filePath);

        SessionFactory factory = null;
        if (useFile && "spring".equals(type.toLowerCase())) {
            throw new DataException(String.format("当使用自定义数据源创建 EntityManagerFactory 时，不支持 Spring 配置模式"));

        } else if (useFile) {
            factory = loadByHibernate(connectionString, builder, filePath, dataSource);

        } else {
            factory = loadByString(connectionString, builder, dataSource);
        }

        return factory;
    }

    private static void updateConfiguration(HibernateConnectionStringBuilder builder, Configuration configure) {

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

        logger.info("开始初始化实体映射信息...");

        final Configuration config = configure;

        HashSet<Class> set = new HashSet<>();
        set.addAll(Arrays.stream(builder.getMappingClasses()).collect(Collectors.toList()));
        set.addAll(Arrays.stream(EntityContext.getAllEntities()).collect(Collectors.toList()));
        set.forEach(c -> {
            logger.info(c.getName());
            config.addAnnotatedClass(c);
        });

        logger.info("共获取到 {} 个实体映射信息...", set.size());
    }

    private static SessionFactory loadByString(String connectionString, HibernateConnectionStringBuilder builder, DataSource dataSource) {

        Configuration configure = new Configuration();
        updateConfiguration(builder, configure);

        SessionFactory factory = dataSource == null ?
                configure.buildSessionFactory() :
                configure.buildSessionFactory(new StandardServiceRegistryBuilder()
                        .applySettings(configure.getProperties())
                        .applySetting(Environment.DATASOURCE, dataSource)
                        .build());

        if (dataSource == null) {
            mapFactory.put(connectionString, factory);
            mapConfiguration.put(connectionString, configure);
        } else {
            mapDataSourceToFactory.put(dataSource, factory);
            mapDataSourceToConfiguration.put(dataSource, configure);
        }

        return factory;
    }

    private static SessionFactory loadByHibernate(String connectionString, HibernateConnectionStringBuilder builder, String filePath, DataSource dataSource) {

        Configuration configure = tryLoad(String.format("file:%s", filePath));
        if (configure == null)
            configure = tryLoad(String.format("file:config/%s", filePath));
        if (configure == null)
            configure = tryLoad(filePath);

        updateConfiguration(builder, configure);

        SessionFactory factory = dataSource == null ?
                configure.buildSessionFactory() :
                configure.buildSessionFactory(new StandardServiceRegistryBuilder()
                        .applySettings(configure.getProperties())
                        .applySetting(Environment.DATASOURCE, dataSource)
                        .build());

        if (dataSource == null) {
            mapFactory.put(connectionString, factory);
            mapConfiguration.put(connectionString, configure);
        } else {
            mapDataSourceToFactory.put(dataSource, factory);
            mapDataSourceToConfiguration.put(dataSource, configure);
        }

        return factory;
    }

    private static SessionFactory loadBySpring(String connectionString, String filePath) {

        ApplicationContext ac = ClassPathXmlApplicationContextPool.findInitialize(filePath);
        LocalSessionFactoryBean bean = ac.getBean(LocalSessionFactoryBean.class);

        Configuration configure = bean.getConfiguration();
        SessionFactory factory = bean.getObject();

        mapFactory.put(connectionString, factory);
        mapConfiguration.put(connectionString, configure);

        return factory;
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
