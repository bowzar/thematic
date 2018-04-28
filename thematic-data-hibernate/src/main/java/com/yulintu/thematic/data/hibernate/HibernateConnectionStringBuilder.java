package com.yulintu.thematic.data.hibernate;

import com.google.common.base.Strings;
import com.yulintu.thematic.data.ConnectionStringBuilder;

import java.util.ArrayList;
import java.util.Arrays;

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

    //region configureFilePath
    public String getConfigureFileType() {
        return getString("type", "string");
    }

    public void setConfigureFileType(String value) {
        setString("type", value);
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

    //region url
    public String getUrl() {
        return getString("hibernate.connection.url");
    }

    public void setUrl(String value) {
        setString("hibernate.connection.url", value);
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

    //region password
    public Class[] getMappingClasses() {

        if (!hasKey("mappingClasses"))
            return new Class[0];

        String[] classes = getString("mappingClasses").split("#");
        ArrayList<Class> types = new ArrayList<>();

        Arrays.stream(classes).forEach(c -> {
            if (Strings.isNullOrEmpty(c))
                return;

            try {
                types.add(Class.forName(c));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return types.toArray(new Class[0]);
    }

    public void setMappingClasses(Class[] types) {

        StringBuilder builder = new StringBuilder(types[0].getName());
        for (int i = 1; i < types.length; i++) {
            builder.append(String.format("#%s", types[i].getName()));
        }

        setString("mappingClasses", builder.toString());
    }
    //endregion
}
