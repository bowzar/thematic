package com.yulintu.thematic.example.employee.postgresql;

import com.yulintu.thematic.data.Provider;
import com.yulintu.thematic.example.employee.RepositoryEmployeeImpl;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class RepositoryEmployeePostgreSQL extends RepositoryEmployeeImpl {

    public RepositoryEmployeePostgreSQL(Provider provider) {
        super(provider);
    }

    @Override
    public String toString() {
        return super.toString() + "#" + "PostgreSQL";
    }
}
