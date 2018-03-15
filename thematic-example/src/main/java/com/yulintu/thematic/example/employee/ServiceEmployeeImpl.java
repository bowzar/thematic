package com.yulintu.thematic.example.employee;

import com.yulintu.thematic.data.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceEmployeeImpl extends ServiceImpl implements ServiceEmployee {

    private RepositoryEmployee repository() {
        return getRepository(RepositoryEmployee.class);
    }

    @Override
    @Cacheable(value = "default", key = "'test:'+#root.targetClass.simpleName+':'+#root.methodName+':'+#val")
    public String echo(String val) {
        return repository().echo(this.getClass().getName());
    }

    @Override
    @Cacheable(value = "employee", key = "'test:'+#root.targetClass.simpleName+':'+#root.methodName+':0'")
    public List<User> all() {
        return repository().all();
    }

    @Override
    @CacheEvict(value = "default", allEntries = true)
    public int clear() {
        return 1;
    }

    @Override
    @CacheEvict(value = {"default", "employee"}, allEntries = true)
    public int clearAll() {
        return 1;
    }
}
