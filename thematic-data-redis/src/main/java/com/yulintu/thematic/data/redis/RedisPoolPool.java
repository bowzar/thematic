package com.yulintu.thematic.data.redis;

import com.google.common.base.Strings;
import com.yulintu.thematic.JavaTypeConverter;
import com.yulintu.thematic.data.ClassPathXmlApplicationContextPool;
import com.yulintu.thematic.data.KeyValueConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisPoolPool {

    //region fields
    private static final Map<String, Pool> mapFactory = new ConcurrentHashMap<>();
    //endregion

    //region ctor
    public RedisPoolPool() {

    }
    //endregion

    //region methods
    public static boolean has(String connectionString) {
        return mapFactory.containsKey(connectionString);
    }

    public static Pool initialize(String connectionString) {

        if (has(connectionString))
            return mapFactory.get(connectionString);

        RedisConnectionStringBuilder builder = new RedisConnectionStringBuilder(connectionString);

        String filePath = builder.getConfigureFilePath();
        boolean useFile = !Strings.isNullOrEmpty(filePath);

        Pool pool = null;

        if (useFile) {
            ClassPathXmlApplicationContext ac = ClassPathXmlApplicationContextPool.initialize(filePath);
            pool = ac.getBean(Pool.class);

            mapFactory.put(connectionString, pool);
            return pool;
        }

        String host = builder.getHost();
        String port = builder.getPort();
        String password = builder.getPassword();
        String database = builder.getDatabase();

        JedisPoolConfig config = new JedisPoolConfig();

        if (!Strings.isNullOrEmpty(host) &&
                !Strings.isNullOrEmpty(port) &&
                !Strings.isNullOrEmpty(password) &&
                !Strings.isNullOrEmpty(database))
            pool = new JedisPool(
                    config,
                    host,
                    JavaTypeConverter.getInstance().to(Integer.class, port),
                    60000,
                    password,
                    JavaTypeConverter.getInstance().to(Integer.class, database));
        else if (!Strings.isNullOrEmpty(host) &&
                !Strings.isNullOrEmpty(port) &&
                !Strings.isNullOrEmpty(password))
            pool = new JedisPool(
                    config,
                    host,
                    JavaTypeConverter.getInstance().to(Integer.class, port),
                    60000,
                    password);
        else if (!Strings.isNullOrEmpty(host) &&
                !Strings.isNullOrEmpty(port))
            pool = new JedisPool(
                    config,
                    host,
                    JavaTypeConverter.getInstance().to(Integer.class, port));
        else if (!Strings.isNullOrEmpty(host))
            pool = new JedisPool(host);
        else
            throw new RuntimeException("Redis 链接参数不全");

        mapFactory.put(connectionString, pool);
        return pool;
    }
    //endregion
}
