package com.yulintu.thematic.example.redis;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedis {

    private Jedis jedis;

    @Before
    public void before() {
        jedis = new Jedis("localhost");
        jedis.auth("123456");
    }

    @Test
    public void testCommon() {
        System.out.println("服务正在运行: " + jedis.ping());
    }

    @Test
    public void testKeys() {
        Set<String> keys = jedis.keys("*");
        keys.stream().forEach(c -> System.out.println(c));

        jedis.set("name", "JY");
        System.out.println(jedis.get("name"));
    }

    @Test
    public void testProvider() {
        Set<String> keys = jedis.keys("*");
        keys.stream().forEach(c -> System.out.println(c));

        jedis.set("name", "JY");
        System.out.println(jedis.get("name"));
    }
}
