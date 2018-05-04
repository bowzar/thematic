package com.yulintu.thematic.data.sharding;

import com.yulintu.thematic.JavaTypeConverter;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;
import java.util.OptionalDouble;

public class ShardReducerMin implements ShardReducer {

    @Override
    public Object reduce(ShardMethodInvokeMetadata metadata, ShardMethodInvokeResult[] shards) {

        MethodSignature signature = (MethodSignature) metadata.getJoinPoint().getSignature();
        Class returnType = signature.getReturnType();

        OptionalDouble val = Arrays.stream(shards).mapToDouble(c ->
                JavaTypeConverter.getInstance().toDouble(c.getReturnValue())).min();

        return val.isPresent() ? JavaTypeConverter.getInstance().to(returnType, val.getAsDouble()) : 0;
    }
}
