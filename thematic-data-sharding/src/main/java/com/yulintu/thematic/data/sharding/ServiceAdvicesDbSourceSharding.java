package com.yulintu.thematic.data.sharding;

import com.yulintu.thematic.ExceptionUtils;
import com.yulintu.thematic.data.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Aspect
public class ServiceAdvicesDbSourceSharding /*implements Ordered */{

    //region fields
    private static final Logger logger = LoggerFactory.getLogger(ServiceAdvicesDbSourceSharding.class);
//    public static final int ORDER = -3000000;
    //endregion

    //region methods
//    @Override
//    public int getOrder() {
//        return ORDER;
//    }

    @Around("this(com.yulintu.thematic.data.Service)")
    public Object onAround(ProceedingJoinPoint pjp) {

        ProviderSharding provider = null;

        try {
            Provider p = ProviderUtils.initializeCurrentProvider();
            if (!(p instanceof ProviderSharding))
                throw new DataException("这不是分库/分片数据源");

            ShardingTransactionUtils.setNeedTransaction(hasTransactional(pjp));

            provider = (ProviderSharding) p;
            Object result = pjp.proceed(pjp.getArgs());

            commit(provider);
            return result;

        } catch (Throwable e) {

            if (provider != null) {
                rollback(provider);
                ProviderUtils.clearCurrentProvider();
                RepositoryFactory.clear(provider);
                provider.getAvailableShards().forEach(RepositoryFactory::clear);
            }

            ExceptionUtils.throwRuntimeException(e);
            return null;
        }
    }

    private void rollback(ProviderSharding provider) {

        List<ProviderDb> shards = provider.getAvailableShards();

        shards.parallelStream().forEach(c -> {

            try {
                if (c.isTransactionBegun())
                    c.rollbackTransaction();

            } catch (Throwable e) {
                logger.error(String.format("回滚 %s 的事务过程中出现异常。%s", c.getConnectionString(), e), e);
            }

            try {
                if (c.isConnectionOpened())
                    c.closeConnection();

            } catch (Throwable e) {
                logger.error(String.format("关闭 %s 的连接过程中出现异常。%s", c.getConnectionString(), e), e);
            }
        });
    }

    private void commit(ProviderSharding provider) {

        List<ProviderDb> shards = provider.getAvailableShards();
        AtomicReference<RuntimeException> error = new AtomicReference<>();

        shards.parallelStream().forEach(c -> {

            try {
                if (error.get() != null)
                    return;
                if (c.isTransactionBegun())
                    c.commitTransaction();

            } catch (Throwable e) {
                error.set(ExceptionUtils.createRuntimeException(e));
                logger.error(String.format("回滚 %s 的事务过程中出现异常。%s", c.getConnectionString(), e), e);
            }

            try {
                if (error.get() != null)
                    return;
                if (c.isConnectionOpened())
                    c.closeConnection();

            } catch (Throwable e) {
                error.set(ExceptionUtils.createRuntimeException(e));
                logger.error(String.format("关闭 %s 的连接过程中出现异常。%s", c.getConnectionString(), e), e);
            }
        });

        if (error.get() != null)
            throw error.get();
    }

    private boolean hasTransactional(ProceedingJoinPoint pjp) {

        Signature signature = pjp.getSignature();
        if (!(signature instanceof MethodSignature))
            return false;

        MethodSignature methodSignature = (MethodSignature) signature;
        Object target = pjp.getTarget();
        Method method = null;

        try {
            method = target.getClass().getMethod(
                    methodSignature.getName(),
                    methodSignature.getParameterTypes());

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        Transactional transactional = method.getDeclaredAnnotation(Transactional.class);
        return transactional != null;
    }
}
