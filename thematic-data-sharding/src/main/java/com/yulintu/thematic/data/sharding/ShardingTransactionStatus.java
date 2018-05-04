package com.yulintu.thematic.data.sharding;

import com.yulintu.thematic.data.ProviderDb;
import org.springframework.transaction.TransactionException;

public class ShardingTransactionStatus implements org.springframework.transaction.TransactionStatus {

    //region fields
    private ProviderDb provider;
    //endregion

    //region ctor
    public ShardingTransactionStatus(ProviderDb provider) {
        this.provider = provider;
    }
    //endregion

    //region methods
    @Override
    public boolean isNewTransaction() {
        return false;
    }

    @Override
    public boolean hasSavepoint() {
        return false;
    }

    @Override
    public void setRollbackOnly() {
    }

    @Override
    public boolean isRollbackOnly() {
        return false;
    }

    @Override
    public void flush() {
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    @Override
    public Object createSavepoint() throws TransactionException {
        return null;
    }

    @Override
    public void rollbackToSavepoint(Object o) throws TransactionException {
    }

    @Override
    public void releaseSavepoint(Object o) throws TransactionException {
    }
    //endregion
}
