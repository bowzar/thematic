package com.yulintu.thematic.example.employee;

import com.yulintu.thematic.data.DbConnection;
import com.yulintu.thematic.data.ServiceImpl;
import com.yulintu.thematic.data.DbTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceEmployeeImpl extends ServiceImpl implements ServiceEmployee {

    private RepositoryEmployee repository() {
        return getRepository(RepositoryEmployee.class);
    }

    @Override
    public String echo(String val) {
        return repository().echo(this.getClass().getName());
    }

    @Override
    public List<User> all() {
        return repository().all();
    }

}
