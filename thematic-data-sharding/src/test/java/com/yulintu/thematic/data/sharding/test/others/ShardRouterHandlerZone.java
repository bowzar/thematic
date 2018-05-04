package com.yulintu.thematic.data.sharding.test.others;

import com.google.common.base.Strings;
import com.yulintu.thematic.data.sharding.ShardElement;
import com.yulintu.thematic.data.sharding.ShardMethodInvokeMetadata;
import com.yulintu.thematic.data.sharding.ShardRouter;
import com.yulintu.thematic.data.sharding.ShardRouterHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;

@Component
@ShardRouter
public class ShardRouterHandlerZone implements ShardRouterHandler {

    @Override
    public boolean inspect(ShardMethodInvokeMetadata metadata, ShardElement shard) {

        String type = metadata.getShardType();
        Properties properties = shard.getProperties();

        if (!"zone".equals(type))
            return false;
        if (!properties.containsKey("zone"))
            return false;

        String zone = (String) properties.get("zone");
        if (Strings.isNullOrEmpty(zone))
            return false;

        HashMap<String, Object> args = metadata.getArgs();
        Optional<Object> first = args.values().stream().findFirst();
        if (!first.isPresent())
            return false;

        String code = (String) first.get();
        if (Strings.isNullOrEmpty(code))
            return false;

        if ("86".equals(code))
            return true;

        return zone.startsWith(code) || code.startsWith(zone);
    }
}
