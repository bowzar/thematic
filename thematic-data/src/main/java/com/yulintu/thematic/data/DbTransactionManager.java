package com.yulintu.thematic.data;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

public class DbTransactionManager implements PlatformTransactionManager {

    //region ctor
    public DbTransactionManager() {
    }
    //endregion

    //region methods
    @Override
    public TransactionStatus getTransaction(TransactionDefinition transactionDefinition) throws TransactionException {

        ProviderDb provider = (ProviderDb) ProviderUtils.initializeCurrentProvider();
        return new DbTransactionStatus(provider);
    }

    @Override
    public void commit(TransactionStatus transactionStatus) throws TransactionException {
        transactionStatus.flush();
    }

    @Override
    public void rollback(TransactionStatus transactionStatus) throws TransactionException {
        transactionStatus.rollbackToSavepoint(new Object());
    }
    //endregion
}
