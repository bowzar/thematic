package com.yulintu.thematic.data.sharding;

public interface ShardRouterHandler {

    boolean inspect(ShardMethodInvokeMetadata metadata, ShardElement shard);
}
