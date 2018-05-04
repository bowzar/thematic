package com.yulintu.thematic.data.sharding.test.others;

import com.yulintu.thematic.data.sharding.ShardElement;
import com.yulintu.thematic.data.sharding.ShardMethodInvokeMetadata;
import com.yulintu.thematic.data.sharding.ShardRouter;
import com.yulintu.thematic.data.sharding.ShardRouterHandler;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@ShardRouter
public class ShardRouterHandlerStar implements ShardRouterHandler {

    @Override
    public boolean inspect(ShardMethodInvokeMetadata metadata, ShardElement shard) {

        String type = metadata.getShardType();
        Properties properties = shard.getProperties();
        return "*".equals(type) && properties.containsKey("*");
    }
}
