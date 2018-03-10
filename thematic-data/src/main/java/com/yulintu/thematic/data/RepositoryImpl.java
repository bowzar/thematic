package com.yulintu.thematic.data;

import static com.google.common.base.Preconditions.checkNotNull;

public class RepositoryImpl implements Repository {

    //region provider
    private Provider provider;

    protected Provider getProvider() {
        return provider;
    }
    //endregion

    //region ctor
    public RepositoryImpl(Provider provider) {
        checkNotNull(provider);
        this.provider = provider;
    }
    //endregion

    //region methods
    //endregion
}
