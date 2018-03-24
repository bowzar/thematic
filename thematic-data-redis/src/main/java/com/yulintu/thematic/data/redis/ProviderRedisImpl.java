package com.yulintu.thematic.data.redis;

import com.yulintu.thematic.ExceptionUtils;
import com.yulintu.thematic.data.ProviderDbImpl;
import redis.clients.jedis.*;

import java.io.IOException;

public class ProviderRedisImpl extends ProviderDbImpl implements ProviderRedis {

    //region fields

    private JedisPool pool;
    private Jedis jedis;
    private Transaction transaction;

    //endregion

    //region ctor
    public ProviderRedisImpl(String connectionString) {
        super(connectionString);
    }

    @Override
    protected void finalize() {
        pool = null;
        jedis = null;
        transaction = null;
    }

    //endregion

    //region methods
    @Override
    protected void initialize(String connectionString) {
        pool = (JedisPool) RedisPoolPool.initialize(connectionString);
    }

    @Override
    protected boolean onOpenConnection() {
        jedis = pool.getResource();
        return true;
    }

    @Override
    protected boolean onCloseConnection() {
        jedis.close();
        jedis = null;
        return true;
    }

    @Override
    protected boolean onBeginTransaction() {
        transaction = jedis.multi();
        return true;
    }

    @Override
    protected boolean onCommitTransaction() {
        transaction.exec();

        try {
            transaction.close();

        } catch (IOException e) {
            ExceptionUtils.throwRuntimeException(e);
            return false;
        }

        transaction = null;
        return true;
    }

    @Override
    protected boolean onRollbackTransaction() {
        transaction.discard();

        try {
            transaction.close();

        } catch (IOException e) {
            ExceptionUtils.throwRuntimeException(e);
            return false;
        }

        transaction = null;
        return true;
    }

    @Override
    public void execute(RedisExecute callback) {
        callback.execute(jedis);
    }

    @Override
    public void executeInSession(RedisExecute callback) {

        try (Jedis jedis = pool.getResource()) {
            callback.execute(jedis);
        }
    }

    @Override
    public void executeInTransaction(RedisTransactionExecute callback) {

        try (Jedis jedis = pool.getResource()) {
            try (Transaction multi = jedis.multi()) {
                callback.execute(multi);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //endregion
}
