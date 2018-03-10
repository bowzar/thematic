package com.yulintu.thematic.data;

public class RepositoryHibernateImpl extends RepositoryImpl implements Repository {

    //region ctor
    public RepositoryHibernateImpl(Provider provider) {
        super(provider);
    }
    //endregion

    //region methods
    public ProviderHibernate getHibernateProvider() {
        return (ProviderHibernate) getProvider();
    }
    //endregion
}
