package com.yulintu.thematic.data.sharding.test.services;

import com.yulintu.thematic.data.ServiceImpl;
import com.yulintu.thematic.data.sharding.ShardKey;
import com.yulintu.thematic.data.sharding.Shardable;
import com.yulintu.thematic.data.sharding.test.entities.SJZD;
import com.yulintu.thematic.data.sharding.test.repositories.RepositorySJZD;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ServiceSJZDImpl extends ServiceImpl implements ServiceSJZD {

    private RepositorySJZD repository() {
        return getRepository(RepositorySJZD.class);
    }

    @Override
    @Transactional
    public boolean any() {
        return repository().any();
    }

    @Override
    public boolean any(String code) {
        return repository().any(code);
    }

    @Override
    public int count(String code) {
        return repository().count(code);
    }

    @Override
    public List<SJZD> get(String code) {
        return repository().get(code);
    }
}
