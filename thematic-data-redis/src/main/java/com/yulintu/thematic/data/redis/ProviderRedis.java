package com.yulintu.thematic.data.redis;

import com.yulintu.thematic.data.ProviderDb;

import java.util.List;

public interface ProviderRedis extends ProviderDb {

    void execute(RedisExecute callback);

    void executeInSession(RedisExecute callback);

    void executeInTransaction(RedisTransactionExecute callback);
}
