package com.yulintu.thematic.data;


public interface ProviderDb extends Provider {

    boolean openConnection();

    boolean closeConnection();

    boolean isConnectionOpened();

    boolean beginTransaction();

    boolean commitTransaction();

    boolean rollbackTransaction();

    boolean isTransactionBegun();
}
