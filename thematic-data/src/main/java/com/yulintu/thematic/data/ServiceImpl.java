package com.yulintu.thematic.data;

import org.springframework.util.Assert;

public class ServiceImpl implements Service {

    //region ctor
    public ServiceImpl() {

    }
    //endregion

    //region methods
    protected <T extends Repository> T getRepository(Class<? extends T> type) {

        Provider provider = ProviderUtils.getCurrentProvider();
        Assert.notNull(provider);

        return RepositoryFactory.get(provider).create(type, true);
    }
    //endregion
}
