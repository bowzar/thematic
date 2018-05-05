package com.yulintu.thematic.data.sharding.test.repositories.sharding;

import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.data.RepositoryFactory;
import com.yulintu.thematic.data.sharding.ProviderSharding;
import com.yulintu.thematic.data.sharding.test.repositories.RepositorySJZD;
import com.yulintu.thematic.data.sharding.test.repositories.core.RepositorySJZDImpl;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class RepositorySJZDShardingImpl extends RepositorySJZDImpl {

    public RepositorySJZDShardingImpl(Provider provider) {
        super(provider);
    }

    @Override
    public int count() {

        ProviderSharding provider = (ProviderSharding) getProvider();
        int sum = provider.getCurrentShards().parallelStream().mapToInt(c ->
                RepositoryFactory.get(c).create(RepositorySJZD.class, true).count()).sum();

        return sum;
    }
}
