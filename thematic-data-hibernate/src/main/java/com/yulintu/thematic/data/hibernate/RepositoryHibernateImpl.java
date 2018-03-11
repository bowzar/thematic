package com.yulintu.thematic.data.hibernate;

import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.data.Repository;
import com.yulintu.thematic.data.RepositoryImpl;

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
