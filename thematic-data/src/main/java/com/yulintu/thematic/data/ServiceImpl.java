package com.yulintu.thematic.data;

import com.yulintu.thematic.AssertUtils;

public class ServiceImpl implements Service {

    //region ctor
    public ServiceImpl() {

    }
    //endregion

    //region methods
    protected <T extends Repository> T getRepository(Class<? extends T> type) {

        Provider provider = ProviderUtils.getCurrentProvider();
        AssertUtils.notNull(provider, "provider");

        return RepositoryFactory.get(provider).create(type, true);
    }
    //endregion
}
