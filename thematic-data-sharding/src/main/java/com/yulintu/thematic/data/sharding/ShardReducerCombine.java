package com.yulintu.thematic.data.sharding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ShardReducerCombine implements ShardReducer {

    @Override
    public Object reduce(ShardMethodInvokeMetadata metadata, ShardMethodInvokeResult[] shards) {

        List<Object> list = new ArrayList<>();
        AtomicBoolean isArray = new AtomicBoolean(false);

        Arrays.stream(shards).forEach(c -> {

            Object value = c.getReturnValue();
            if (value == null)
                return;

            Class<?> type = value.getClass();
            if (type.isArray()) {
                Object[] objs = (Object[]) value;
                list.addAll(Arrays.asList(objs));
                isArray.set(true);
                return;
            }

            if (value instanceof Collection<?>) {
                list.addAll((Collection<?>) value);
            }
        });

        return isArray.get() ? list.toArray() : list;
    }
}
