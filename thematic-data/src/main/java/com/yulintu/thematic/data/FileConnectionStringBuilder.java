package com.yulintu.thematic.data;

public class FileConnectionStringBuilder extends ConnectionStringBuilder {

    //region ctor
    public FileConnectionStringBuilder() {

    }

    public FileConnectionStringBuilder(String connectionString) {
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
}
