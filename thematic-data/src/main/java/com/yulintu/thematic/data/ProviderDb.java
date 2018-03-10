package com.yulintu.thematic.data;


public interface ProviderDb extends Provider {

    boolean openConnection();

    boolean closeConnection();

    boolean beginTransaction();

    boolean commitTransaction();

    boolean rollbackTransaction();
}
