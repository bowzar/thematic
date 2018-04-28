package com.yulintu.thematic.data;

import com.google.common.base.Strings;

import java.util.UUID;

public class ProviderImpl implements Provider {

    //region id
    private UUID id;

    public UUID getId() {
        return id;
    }
    //endregion

    //region connectionString
    private String connectionString;

    public String getConnectionString() {
        return connectionString;
    }

    protected void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
    //endregion

    //region type
    private String type;

    public String getType() {
        return type;
    }

    protected void setType(String type) {
        this.type = type;
    }
    //endregion

    //region ctor
    public ProviderImpl(String connectionString) {
        this.id = UUID.randomUUID();
        this.connectionString = connectionString;

        if (!Strings.isNullOrEmpty(this.connectionString))
            initialize(this.connectionString);
    }
    //endregion

    //region methods
    protected void initialize(String connectionString) {

    }
    //endregion
}
