package com.yulintu.thematic.data.sharding;

import com.yulintu.thematic.data.ProviderDb;
import com.yulintu.thematic.data.ProviderUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

public class ShardingTransactionManager implements PlatformTransactionManager {

    //region ctor
    public ShardingTransactionManager() {
    }
    //endregion

    //region methods
    @Override
    public TransactionStatus getTransaction(TransactionDefinition transactionDefinition) throws TransactionException {

        ProviderDb provider = (ProviderDb) ProviderUtils.initializeCurrentProvider();
        return new ShardingTransactionStatus(provider);
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
