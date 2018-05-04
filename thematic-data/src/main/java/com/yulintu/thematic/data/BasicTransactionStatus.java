package com.yulintu.thematic.data;

import org.springframework.transaction.TransactionException;

public class BasicTransactionStatus implements org.springframework.transaction.TransactionStatus {

    //region fields
    private ProviderDb provider;
    private boolean isTransactionNew;
    //endregion

    //region ctor
    public BasicTransactionStatus(ProviderDb provider) {
        this.provider = provider;
        provider.openConnection();
        isTransactionNew = provider.beginTransaction();
    }
    //endregion

    //region methods
    @Override
    public boolean isNewTransaction() {
        return isTransactionNew;
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
        provider.commitTransaction();
        provider.closeConnection();
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
        provider.rollbackTransaction();
        provider.closeConnection();
    }

    @Override
    public void releaseSavepoint(Object o) throws TransactionException {
    }
    //endregion
}
