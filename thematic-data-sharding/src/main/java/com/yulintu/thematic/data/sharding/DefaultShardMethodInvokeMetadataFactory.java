package com.yulintu.thematic.data.sharding;

import com.google.common.base.Strings;
import com.yulintu.thematic.ClassUtils;
import com.yulintu.thematic.data.DataException;
import javafx.util.Pair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;

@Component
public class DefaultShardMethodInvokeMetadataFactory implements ShardMethodInvokeMetadataFactory {

    @Override
    public ShardMethodInvokeMetadata generate(ProceedingJoinPoint pjp) {

        ShardMethodInvokeMetadata metadata = new ShardMethodInvokeMetadata();
        metadata.setJoinPoint(pjp);
        metadata.setTransactional(ShardingTransactionUtils.isNeedTransaction());

        Signature signature = pjp.getSignature();
        if (!(signature instanceof MethodSignature))
            return null;

        MethodSignature methodSignature = (MethodSignature) signature;

        Method method = null;
        Object target = pjp.getTarget();
        Class<?> targetClass = target.getClass();
        try {
            method = targetClass.getMethod(
                    methodSignature.getName(),
                    methodSignature.getParameterTypes());

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        Shardable shardable = method.getDeclaredAnnotation(Shardable.class);
        if (shardable == null)
            throw new DataException(String.format("方法 %s 未配置任何分库/分片规则", method.getName()));

        ShardConfig targetShardable = targetClass.getAnnotation(ShardConfig.class);
        String type = shardable.value();
        if (Strings.isNullOrEmpty(type) && targetShardable != null)
            type = targetShardable.value();

        metadata.setReduceType(shardable.reduceType());
        metadata.setReducer(shardable.reducer());
        metadata.setShardType(type);

        if (metadata.getReduceType() == ShardReduceType.CUSTOM && metadata.getReducer() == ShardReducer.class)
            throw new DataException(String.format("方法 %s 的分片归约类型为 Custom , 但未指定分片归约器", method.getName()));

        HashMap<Pair<String, Integer>, String[]> indexes = new HashMap<>();

        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {

            Parameter parameter = parameters[i];
            String name = parameter.getName();
            ShardKey annotation = parameter.getDeclaredAnnotation(ShardKey.class);
            if (annotation == null)
                continue;

            Pair<String, Integer> pair = new Pair<>(name, i);
            indexes.put(pair, annotation.value());
        }

        Object[] args = pjp.getArgs();
        StringBuilder b = new StringBuilder(type + "#");
        indexes.keySet().stream().forEach(c -> {

            String name = c.getKey();
            Integer index = c.getValue();
            String[] paths = indexes.get(c);

            if (paths.length == 0 || paths.length == 1 && Strings.isNullOrEmpty(paths[0])) {
                metadata.getArgs().put(name, args[index]);
                b.append(String.format("%s#", args[index]));
                return;
            }

            Object obj = args[index];
            if (obj == null)
                return;

            for (String path : paths) {
                Object fieldValue = ClassUtils.getFieldValue(obj, path);
                metadata.getArgs().put(path, fieldValue);
                b.append(String.format("%s#", fieldValue));
            }
        });

        metadata.setKey(b.toString());
        return metadata;
    }
}
