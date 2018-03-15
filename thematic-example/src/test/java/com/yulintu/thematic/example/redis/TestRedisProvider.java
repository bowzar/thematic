package com.yulintu.thematic.example.redis;


import com.yulintu.thematic.data.redis.ProviderRedis;
import com.yulintu.thematic.data.redis.ProviderRedisImpl;
import com.yulintu.thematic.data.redis.RedisConnectionStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedisProvider {

    private ProviderRedis provider;
    private ProviderRedis provider0;
    private ProviderRedis provider1;

    @Before
    public void before() {
        RedisConnectionStringBuilder builder = null;

        builder = new RedisConnectionStringBuilder("configure=redis.0.xml");
        provider0 = new ProviderRedisImpl(builder.getConnectionString());

        builder = new RedisConnectionStringBuilder("configure=redis.1.xml");
        provider1 = new ProviderRedisImpl(builder.getConnectionString());

        provider = provider0;
    }

    @Test
    public void testProvider() {
        provider0.executeInSession(jedis -> jedis.set("des", "value from 0"));
        provider1.executeInSession(jedis -> jedis.set("des", "value from 1"));

        provider0.executeInSession(jedis -> System.out.println(jedis.get("des")));
        provider1.executeInSession(jedis -> System.out.println(jedis.get("des")));
    }
}
