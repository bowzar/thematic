package com.yulintu.thematic.data.redis;

import com.yulintu.thematic.data.ConnectionStringBuilder;

public class RedisConnectionStringBuilder extends ConnectionStringBuilder {

    //region ctor
    public RedisConnectionStringBuilder() {
    }

    public RedisConnectionStringBuilder(String connectionString) {
        super(connectionString);
    }
    //endregion

    //region configureFilePath
    public String getConfigureFilePath() {
        return getString("configure");
    }

    public void setConfigureFilePath(String value) {
        setString("configure", value);
    }
    //endregion

    //region mode
    public String getMode() {
        return getString("mode", "single");
    }

    public void setMode(String value) {
        setString("mode", value);
    }
    //endregion

    //region host
    public String getHost() {
        return getString("host", "localhost");
    }

    public void setHost(String value) {
        setString("host", value);
    }
    //endregion

    //region port
    public String getPort() {
        return getString("port", "6379");
    }

    public void setPort(String value) {
        setString("port", value);
    }
    //endregion

    //region password
    public String getPassword() {
        return getString("password");
    }

    public void setPassword(String value) {
        setString("password", value);
    }
    //endregion

    //region database
    public String getDatabase() {
        return getString("database", "0");
    }

    public void setDatabase(String value) {
        setString("database", value);
    }
    //endregion
}
