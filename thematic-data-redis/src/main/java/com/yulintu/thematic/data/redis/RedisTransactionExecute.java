package com.yulintu.thematic.data.redis;

import redis.clients.jedis.Transaction;

public interface RedisTransactionExecute {

    void execute(Transaction jedis);
}
