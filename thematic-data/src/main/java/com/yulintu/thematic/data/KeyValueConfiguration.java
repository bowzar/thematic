package com.yulintu.thematic.data;

import java.util.HashMap;
import java.util.Map;

public class KeyValueConfiguration {

    //region map
    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    private Map<String, String> map = new HashMap<>();
    //endregion

    //region ctor
    KeyValueConfiguration() {

    }
    //endregion

    //region methods
    public String get(String key) {
        return map.containsKey(key) ? map.get(key) : null;
    }

    public void set(String key, String value) {
        map.put(key, value);
    }
    //endregion
}
