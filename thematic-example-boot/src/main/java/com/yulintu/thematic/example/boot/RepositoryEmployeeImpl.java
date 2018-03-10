package com.yulintu.thematic.example.boot;

import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.data.ProviderHibernate;
import com.yulintu.thematic.data.RepositoryHibernateImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RepositoryEmployeeImpl extends RepositoryHibernateImpl implements RepositoryEmployee {

    public RepositoryEmployeeImpl(Provider provider) {
        super(provider);
    }

    @Override
    public String echo(String val) {
        return val + "#" + this.getClass().getName() + "#" + getProvider().getId();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> all() {

        Object val = getHibernateProvider()
                .execute(session -> session
                        .createQuery("from User")
                        .list());

        return (List<User>) val;
    }
}
