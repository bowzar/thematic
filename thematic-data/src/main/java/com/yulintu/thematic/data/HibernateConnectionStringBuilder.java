package com.yulintu.thematic.data;

public class HibernateConnectionStringBuilder extends ConnectionStringBuilder {

    //region ctor
    public HibernateConnectionStringBuilder() {
    }

    public HibernateConnectionStringBuilder(String connectionString) {
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


    //region show_sql
    public String getShowSql() {
        return getString("hibernate.show_sql", "true");
    }

    public void setShowSql(String value) {
        setString("hibernate.show_sql", value);
    }
    //endregion

    //region format_sql
    public String getFormatSql() {
        return getString("hibernate.format_sql", "true");
    }

    public void setFormatSql(String value) {
        setString("hibernate.format_sql", value);
    }
    //endregion

    //region dialect
    public String getDialect() {
        return getString("hibernate.dialect");
    }

    public void setDialect(String value) {
        setString("hibernate.dialect", value);
    }
    //endregion

    //region driver_class
    public String getDriverClass() {
        return getString("hibernate.connection.driver_class");
    }

    public void setDriverClass(String value) {
        setString("hibernate.connection.driver_class", value);
    }
    //endregion

    //region host
    public String getHost() {
        return getString("hibernate.connection.host");
    }

    public void setHost(String value) {
        setString("hibernate.connection.host", value);
    }
    //endregion

    //region database
    public String getDatabase() {
        return getString("hibernate.connection.database");
    }

    public void setDatabase(String value) {
        setString("hibernate.connection.database", value);
    }
    //endregion

    //region username
    public String getUsername() {
        return getString("hibernate.connection.username");
    }

    public void setUsername(String value) {
        setString("hibernate.connection.username", value);
    }
    //endregion

    //region password
    public String getPassword() {
        return getString("hibernate.connection.password");
    }

    public void setPassword(String value) {
        setString("hibernate.connection.password", value);
    }
    //endregion
}
