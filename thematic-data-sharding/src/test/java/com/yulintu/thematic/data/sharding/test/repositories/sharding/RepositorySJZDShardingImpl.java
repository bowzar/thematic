package com.yulintu.thematic.data.sharding.test.repositories.sharding;

import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.data.sharding.test.repositories.core.RepositorySJZDImpl;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class RepositorySJZDShardingImpl extends RepositorySJZDImpl {

    public RepositorySJZDShardingImpl(Provider provider) {
        super(provider);
    }

}
