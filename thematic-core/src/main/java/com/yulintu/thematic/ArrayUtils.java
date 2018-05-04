package com.yulintu.thematic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;

public class ArrayUtils {

    public static <K, V, T> HashMap<K, V> toHashMap(Function<T, K> keySelector, Function<T, V> valueSelector, T... items) {

        AssertUtils.notNull(keySelector, "keySelector");
        AssertUtils.notNull(valueSelector, "valueSelector");

        HashMap<K, V> map = new HashMap<>();
        Arrays.stream(items).forEach(c -> {
            map.put(keySelector.apply(c), valueSelector.apply(c));
        });

        return map;
    }

    public static <T> HashSet<T> toHashSet(T... items) {

        HashSet<T> ts = new HashSet<>();
        Arrays.stream(items).forEach(c -> ts.add(c));
        return ts;
    }
}
