package com.yulintu.thematic.data.sharding;

import java.util.Arrays;
import java.util.Optional;

public class ShardReducerFirstNotNull implements ShardReducer {

    @Override
    public Object reduce(ShardMethodInvokeMetadata metadata, ShardMethodInvokeResult[] shards) {
        Optional<ShardMethodInvokeResult> any = Arrays.stream(shards).filter(c -> c.getReturnValue() != null).findAny();
        return any.isPresent() ? any.get().getReturnValue() : null;
    }
}
