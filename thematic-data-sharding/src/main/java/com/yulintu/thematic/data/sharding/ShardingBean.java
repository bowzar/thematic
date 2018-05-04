package com.yulintu.thematic.data.sharding;

import java.util.ArrayList;
import java.util.List;

public class ShardingBean {

    private List<ShardElement> shards = new ArrayList<>();

    public List<ShardElement> getShards() {
        return shards;
    }

    public void setShards(List<ShardElement> shards) {
        this.shards = shards;
    }
}
