package com.yulintu.thematic.data.sharding;

public class ShardReducerFirst implements ShardReducer {

    @Override
    public Object reduce(ShardMethodInvokeMetadata metadata, ShardMethodInvokeResult[] shards) {
        return shards.length > 0 ? shards[0].getReturnValue() : null;
    }
}
