package com.yulintu.thematic.example.employee;

import com.yulintu.thematic.data.DbConnection;
import com.yulintu.thematic.data.DbTransaction;
import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.data.hibernate.RepositoryHibernateImpl;

import java.util.List;

@DbConnection
public class RepositoryEmployeeImpl extends RepositoryHibernateImpl implements RepositoryEmployee {

    public RepositoryEmployeeImpl(Provider provider) {
        super(provider);
    }

    @Override
    public String echo(String val) {
        return val + "#" + this.getClass().getName() + "#" + getProvider().getId();
    }

    @SuppressWarnings("unchecked")
    @DbTransaction
    @Override
    public List<User> all() {

//        throw new EmployeeTestException("链接未打开", new Exception("test"));

        Object val = getHibernateProvider()
                .execute(session -> session
                        .createQuery("from User")
                        .list());


        User user = new User();
        return (List<User>) val;
    }
}
