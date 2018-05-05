package com.yulintu.thematic.data.sharding.test.repositories.core;

import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.data.hibernate.RepositoryPersistenceQueryDSLImpl;
import com.yulintu.thematic.data.sharding.ShardKey;
import com.yulintu.thematic.data.sharding.ShardReduceType;
import com.yulintu.thematic.data.sharding.Shardable;
import com.yulintu.thematic.data.sharding.ShardConfig;
import com.yulintu.thematic.data.sharding.test.entities.SJZD;
import com.yulintu.thematic.data.sharding.test.repositories.RepositorySJZD;
import com.yulintu.thematic.data.sharding.test.sentities.QSJZD;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope("prototype")
@ShardConfig("*")
public class RepositorySJZDImpl extends RepositoryPersistenceQueryDSLImpl implements RepositorySJZD {

    public RepositorySJZDImpl(Provider provider) {
        super(provider);
    }

    @Override
    @Shardable(reduceType = ShardReduceType.FIRST)
    public boolean any() {
        return super.any(QSJZD.sJZD);
    }

    @Override
    @Shardable(value = "zone", reduceType = ShardReduceType.FIRST)
    public boolean any(@ShardKey String code) {
//        throw new DataException("test");
        return super.any(QSJZD.sJZD, QSJZD.sJZD.bm.eq(code));
    }

    @Override
    @Shardable(value = "zone", reduceType = ShardReduceType.MAX)
    public int count(@ShardKey String code) {
        return super.count(QSJZD.sJZD, QSJZD.sJZD.bm.eq(code));
    }

    @Override
    @Shardable(value = "zone", reduceType = ShardReduceType.COMBINE)
    public List<SJZD> get(@ShardKey String code) {
        return super.list(QSJZD.sJZD, QSJZD.sJZD.bm.eq(code));
    }

    @Override
    public int count() {
        return count(QSJZD.sJZD);
    }
}
