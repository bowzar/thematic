package com.yulintu.thematic.data.sharding;

import com.yulintu.thematic.ClassUtils;

import java.util.Arrays;
import java.util.Optional;

public class ShardReducerFirst implements ShardReducer {

    @Override
    public Object reduce(ShardMethodInvokeMetadata metadata, ShardMethodInvokeResult[] shards) {

        Optional<ShardMethodInvokeResult> any = Arrays.stream(shards)
                .filter(c -> ClassUtils.isTrue(c.getReturnValue())).findAny();

        return any.isPresent() ? any.get().getReturnValue() : shards[0].getReturnValue();
    }
}
