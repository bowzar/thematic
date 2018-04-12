package com.yulintu.thematic.data.hibernate;

import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.data.Repository;
import com.yulintu.thematic.data.RepositoryImpl;

public class RepositoryPersistenceImpl extends RepositoryImpl implements Repository {

    //region ctor
    public RepositoryPersistenceImpl(Provider provider) {
        super(provider);
    }
    //endregion

    //region methods
    public ProviderPersistence getPersistenceProvider() {
        return (ProviderPersistence) getProvider();
    }
    //endregion
}
