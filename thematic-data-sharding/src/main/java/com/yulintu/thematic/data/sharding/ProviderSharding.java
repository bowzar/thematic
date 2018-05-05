package com.yulintu.thematic.data.sharding;

import com.yulintu.thematic.data.ProviderDb;

import java.util.List;
import java.util.function.Predicate;

public interface ProviderSharding extends ProviderDb {

    //region methods
    List<ProviderDb> getAvailableShards();

    List<ProviderDb> getCurrentShards();

    List<ProviderDb> getQueryShards(String name, Predicate<ShardElement> predicate);

    List<ProviderDb> getEditShards(String name, Predicate<ShardElement> predicate);

    List<ProviderDb> setCurrentQueryShards(String name, Predicate<ShardElement> predicate);

    List<ProviderDb> setCurrentEditShards(String name, Predicate<ShardElement> predicate);
    //endregion
}
