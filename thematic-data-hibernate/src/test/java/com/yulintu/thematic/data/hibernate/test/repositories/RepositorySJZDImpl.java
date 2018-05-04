package com.yulintu.thematic.data.hibernate.test.repositories;

import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.data.hibernate.RepositoryPersistenceQueryDSLImpl;
import com.yulintu.thematic.data.hibernate.test.sentities.QSJZD;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class RepositorySJZDImpl extends RepositoryPersistenceQueryDSLImpl implements RepositorySJZD {

    public RepositorySJZDImpl(Provider provider) {
        super(provider);
    }

    @Override
    public boolean any() {
        return super.any(QSJZD.sJZD);
    }
}
