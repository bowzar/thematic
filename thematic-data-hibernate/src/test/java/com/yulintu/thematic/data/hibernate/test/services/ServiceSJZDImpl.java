package com.yulintu.thematic.data.hibernate.test.services;

import com.yulintu.thematic.data.ServiceImpl;
import com.yulintu.thematic.data.hibernate.test.repositories.RepositorySJZD;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
