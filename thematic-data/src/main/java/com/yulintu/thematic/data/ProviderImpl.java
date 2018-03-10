package com.yulintu.thematic.data;

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
        initialize(this.connectionString);
    }
    //endregion

    //region methods
    protected void initialize(String connectionString) {

    }
    //endregion
}
