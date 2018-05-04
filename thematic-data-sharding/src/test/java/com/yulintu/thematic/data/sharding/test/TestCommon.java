package com.yulintu.thematic.data.sharding.test;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.yulintu.thematic.data.FileConnectionStringBuilder;
import com.yulintu.thematic.data.ProviderDb;
import com.yulintu.thematic.data.sharding.ProviderShardingImpl;
import com.yulintu.thematic.data.sharding.ShardElement;
import org.junit.Test;

import java.util.List;

public class TestCommon {

    @Test
    public void testConfiguration() {

        FileConnectionStringBuilder builder = new FileConnectionStringBuilder();
        builder.setConfigureFilePath("spring.datasource.shards.xml");

        ProviderShardingImpl provider = new ProviderShardingImpl(builder.getConnectionString());

        String zoneCode = "510181";
        String code = zoneCode;
        Predicate<ShardElement> predicate = c -> {

            String zone = (String) c.getProperties().get("zone");
            if (Strings.isNullOrEmpty(zone))
                return false;

            if ("86".equals(zone))
                return true;

            if (zone.startsWith(code) || code.startsWith(zone))
                return true;

            return false;
        };

        List<ProviderDb> z0 = provider.getEditShards(zoneCode, predicate);
        List<ProviderDb> z1 = provider.getEditShards(zoneCode, predicate);
        List<ProviderDb> z2 = provider.getEditShards(zoneCode, predicate);

        zoneCode = "51";
        List<ProviderDb> queryShards = provider.getQueryShards(zoneCode, predicate);
    }
}
