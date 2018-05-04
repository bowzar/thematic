package com.yulintu.thematic.data.sharding;

import com.yulintu.thematic.AssertUtils;
import com.yulintu.thematic.ClassUtils;
import com.yulintu.thematic.ExceptionUtils;
import com.yulintu.thematic.data.*;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProviderShardingImpl extends ProviderDbImpl implements ProviderSharding {

    //region fields
    @Getter
    @Setter
    private ShardingBean shardingBean;

    private List<ProviderDb> currentShards;

    private ConcurrentHashMap<String, List<Pair<ShardElement, ProviderDb>>> mapKey = new ConcurrentHashMap<>();
    private ConcurrentHashMap<ShardElement, ProviderDb> mapElement = new ConcurrentHashMap<>();
    //endregion

    //region ctor
    public ProviderShardingImpl(String connectionString) {
        super(connectionString);
    }
    //endregion

    //region methods - system
    @Override
    protected void initialize(String connectionString) {

        FileConnectionStringBuilder b = new FileConnectionStringBuilder(connectionString);
        String filePath = b.getConfigureFilePath();

        ApplicationContext ac = ClassPathXmlApplicationContextPool.findInitialize(filePath);
        shardingBean = ac.getBean(ShardingBean.class);

        setType("sharding");
    }

    @Override
    protected boolean onOpenConnection() {
        AssertUtils.notNull(currentShards, "currentShards");

        final RuntimeException[] error = {null};

        currentShards.parallelStream().forEach(c -> {
            try {
                c.openConnection();

            } catch (Throwable e) {
                error[0] = ExceptionUtils.createRuntimeException(e);
            }
        });

        if (error[0] != null)
            throw error[0];

        return true;
    }

    @Override
    public boolean onCloseConnection() {
        AssertUtils.notNull(currentShards, "currentShards");

        final RuntimeException[] error = {null};
        currentShards.parallelStream().forEach(c -> {
            try {
                if (c.isConnectionOpened())
                    c.closeConnection();

            } catch (Throwable e) {
                error[0] = ExceptionUtils.createRuntimeException(e);
            }
        });

        if (error[0] != null)
            throw error[0];

        return true;
    }

    @Override
    public boolean onBeginTransaction() {
        AssertUtils.notNull(currentShards, "currentShards");

        final RuntimeException[] error = {null};
        currentShards.parallelStream().forEach(c -> {
            try {
                c.beginTransaction();

            } catch (Throwable e) {
                error[0] = ExceptionUtils.createRuntimeException(e);
            }
        });

        if (error[0] != null)
            throw error[0];

        return true;
    }

    @Override
    public boolean onCommitTransaction() {
        AssertUtils.notNull(currentShards, "currentShards");

        final RuntimeException[] error = {null};
        currentShards.parallelStream().forEach(c -> {
            try {
                if (c.isTransactionBegun())
                    c.commitTransaction();

            } catch (Throwable e) {
                error[0] = ExceptionUtils.createRuntimeException(e);
            }
        });

        if (error[0] != null)
            throw error[0];

        return true;
    }

    @Override
    public boolean onRollbackTransaction() {
        AssertUtils.notNull(currentShards, "currentShards");

        final RuntimeException[] error = {null};
        currentShards.parallelStream().forEach(c -> {
            try {
                if (c.isTransactionBegun())
                    c.rollbackTransaction();

            } catch (Throwable e) {
                error[0] = ExceptionUtils.createRuntimeException(e);
            }
        });

        if (error[0] != null)
            throw error[0];

        return true;
    }
    //endregion

    //region methods - sharding
    public List<ProviderDb> getAvailableShards() {
        return Arrays.asList(mapElement.values().toArray(new ProviderDb[0]));
    }

    @Override
    public List<ProviderDb> getQueryShards(String name, Predicate<ShardElement> predicate) {
        List<Pair<ShardElement, ProviderDb>> list = getShardsInner(name, predicate);
        Map<String, List<Pair<ShardElement, ProviderDb>>> groups =
                list.stream().collect(Collectors.groupingBy(c -> c.getKey().getGroup()));

        List<ProviderDb> result = new ArrayList<>();
        groups.values().forEach(c -> result.add(c.get((int) (Math.random() * c.size())).getValue()));

        return result;
    }

    @Override
    public List<ProviderDb> getEditShards(String name, Predicate<ShardElement> predicate) {
        List<Pair<ShardElement, ProviderDb>> list = getShardsInner(name, predicate);
        return list.stream().map(Pair::getValue).collect(Collectors.toList());
    }

//    @Override
//    public List<ProviderDb> setCurrentQueryShards(String name, Predicate<ShardElement> predicate) {
//        List<ProviderDb> list = getShardsInner(name, predicate);
//        currentShards = Arrays.asList(list.get((int) (Math.random() * list.size())));
//        return currentShards;
//    }
//
//    @Override
//    public List<ProviderDb> setCurrentEditShards(String name, Predicate<ShardElement> predicate) {
//        List<ProviderDb> list = getShardsInner(name, predicate);
//        currentShards = list;
//        return list;
//    }

    private List<Pair<ShardElement, ProviderDb>> getShardsInner(String name, Predicate<ShardElement> predicate) {

        if (mapKey.containsKey(name))
            return mapKey.get(name);

        List<Pair<ShardElement, ProviderDb>> result = new ArrayList<>();
        List<ShardElement> shards = shardingBean.getShards();

        shards.parallelStream().forEach(c -> {

            if (predicate.test(c)) {
                Pair pair = new Pair(c, getShardProvider(c));
                synchronized (result) {
                    result.add(pair);
                }
            }
        });

        mapKey.put(name, result);
        return result;
    }

    private ProviderDb getShardProvider(ShardElement element) {

        if (mapElement.containsKey(element))
            return mapElement.get(element);

        ProviderDb provider = (ProviderDb) ClassUtils.newInstance(
                ClassUtils.getConstructor(ClassUtils.getClass(
                        element.getProvider()), String.class), element.getConnectionString());

        mapElement.put(element, provider);
        return provider;
    }
    //endregion
}
