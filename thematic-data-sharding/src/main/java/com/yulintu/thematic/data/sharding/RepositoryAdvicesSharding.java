package com.yulintu.thematic.data.sharding;

import com.yulintu.thematic.ExceptionUtils;
import com.yulintu.thematic.data.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
public class RepositoryAdvicesSharding implements Ordered {

    //region fields
    public static final int ORDER = -3000000;

    @Autowired
    private ShardMethodInvokeMetadataFactory factory;
    //endregion

    public RepositoryAdvicesSharding() {

    }

    //region methods
    @Override
    public int getOrder() {
        return ORDER;
    }

    @Around("@annotation(com.yulintu.thematic.data.sharding.Shardable)")
    public Object onAround(ProceedingJoinPoint pjp) {

        ProviderSharding provider = null;
        ShardMethodInvokeMetadata metadata = null;

        try {
            Provider p = ProviderUtils.getCurrentProvider();
            if (!(p instanceof ProviderSharding))
                throw new DataException("这不是分库/分片数据源");

            provider = (ProviderSharding) p;
            metadata = factory.generate(pjp);
            if (metadata == null)
                return null;

        } catch (Throwable e) {
            ExceptionUtils.throwRuntimeException(e);
            return null;
        }

        Object result = invokeShards(pjp, metadata, provider);
        return result;
    }

    private Object invokeShards(ProceedingJoinPoint pjp, ShardMethodInvokeMetadata metadata, ProviderSharding provider) {

        try {
            ShardRouterHandler[] routers =
                    GlobalApplicationContext
                            .getApplicationContext()
                            .getBeansWithAnnotation(ShardRouter.class)
                            .values()
                            .toArray(new ShardRouterHandler[0]);

            List<ProviderDb> shards = null;
            if (metadata.isTransactional()) {
                shards = provider.getEditShards(
                        metadata.getKey(), c -> isValid(routers, metadata, c));
            } else
                shards = provider.getQueryShards(
                        metadata.getKey(), c -> isValid(routers, metadata, c));

            if (shards.size() == 0)
                throw new DataException("未名中任何分片规则");

            ShardMethodInvokeResult[] result = invokeShards(pjp, metadata, shards);
            return reduceResult(metadata, result);

        } catch (Throwable e) {
            ExceptionUtils.throwRuntimeException(e);
            return null;
        }
    }

    private ShardMethodInvokeResult[] invokeShards(ProceedingJoinPoint pjp, ShardMethodInvokeMetadata metadata, List<ProviderDb> shards) {

        ShardMethodInvokeResult[] result = new ShardMethodInvokeResult[shards.size()];
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String name = signature.getName();
        Class[] parameterTypes = signature.getParameterTypes();
        Object[] args = pjp.getArgs();
        AtomicInteger atomicInteger = new AtomicInteger(-1);

        shards.parallelStream().forEach(c -> {

            int index = atomicInteger.incrementAndGet();

            if (!c.isConnectionOpened())
                c.openConnection();
            if (metadata.isTransactional() && !c.isTransactionBegun())
                c.beginTransaction();

            Repository repository = RepositoryFactory.get(c)
                    .create(getTargetType(pjp), true);

            try {
                Method method = repository.getClass().getMethod(name, parameterTypes);
                ShardMethodInvokeResult ir = new ShardMethodInvokeResult();
                ir.setProvider(c);
                ir.setReturnValue(method.invoke(repository, args));
                result[index] = ir;

            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

        return result;
    }

    private boolean isValid(ShardRouterHandler[] routers, ShardMethodInvokeMetadata metadata, ShardElement shard) {

        for (ShardRouterHandler router : routers) {
            boolean valid = router.inspect(metadata, shard);
            if (valid)
                return true;
        }

        return false;
    }

    private Class getTargetType(ProceedingJoinPoint pjp) {
        return pjp.getSignature().getDeclaringType();
    }

    private Object reduceResult(ShardMethodInvokeMetadata metadata, ShardMethodInvokeResult[] shards) {

        ApplicationContext ac = GlobalApplicationContext.getApplicationContext();
        ShardReduceType reduceType = metadata.getReduceType();
        Class<ShardReducer> reducerType = metadata.getReducer();
        ShardReducer reducer = null;

        switch (reduceType) {
            case CUSTOM:
                reducer = ac.getBean(reducerType);
                break;
            case COMBINE:
                reducer = ac.getBean(ShardReducerCombine.class);
                break;
            case FIRSTNOTNULL:
                reducer = ac.getBean(ShardReducerFirstNotNull.class);
                break;
            case FIRST:
                reducer = ac.getBean(ShardReducerFirst.class);
                break;
            case SUM:
                reducer = ac.getBean(ShardReducerSum.class);
                break;
            case MAX:
                reducer = ac.getBean(ShardReducerMax.class);
                break;
            case MIN:
                reducer = ac.getBean(ShardReducerMin.class);
                break;
        }

        return reducer.reduce(metadata, shards);
    }
    //endregion
}
