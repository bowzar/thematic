package com.yulintu.thematic.data.sharding;

public interface ShardReducer {

    
    public Object reduce(ShardMethodInvokeMetadata metadata, ShardMethodInvokeResult[] shards);
}
