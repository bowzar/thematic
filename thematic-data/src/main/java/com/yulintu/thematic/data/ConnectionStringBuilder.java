package com.yulintu.thematic.data;

import com.yulintu.thematic.JavaTypeConverter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConnectionStringBuilder {

    //region properties
    //region connectionString
    private String connectionString;

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
    //endregion
    //endregion

    //region fields
    private Map<String, String> map;
    //endregion

    //region ctor
    public ConnectionStringBuilder() {
        this("");
    }

    public ConnectionStringBuilder(String connectionString) {
        reset(connectionString);
    }
    //endregion

    //region methods
    public boolean hasKey(String key) {
        return map.containsKey(key);
    }

    public String getString(String key, String defaultValue) {
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public void setString(String key, String value) {
        map.put(key, value);
        refresh();
    }

    public <T> T getValue(String key, T defaultValue) {
        return JavaTypeConverter.getInstance().to((Class<T>) defaultValue.getClass(), getString(key,
                JavaTypeConverter.getInstance().toString(defaultValue)));
    }

    public <T> T getValue(String key, Class<T> cls) {
        return JavaTypeConverter.getInstance().to(cls, getString(key));
    }

    public <T> void setValue(String key, T value) {
        setString(key, JavaTypeConverter.getInstance().toString(value));
    }

    private void reset(String connectionString) {

        this.connectionString = connectionString;
        map = new HashMap<>();

        if (StringUtils.isEmpty(connectionString))
            return;

        String[] strings = connectionString.split(";");
        Arrays.stream(strings).forEach(c -> {
            String[] opts = c.split("=");
            map.put(opts[0].trim(), opts.length > 1 ? opts[1].trim() : "");
        });
    }

    private void refresh() {

        StringBuilder builder = new StringBuilder();
        map.keySet().stream().forEach(c -> {
            builder.append(c).append("=").append(map.get(c)).append(";");
        });

        this.connectionString = builder.toString();
    }
    //endregion
}
