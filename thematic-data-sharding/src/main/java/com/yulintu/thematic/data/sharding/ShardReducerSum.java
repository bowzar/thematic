package com.yulintu.thematic.data.sharding;

import com.yulintu.thematic.JavaTypeConverter;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;

public class ShardReducerSum implements ShardReducer {

    @Override
    public Object reduce(ShardMethodInvokeMetadata metadata, ShardMethodInvokeResult[] shards) {

        MethodSignature signature = (MethodSignature) metadata.getJoinPoint().getSignature();
        Class returnType = signature.getReturnType();

        Double val = Arrays.stream(shards).mapToDouble(c ->
                JavaTypeConverter.getInstance().toDouble(c.getReturnValue())).sum();

        return JavaTypeConverter.getInstance().to(returnType, val);
    }
}
