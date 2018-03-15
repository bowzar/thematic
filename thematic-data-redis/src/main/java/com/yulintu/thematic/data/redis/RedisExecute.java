package com.yulintu.thematic.data.redis;

import redis.clients.jedis.Jedis;

public interface RedisExecute {

    void execute(Jedis jedis);
}
